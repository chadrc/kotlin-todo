import store.Store
import store.StoreConnector

class TodoStore : Store() {
    private var _todoCollections: ArrayList<TodoCollection> = ArrayList()
    private var _selectedTodoCollectionIndex: Int = -1
    private var _newCollectionName: String = ""
    private var _newTodoText: String = ""

    val todoCollections: ArrayList<TodoCollection> get() = _todoCollections
    val newCollectionName: String get() = _newCollectionName
    val newTodoText: String get() = _newTodoText
    val selectedTodoCollection: TodoCollection?
        get() = if (_selectedTodoCollectionIndex == -1) null else _todoCollections[_selectedTodoCollectionIndex]

    init {
        _todoCollections = ArrayList()
        val startingTodos: ArrayList<Todo> = ArrayList()

        startingTodos.add(Todo("Make Coffee"))
        startingTodos.add(Todo("Eat Breakfast"))
        startingTodos.add(Todo("Pack Lunch"))

        _todoCollections.add(TodoCollection("Morning Routine", startingTodos))
    }

    fun updateNewCollectionName(name: String) = action {
        _newCollectionName = name
    }

    fun createNewCollection() = action {
        _todoCollections.add(TodoCollection(_newCollectionName))
        _newCollectionName = ""
    }

    fun updateNewTodoText(text: String) = action {
        _newTodoText = text
    }

    fun createNewTodo() = action {
        selectedTodoCollection?.todos?.add(Todo(_newTodoText))
        _newTodoText = ""
    }

    fun selectTodoCollection(index: Int) = action {
        _selectedTodoCollectionIndex = index
    }

    fun toggleComplete(index: Int) = action {
        val collection = selectedTodoCollection
        if (collection != null) {
            val todo = collection.todos[index]
            todo.completed = !todo.completed
        }
    }
}

val connector = StoreConnector(TodoStore())