package components

import TodoCollection
import components.styles.TodoStyles
import kotlinx.css.*
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import kotlinx.html.js.onSubmitFunction
import org.w3c.dom.HTMLInputElement
import react.RBuilder
import react.RProps
import react.RState
import react.dom.form
import react.dom.h3
import store.RStoreListener
import styled.*

class TodoCollectionList : RStoreListener<RProps, RState>() {
    override fun RBuilder.render() {
        styledAside {
            css {
                marginRight = 20.px
            }

            h3 {
                +"Collections"
            }

            form {
                attrs {
                    onSubmitFunction = {
                        it.preventDefault()
                        store.createNewCollection()
                    }
                }

                styledInput {
                    css {
                        +TodoStyles.input
                    }

                    attrs {
                        onChangeFunction = {
                            val value = (it.target as HTMLInputElement).value
                            store.updateNewCollectionName(value)
                        }
                        value = store.newCollectionName
                        placeholder = "New Collection"
                    }
                }
            }

            styledUl {
                css {
                    listStyleType = ListStyleType.none
                    padding(0.px)
                }

                store.todoCollections.mapIndexed { index: Int, collection: TodoCollection ->
                    styledLi {
                        css {
                            +TodoStyles.todoCollectionListItem

                            display = Display.flex
                            justifyContent = JustifyContent.spaceBetween
                            alignItems = Align.center
                        }
                        +collection.name

                        deleteButton {
                            it.stopPropagation()
                            store.startDeleteCollection(index)
                        }

                        attrs {
                            onClickFunction = { store.selectTodoCollection(index) }
                        }
                    }
                }
            }
        }
    }
}

fun RBuilder.todoCollectionList() = child(TodoCollectionList::class) {}