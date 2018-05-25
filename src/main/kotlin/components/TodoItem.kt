package components

import components.styles.TodoStyles
import kotlinx.css.Display
import kotlinx.css.JustifyContent
import kotlinx.html.Entities
import kotlinx.html.InputType
import kotlinx.html.js.onClickFunction
import react.RBuilder
import react.dom.button
import react.dom.input
import react.dom.span
import styled.css
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
            }

            span {
                +text
            }

            span {
                input {
                    attrs {
                        type = InputType.checkBox
                        checked = completed
                        onClickFunction = { onCheckboxClick() }
                    }
                }

                button {
                    consumer.onTagContentUnsafe {
                        +Entities.times
                    }

                    attrs {
                        onClickFunction = {
                            it.stopPropagation()
                            onDeleteClicked()
                        }
                    }
                }
            }
        }
    }
}