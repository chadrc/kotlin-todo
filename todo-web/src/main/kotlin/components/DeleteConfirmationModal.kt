package components

import TodoStoreListener
import components.styles.TodoStyles
import kotlinx.html.js.onClickFunction
import react.RBuilder
import react.RProps
import react.RState
import react.dom.aside
import react.dom.button
import react.dom.h3
import react.dom.section
import styled.css
import styled.styledDiv

class DeleteConfirmationModal : TodoStoreListener<RProps, RState>() {
    override fun RBuilder.render() {
        if (store.indexToDelete != -1) {
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
                                onClickFunction = { store.cancelDelete() }
                            }
                        }

                        button {
                            +"Yes"

                            attrs {
                                onClickFunction = { store.confirmDelete() }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun RBuilder.deleteConfirmationModal() = child(DeleteConfirmationModal::class) {}