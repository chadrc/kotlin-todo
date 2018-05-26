package components

import components.styles.TodoStyles
import kotlinx.css.Align
import kotlinx.css.Display
import kotlinx.css.JustifyContent
import kotlinx.html.InputType
import kotlinx.html.js.onClickFunction
import react.RBuilder
import react.dom.span
import styled.*

fun RBuilder.todoItem(
        text: String = "",
        completed: Boolean = false,
        onCheckboxClick: () -> Unit = {},
        onDeleteClicked: () -> Unit = {}
) {
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
                        display = Display.none
                    }

                    attrs {
                        type = InputType.checkBox
                        checked = completed
                        onClickFunction = { onCheckboxClick() }
                    }
                }

                styledSpan {
                    css {
                        +TodoStyles.completeToggle
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