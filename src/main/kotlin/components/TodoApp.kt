package components

import Todo
import TodoCollection
import kotlinx.html.InputType
import kotlinx.html.js.onClickFunction
import react.*
import react.dom.*

class TodoApp : RComponent<RProps, TodoAppState>() {

    init {
        val collections: ArrayList<TodoCollection> = ArrayList()
        val startingTodos: ArrayList<Todo> = ArrayList()

        startingTodos.add(Todo("Make Coffee"))
        startingTodos.add(Todo("Eat Breakfast"))
        startingTodos.add(Todo("Pack Lunch"))

        collections.add(TodoCollection("Morning Routine", startingTodos))

        state = TodoAppState(collections, -1)
    }

    private val selectedTodoCollection: TodoCollection
        get() = this.state.todoCollections[this.state.selectedTodoCollectionIndex]

    private fun selectTodo(index: Int) {
        setState {
            selectedTodoCollectionIndex = index
        }
    }

    private fun toggleComplete(index: Int) {
        val collection = selectedTodoCollection
        val todo = collection.todos[index]
        todo.completed = !todo.completed

        setState {
            todoCollections = state.todoCollections
        }
    }

    override fun RBuilder.render() {
        section {
            h1 {
                +"Todo App"
            }

            aside {
                h3 {
                    +"Collections"
                }

                ul {
                    state.todoCollections.mapIndexed { index: Int, collection: TodoCollection ->
                        li {
                            +collection.name

                            attrs {
                                onClickFunction = { selectTodo(index) }
                            }
                        }
                    }
                }
            }

            if (state.selectedTodoCollectionIndex != -1) {
                section {
                    h3 {
                        +selectedTodoCollection.name
                    }

                    ul {
                        selectedTodoCollection.todos.mapIndexed { index: Int, todo: Todo ->
                            li {
                                label {
                                    +todo.text

                                    input {
                                        attrs {
                                            type = InputType.checkBox
                                            checked = todo.completed
                                            onClickFunction = { toggleComplete(index)}
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

class TodoAppState(
        var todoCollections: ArrayList<TodoCollection>,
        var selectedTodoCollectionIndex: Int
) : RState

fun RBuilder.TodoApp() = child(TodoApp::class) {}