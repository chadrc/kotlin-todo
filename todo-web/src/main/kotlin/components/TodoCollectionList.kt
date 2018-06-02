package components

import StoreResourceLoader
import TodoCollection
import components.styles.TodoStyles
import kotlinx.css.*
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import kotlinx.html.js.onSubmitFunction
import org.w3c.dom.HTMLInputElement
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.form
import react.dom.h3
import styled.*
import todoStore

class TodoCollectionList : RComponent<RProps, RState>() {
    private val todoCollections: ArrayList<TodoCollection>
            by StoreResourceLoader<RProps, RState, TodoCollectionList, ArrayList<TodoCollection>>()
    private val newCollectionName: String
            by StoreResourceLoader<RProps, RState, TodoCollectionList, String>()

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
                        todoStore.createNewCollection()
                    }
                }

                styledInput {
                    css {
                        +TodoStyles.input
                    }

                    attrs {
                        onChangeFunction = {
                            val value = (it.target as HTMLInputElement).value
                            todoStore.updateNewCollectionName(value)
                        }
                        value = newCollectionName
                        placeholder = "New Collection"
                    }
                }
            }

            styledUl {
                css {
                    listStyleType = ListStyleType.none
                    padding(0.px)
                }

                todoCollections.mapIndexed { index: Int, collection: TodoCollection ->
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
                            todoStore.startDeleteCollection(index)
                        }

                        attrs {
                            onClickFunction = { todoStore.selectTodoCollection(index) }
                        }
                    }
                }
            }
        }
    }
}

fun RBuilder.todoCollectionList() = child(TodoCollectionList::class) {}