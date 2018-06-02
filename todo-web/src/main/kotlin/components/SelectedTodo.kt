package components

import StoreResourceLoader
import Todo
import components.styles.TodoStyles
import kotlinx.css.ListStyleType
import kotlinx.css.padding
import kotlinx.css.px
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onSubmitFunction
import org.w3c.dom.HTMLInputElement
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.form
import react.dom.h3
import styled.css
import styled.styledInput
import styled.styledUl
import todoStore

class SelectedTodo : RComponent<RProps, RState>() {
    private val selectedCollectionIndex: Int by StoreResourceLoader()
    private val newTodoText: String by StoreResourceLoader()

    override fun RBuilder.render() {
        val selectedTodoCollection = todoStore.selectedTodoCollection
        if (selectedTodoCollection != null) {
            h3 {
                +selectedTodoCollection.name
            }

            form {
                attrs {
                    onSubmitFunction = {
                        it.preventDefault()
                        todoStore.createNewTodo()
                    }
                }

                styledInput {
                    css {
                        +TodoStyles.input
                    }

                    attrs {
                        onChangeFunction = {
                            val value = (it.target as HTMLInputElement).value
                            todoStore.updateNewTodoText(value)
                        }
                        value = newTodoText
                        placeholder = "New Todo"
                    }
                }
            }

            styledUl {
                css {
                    listStyleType = ListStyleType.none
                    padding(0.px)
                }

                selectedTodoCollection.todos.mapIndexed { index: Int, todo: Todo ->
                    todoItem(todo.text, todo.completed, { todoStore.toggleComplete(index) }, { todoStore.startDeleteTodo(index) })
                }
            }
        }
    }
}

fun RBuilder.selectedTodo() = child(SelectedTodo::class) {}