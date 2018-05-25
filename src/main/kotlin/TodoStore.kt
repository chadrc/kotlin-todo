import store.Store
import store.StoreConnector
import kotlin.browser.localStorage

class TodoStore : Store() {
    private enum class Resource {
        Collection,
        Todo
    }
    private var _todoCollections: ArrayList<TodoCollection> = ArrayList()
    private var _selectedTodoCollectionIndex: Int = -1
    private var _newCollectionName: String = ""
    private var _newTodoText: String = ""
    private var _indexToDelete: Int = -1
    private var _deleteType: Resource? = null

    val todoCollections: ArrayList<TodoCollection> get() = _todoCollections
    val newCollectionName: String get() = _newCollectionName
    val newTodoText: String get() = _newTodoText
    val indexToDelete: Int get() = _indexToDelete
    val selectedTodoCollection: TodoCollection?
        get() = if (_selectedTodoCollectionIndex == -1
                || _selectedTodoCollectionIndex >= _todoCollections.size)
            null else _todoCollections[_selectedTodoCollectionIndex]

    init {
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
            _todoCollections = ArrayList()

            // Hack until serialization can be figured out
            val todoCollections: dynamic = data.todoCollections
            var i: dynamic = 0
            while (i < todoCollections.length) {
                val collectionData = todoCollections[i]
                val todosData = collectionData.todos
                val todos = ArrayList<Todo>()

                var j: dynamic = 0
                while (j < todosData.length) {
                    val todoData = todosData[j]
                    todos.add(Todo(todoData.text as String, todoData.completed as Boolean))
                    j++
                }

                _todoCollections.add(TodoCollection(collectionData.name as String, todos))
                i++
            }
        }
    }

    fun updateNewCollectionName(name: String) = action {
        _newCollectionName = name
    }

    fun createNewCollection() = action {
        _todoCollections.add(TodoCollection(_newCollectionName))
        _newCollectionName = ""
        serialize()
    }

    fun updateNewTodoText(text: String) = action {
        _newTodoText = text
    }

    fun createNewTodo() = action {
        selectedTodoCollection?.todos?.add(Todo(_newTodoText))
        _newTodoText = ""
        serialize()
    }

    fun selectTodoCollection(index: Int) = action {
        _selectedTodoCollectionIndex = index
        serialize()
    }

    fun toggleComplete(index: Int) = action {
        val collection = selectedTodoCollection
        if (collection != null) {
            val todo = collection.todos[index]
            todo.completed = !todo.completed
        }
        serialize()
    }

    fun startDeleteCollection(index: Int) = action {
        _deleteType = Resource.Collection
        _indexToDelete = index
    }

    fun startDeleteTodo(index: Int) = action {
        _deleteType = Resource.Todo
        _indexToDelete = index
    }

    fun confirmDelete() = action {
        val collection = if (_deleteType == Resource.Collection) _todoCollections else selectedTodoCollection?.todos
        console.log("removing", _indexToDelete, collection)
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
}

val connector = StoreConnector(TodoStore())