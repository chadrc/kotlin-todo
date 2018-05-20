package components

import kotlinx.html.InputType
import kotlinx.html.js.onClickFunction
import react.RBuilder
import react.dom.input
import react.dom.label
import react.dom.li

fun RBuilder.todoItem(text: String, completed: Boolean, onClick: () -> Unit) {
    li {
        label {
            +text

            input {
                attrs {
                    type = InputType.checkBox
                    checked = completed
                    onClickFunction = { onClick() }
                }
            }
        }
    }
}