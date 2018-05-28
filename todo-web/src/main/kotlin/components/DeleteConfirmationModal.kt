package components

import components.styles.TodoStyles
import connector
import kotlinx.html.js.onClickFunction
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

class DeleteConfirmationModal : RComponent<DeleteConfirmationModalProps, RState>() {
    override fun RBuilder.render() {
        if (props.confirmingDelete) {
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

                            attrs {
                                onClickFunction = { props.cancelDelete() }
                            }
                        }

                        button {
                            +"Yes"

                            attrs {
                                onClickFunction = { props.confirmDelete() }
                            }
                        }
                    }
                }
            }
        }
    }
}

class DeleteConfirmationModalProps(
        var confirmingDelete: Boolean = false,
        var confirmDelete: () -> Unit,
        var cancelDelete: () -> Unit
) : RProps

fun RBuilder.deleteConfirmationModal() = connector.connect(this, DeleteConfirmationModal::class) { store ->
    DeleteConfirmationModalProps(
            store.indexToDelete != -1,
            { store.confirmDelete() },
            { store.cancelDelete() }
    )
}