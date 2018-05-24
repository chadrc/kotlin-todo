package components

import TodoCollection
import components.styles.TodoStyles
import connector
import kotlinx.css.ListStyleType
import kotlinx.css.padding
import kotlinx.css.px
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import kotlinx.html.js.onSubmitFunction
import org.w3c.dom.HTMLInputElement
import react.*
import react.dom.*
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
                        }
                        +collection.name

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
        var createNewCollection: () -> Unit = {}
) : RProps

fun RBuilder.todoCollectionList() = connector.connect(this, TodoCollectionList::class, { store ->
    TodoCollectionListProps(
            store.todoCollections,
            { index: Int -> store.selectTodoCollection(index) },
            store.newCollectionName,
            { name: String -> store.updateNewCollectionName(name) },
            { store.createNewCollection() }
    )
})