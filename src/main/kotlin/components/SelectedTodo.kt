package components

import Todo
import TodoCollection
import TodoStore
import components.styles.TodoStyles
import connector
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
import react.dom.input
import styled.css
import styled.styledInput
import styled.styledUl

class SelectedTodo : RComponent<SelectedTodoProps, RState>() {
    override fun RBuilder.render() {
        if (props.selectedTodoCollection != null) {
            h3 {
                +props.selectedTodoCollection.name
            }

            form {
                attrs {
                    onSubmitFunction = {
                        it.preventDefault()
                        props.createNewTodo()
                    }
                }

                styledInput {
                    css {
                        +TodoStyles.input
                    }

                    attrs {
                        onChangeFunction = {
                            val value = (it.target as HTMLInputElement).value
                            props.updateNewTodoText(value)
                        }
                        value = props.newTodoText
                        placeholder = "New Todo"
                    }
                }
            }

            styledUl {
                css {
                    listStyleType = ListStyleType.none
                    padding(0.px)
                }

                props.selectedTodoCollection.todos.mapIndexed { index: Int, todo: Todo ->
                    todoItem(todo.text, todo.completed, { props.toggleComplete(index) }, { props. startDeleteTodo(index) })
                }
            }
        }
    }
}

class SelectedTodoProps(
        val selectedTodoCollection: TodoCollection?,
        val toggleComplete: (index: Int) -> Unit = {},
        var newTodoText: String = "",
        var updateNewTodoText: (text: String) -> Unit = {},
        var createNewTodo: () -> Unit = {},
        var startDeleteTodo: (index: Int) -> Unit = {}
) : RProps

fun RBuilder.selectedTodo() = connector.connect(this, SelectedTodo::class, { store: TodoStore ->
    SelectedTodoProps(
            store.selectedTodoCollection,
            { index: Int -> store.toggleComplete(index) },
            store.newTodoText,
            { text: String -> store.updateNewTodoText(text) },
            { store.createNewTodo() },
            { index: Int -> store.startDeleteTodo(index)}
    )
})