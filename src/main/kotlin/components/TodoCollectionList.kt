package components

import TodoCollection
import kotlinx.html.js.onClickFunction
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.aside
import react.dom.h3
import react.dom.li
import react.dom.ul

class TodoCollectionList : RComponent<TodoCollectionListProps, RState>() {
    override fun RBuilder.render() {
        aside {
            h3 {
                +"Collections"
            }

            ul {
                props.collectionList.mapIndexed { index: Int, collection: TodoCollection ->
                    li {
                        +collection.name

                        attrs {
                            onClickFunction = { props.onListSelected(index) }
                        }
                    }
                }
            }
        }
    }
}

class TodoCollectionListProps(
        var collectionList: ArrayList<TodoCollection>,
        var onListSelected: (index: Int) -> Unit
) : RProps

fun RBuilder.todoCollectionList(
        collectionList: ArrayList<TodoCollection>,
        onListSelected: (index: Int) -> Unit) = child(TodoCollectionList::class) {
    attrs.collectionList = collectionList
    attrs.onListSelected = onListSelected
}