package components

import Todo
import TodoCollection
import TodoStore
import connector
import kotlinx.css.px
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.h3
import styled.css
import styled.styledUl

class SelectedTodo : RComponent<SelectedTodoProps, RState>() {
    override fun RBuilder.render() {
        if (props.selectedTodoCollection != null) {
            h3 {
                +props.selectedTodoCollection.name
            }

            styledUl {
                css {
                    paddingLeft = 20.px
                }

                props.selectedTodoCollection.todos.mapIndexed { index: Int, todo: Todo ->
                    todoItem(todo.text, todo.completed, { props.toggleComplete(index) })
                }
            }
        }
    }
}

class SelectedTodoProps(
        val selectedTodoCollection: TodoCollection?,
        val toggleComplete: (index: Int) -> Unit = {}
) : RProps

fun RBuilder.selectedTodo() = connector.connect(this, SelectedTodo::class, { store: TodoStore ->
    SelectedTodoProps(
            store.selectedTodoCollection,
            { index: Int -> store.toggleComplete(index) }
    )
})