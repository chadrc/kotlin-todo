import store.Store

class TodoStore: Store() {
    var todoCollections: ArrayList<TodoCollection> = ArrayList()
    var selectedTodoCollectionIndex: Int = -1

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

    fun selectTodoCollection(index: Int) = action({
        selectedTodoCollectionIndex = index
    })

    fun toggleComplete(index: Int) = action({
        val collection = selectedTodoCollection
        if (collection != null) {
            val todo = collection.todos[index]
            todo.completed = !todo.completed
        }
    })
}
