package components

import kotlinx.html.InputType
import kotlinx.html.js.onClickFunction
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.input
import react.dom.label
import react.dom.li

class TodoItem: RComponent<TodoItemProps, RState>() {

    override fun RBuilder.render() {
        li {
            label {
                +props.text

                input {
                    attrs {
                        type = InputType.checkBox
                        checked = props.completed
                        onClickFunction = { props.onClick()}
                    }
                }
            }
        }
    }
}

class TodoItemProps(
        var text: String,
        var completed: Boolean,
        var onClick: () -> Unit
) : RProps

fun RBuilder.todoItem(text: String, completed: Boolean, onClick: () -> Unit) = child(TodoItem::class) {
    attrs.text = text
    attrs.completed = completed
    attrs.onClick = onClick
}