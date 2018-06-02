package components

import StoreResourceLoader
import components.styles.TodoStyles
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
import todoStore

class DeleteConfirmationModal : RComponent<RProps, RState>() {
    private val indexToDelete: Int by StoreResourceLoader()

    override fun RBuilder.render() {
        if (indexToDelete != -1) {
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
                                onClickFunction = { todoStore.cancelDelete() }
                            }
                        }

                        button {
                            +"Yes"

                            attrs {
                                onClickFunction = { todoStore.confirmDelete() }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun RBuilder.deleteConfirmationModal() = child(DeleteConfirmationModal::class) {}