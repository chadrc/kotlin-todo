data class TodoCollection(
        var name: String,
        val todos: ArrayList<Todo> = ArrayList(),
        val id: String = ""
)