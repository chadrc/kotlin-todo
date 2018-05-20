package components

import Todo
import TodoCollection
import kotlinx.css.*
import react.*
import react.dom.*
import styled.css
import styled.styledDiv
import styled.styledSection
import styled.styledUl

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

    private fun selectTodoCollection(index: Int) {
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
        styledSection {
            css {
                display = Display.flex
                flexDirection = FlexDirection.column
                alignItems = Align.center

                width = 80.pct
                margin(LinearDimension.auto)
            }

            h1 {
                +"Todo App"
            }

            styledSection {
                css {
                    display = Display.flex
                    width = 100.pct
                }

                styledDiv {
                    css {
                        flexGrow = 1.0
                    }

                    todoCollectionList(state.todoCollections, { index: Int -> selectTodoCollection(index) })
                }

                styledSection {
                    css {
                        flexGrow = 2.0
                    }

                    if (state.selectedTodoCollectionIndex != -1) {
                        h3 {
                            +selectedTodoCollection.name
                        }

                        styledUl {
                            css {
                                paddingLeft = 20.px
                            }

                            selectedTodoCollection.todos.mapIndexed { index: Int, todo: Todo ->
                                todoItem(todo.text, todo.completed, { toggleComplete(index) })
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

fun RBuilder.todoApp() = child(TodoApp::class) {}