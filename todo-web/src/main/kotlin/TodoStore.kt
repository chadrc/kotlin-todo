import org.w3c.fetch.RequestInit
import react.RComponent
import store.Store
import store.StoreConnector
import kotlin.browser.localStorage
import kotlin.browser.window
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class TodoStore : Store() {
    private enum class Resource {
        Collection,
        Todo
    }

    private var _apiEnabled = false
    private var _todoCollections = ArrayList<TodoCollection>()
    private var _selectedTodoCollectionIndex = -1
    private var _newCollectionName = ""
    private var _newTodoText = ""
    private var _indexToDelete = -1
    private var _deleteType: Resource? = null

    @Suppress("unused")
    val todoCollections: ArrayList<TodoCollection> get() = _todoCollections

    @Suppress("unused")
    val newCollectionName: String get() = _newCollectionName
    val newTodoText: String get() = _newTodoText
    val indexToDelete: Int get() = _indexToDelete
    val selectedTodoCollection: TodoCollection?
        get() = if (_selectedTodoCollectionIndex == -1
                || _selectedTodoCollectionIndex >= _todoCollections.size)
            null else _todoCollections[_selectedTodoCollectionIndex]

    init {
        // Check if api is up
        window.fetch("/collection", object : RequestInit {
            override var method: String? = "GET"
        }).then {
            it.json().then {
                _apiEnabled = true

                // Inline action to invoke listeners
                this@TodoStore.action {
                    this._todoCollections = parseTodos(it)
                }
            }.catch {
                console.error(it)
            }
        }.catch {
            console.log("Can't connect, will use local storage for persistence.")

            val dataStr = localStorage.getItem("todo-data")
            if (dataStr == null) {
                _todoCollections = ArrayList()
                val startingTodos: ArrayList<Todo> = ArrayList()

                startingTodos.add(Todo("Make Coffee"))
                startingTodos.add(Todo("Eat Breakfast"))
                startingTodos.add(Todo("Pack Lunch"))

                _todoCollections.add(TodoCollection("Morning Routine", startingTodos))
                serialize()
            } else {
                val data = JSON.parse<SaveData>(dataStr)

                _selectedTodoCollectionIndex = data.selectedCollectionIndex
                _todoCollections = parseTodos(data.todoCollections)
            }
        }
    }

    fun updateNewCollectionName(name: String) = action {
        _newCollectionName = name
    }

    private data class CreateCollectionRequest(val name: String)

    fun createNewCollection() = action {
        val newIndex = _todoCollections.size
        _todoCollections.add(TodoCollection(_newCollectionName))
        serialize()

        if (_apiEnabled) {
            postFetch("/collection", CreateCollectionRequest(_newCollectionName)) {
                val collection = parseCollection(it)
                // reassign collection so it has id generated by api
                _todoCollections[newIndex] = collection
                serialize()
            }
        }

        _newCollectionName = ""
    }

    fun updateNewTodoText(text: String) = action {
        _newTodoText = text
    }

    private data class NewTodoRequest(val collectionId: String, val text: String)

    fun createNewTodo() = action {
        selectedTodoCollection?.todos?.add(Todo(_newTodoText))
        serialize()

        if (_apiEnabled) {
            val id = selectedTodoCollection?.id ?: ""
            postFetch("/collection/todo", NewTodoRequest(id, _newTodoText))
        }
        _newTodoText = ""
    }

    fun selectTodoCollection(index: Int) = action {
        _selectedTodoCollectionIndex = index
        serialize()
    }

    private data class EditTodoRequest(val collectionId: String, val todoIndex: Int, val completed: Boolean)

    fun toggleComplete(index: Int) = action {
        val collection = selectedTodoCollection
        if (collection != null) {
            val todo = collection.todos[index]
            todo.completed = !todo.completed
            serialize()

            if (_apiEnabled) {
                val id = selectedTodoCollection?.id ?: ""
                patchFetch("/collection/todo", EditTodoRequest(id, index, todo.completed))
            }
        }
    }

    fun startDeleteCollection(index: Int) = action {
        _deleteType = Resource.Collection
        _indexToDelete = index
    }

    fun startDeleteTodo(index: Int) = action {
        _deleteType = Resource.Todo
        _indexToDelete = index
    }

    private data class DeleteCollectionRequest(val collectionId: String)
    private data class DeleteTodoRequest(val collectionId: String, val todoIndex: Int)

    fun confirmDelete() = action {
        val collection: ArrayList<*>?
        if (_deleteType == Resource.Collection) {
            collection = _todoCollections
            val item = _todoCollections[_indexToDelete]
            val collectionId = item.id
            deleteFetch("/collection", DeleteCollectionRequest(collectionId))
        } else {
            collection = selectedTodoCollection?.todos
            val collectionId = selectedTodoCollection?.id ?: ""
            deleteFetch("/collection/todo", DeleteTodoRequest(collectionId, _indexToDelete))
        }
        collection?.removeAt(_indexToDelete)
        cancelDelete()
    }

    fun cancelDelete() = action {
        _indexToDelete = -1
        _deleteType = null
    }

    private data class SaveData(
            val todoCollections: ArrayList<TodoCollection>,
            val selectedCollectionIndex: Int
    )

    private fun serialize() {
        localStorage.setItem("todo-data", JSON.stringify(SaveData(
                _todoCollections,
                _selectedTodoCollectionIndex
        )))
    }

    private fun parseTodos(data: dynamic): ArrayList<TodoCollection> {
        // Hack until serialization can be figured out
        val collections = ArrayList<TodoCollection>()
        var i: dynamic = 0
        while (i < data.length) {
            collections.add(parseCollection(data[i]))
            i++
        }

        return collections
    }

    private fun parseCollection(data: dynamic): TodoCollection {
        val todosData = data.todos
        val todos = ArrayList<Todo>()

        var j: dynamic = 0
        while (j < todosData.length) {
            val todoData = todosData[j]
            todos.add(Todo(todoData.text as String, todoData.completed as Boolean))
            j++
        }

        return TodoCollection(
                data.name as String,
                todos,
                data.id as String
        )
    }
}

class StoreResourceDelegate<P>(private val bindProp: KProperty<P>?) : ReadOnlyProperty<Any, P> {
    override fun getValue(thisRef: Any, property: KProperty<*>): P {
        val name = bindProp?.name ?: property.name
        return todoStore.asDynamic()[name] as P
    }
}

class StoreResourceLoader<P>
(private val bindProp: KProperty<P>? = null) {
    init {
        if (bindProp != null
                && todoStore.asDynamic()[bindProp.name] == undefined) {
            throw Exception("Property ${bindProp.name} not defined in TodoStore.")
        }
    }

    operator fun provideDelegate(
            thisRef: Any,
            prop: KProperty<*>
    ): ReadOnlyProperty<Any, P> {
        if (todoStore.asDynamic()[prop.name] == undefined) {
            throw Exception("Property ${prop.name} not defined in TodoStore.")
        }

        if (thisRef is RComponent<*, *>) {
            todoStore.addListener { thisRef.forceUpdate() }
        }

        return StoreResourceDelegate(bindProp)
    }
}

val todoStore = TodoStore()
val connector = StoreConnector(todoStore)