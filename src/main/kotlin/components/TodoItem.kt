package components

import components.styles.TodoStyles
import kotlinx.css.Align
import kotlinx.css.Display
import kotlinx.css.JustifyContent
import kotlinx.html.InputType
import kotlinx.html.js.onClickFunction
import react.RBuilder
import react.dom.span
import styled.css
import styled.styledInput
import styled.styledLabel
import styled.styledLi

fun RBuilder.todoItem(text: String = "", completed: Boolean = false, onCheckboxClick: () -> Unit = {}, onDeleteClicked: () -> Unit = {}) {
    styledLi {
        css {
            +TodoStyles.todoListItem
        }

        styledLabel {
            css {
                display = Display.flex
                justifyContent = JustifyContent.spaceBetween
                alignItems = Align.center
            }

            span {
                +text
            }

            span {
                styledInput {
                    css {
                        +TodoStyles.completeToggle
                    }

                    attrs {
                        type = InputType.checkBox
                        checked = completed
                        onClickFunction = { onCheckboxClick() }
                    }
                }

                deleteButton {
                    it.stopPropagation()
                    onDeleteClicked()
                }
            }
        }
    }
}