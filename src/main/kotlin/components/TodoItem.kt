package components

import components.styles.TodoStyles
import kotlinx.css.Display
import kotlinx.css.JustifyContent
import kotlinx.html.InputType
import kotlinx.html.js.onClickFunction
import react.RBuilder
import react.dom.input
import react.dom.span
import styled.css
import styled.styledLabel
import styled.styledLi

fun RBuilder.todoItem(text: String = "", completed: Boolean = false, onClick: () -> Unit = {}) {
    styledLi {
        css {
            +TodoStyles.todoListItem
        }

        styledLabel {
            css {
                display = Display.flex
                justifyContent = JustifyContent.spaceBetween
            }

            span {
                +text
            }

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