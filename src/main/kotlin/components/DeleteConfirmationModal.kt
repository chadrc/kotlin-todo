package components

import components.styles.TodoStyles
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.aside
import react.dom.button
import react.dom.h3
import react.dom.section
import styled.css
import styled.styledDiv

class DeleteConfirmationModal : RComponent<RProps, RState>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                +TodoStyles.modal
            }

            aside {
                h3 {
                    +"Are you sure you want to delete?"
                }

                section {

                    button {
                        +"Cancel"
                    }

                    button {
                        +"Yes"
                    }
                }
            }
        }
    }
}

fun RBuilder.deleteConfirmationModal() = child(DeleteConfirmationModal::class) {}