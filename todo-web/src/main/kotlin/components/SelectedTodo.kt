package components

import Todo
import components.styles.TodoStyles
import kotlinx.css.ListStyleType
import kotlinx.css.padding
import kotlinx.css.px
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onSubmitFunction
import org.w3c.dom.HTMLInputElement
import react.RBuilder
import react.RProps
import react.RState
import react.dom.form
import react.dom.h3
import store.RStoreListener
import styled.css
import styled.styledInput
import styled.styledUl

class SelectedTodo : RStoreListener<RProps, RState>() {
    override fun RBuilder.render() {
        val selectedTodoCollection = store.selectedTodoCollection
        if (selectedTodoCollection != null) {
            h3 {
                +selectedTodoCollection.name
            }

            form {
                attrs {
                    onSubmitFunction = {
                        it.preventDefault()
                        store.createNewTodo()
                    }
                }

                styledInput {
                    css {
                        +TodoStyles.input
                    }

                    attrs {
                        onChangeFunction = {
                            val value = (it.target as HTMLInputElement).value
                            store.updateNewTodoText(value)
                        }
                        value = store.newTodoText
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
                    todoItem(todo.text, todo.completed, { store.toggleComplete(index) }, { store.startDeleteTodo(index) })
                }
            }
        }
    }
}

fun RBuilder.selectedTodo() = child(SelectedTodo::class) {}