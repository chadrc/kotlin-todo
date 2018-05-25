package components

import TodoCollection
import components.styles.TodoStyles
import connector
import kotlinx.css.*
import kotlinx.html.Entities
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import kotlinx.html.js.onSubmitFunction
import org.w3c.dom.HTMLInputElement
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.button
import react.dom.form
import react.dom.h3
import styled.*

class TodoCollectionList : RComponent<TodoCollectionListProps, RState>() {
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
                        props.createNewCollection()
                    }
                }

                styledInput {
                    css {
                        +TodoStyles.input
                    }

                    attrs {
                        onChangeFunction = {
                            val value = (it.target as HTMLInputElement).value
                            props.updateNewCollectionName(value)
                        }
                        value = props.newCollectionName
                        placeholder = "New Collection"
                    }
                }
            }

            styledUl {
                css {
                    listStyleType = ListStyleType.none
                    padding(0.px)
                }

                props.collectionList.mapIndexed { index: Int, collection: TodoCollection ->
                    styledLi {
                        css {
                            +TodoStyles.todoCollectionListItem

                            display = Display.flex
                            justifyContent = JustifyContent.spaceBetween
                        }
                        +collection.name

                        button {
                            consumer.onTagContentUnsafe {
                                +Entities.times
                            }

                            attrs {
                                onClickFunction = {
                                    it.stopPropagation()
                                    props.startDeleteConfirmation(index)
                                }
                            }
                        }

                        attrs {
                            onClickFunction = { props.onListSelected.invoke(index) }
                        }
                    }
                }
            }
        }
    }
}

class TodoCollectionListProps(
        var collectionList: ArrayList<TodoCollection> = ArrayList(),
        var onListSelected: ((index: Int) -> Unit) = {},
        var newCollectionName: String = "",
        var updateNewCollectionName: (name: String) -> Unit = {},
        var createNewCollection: () -> Unit = {},
        var startDeleteConfirmation: (index: Int) -> Unit = {}
) : RProps

fun RBuilder.todoCollectionList() = connector.connect(this, TodoCollectionList::class) { store ->
    TodoCollectionListProps(
            store.todoCollections,
            { index: Int -> store.selectTodoCollection(index) },
            store.newCollectionName,
            { name: String -> store.updateNewCollectionName(name) },
            { store.createNewCollection() },
            { index: Int -> store.startDeleteCollection(index)}
    )
}