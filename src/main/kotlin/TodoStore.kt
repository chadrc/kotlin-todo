import store.Store
import store.StoreConnector

class TodoStore : Store() {
    var todoCollections: ArrayList<TodoCollection> = ArrayList()
    var selectedTodoCollectionIndex: Int = -1
    var newCollectionName: String = ""
    var newTodoText: String = ""

    val selectedTodoCollection: TodoCollection?
        get() = if (selectedTodoCollectionIndex == -1) null else todoCollections[selectedTodoCollectionIndex]

    init {
        todoCollections = ArrayList()
        val startingTodos: ArrayList<Todo> = ArrayList()

        startingTodos.add(Todo("Make Coffee"))
        startingTodos.add(Todo("Eat Breakfast"))
        startingTodos.add(Todo("Pack Lunch"))

        todoCollections.add(TodoCollection("Morning Routine", startingTodos))
    }

    fun updateNewCollectionName(name: String) = action {
        newCollectionName = name
    }

    fun createNewCollection() = action {
        todoCollections.add(TodoCollection(newCollectionName))
        newCollectionName = ""
    }

    fun updateNewTodoText(text: String) = action {
        newTodoText = text
    }

    fun createNewTodo() = action {
        selectedTodoCollection?.todos?.add(Todo(newTodoText))
        newTodoText = ""
    }

    fun selectTodoCollection(index: Int) = action {
        selectedTodoCollectionIndex = index
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