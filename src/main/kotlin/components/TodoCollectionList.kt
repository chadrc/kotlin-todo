package components

import TodoCollection
import connector
import kotlinx.css.ListStyleType
import kotlinx.css.padding
import kotlinx.css.px
import kotlinx.html.js.onClickFunction
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.aside
import react.dom.h3
import react.dom.li
import styled.css
import styled.styledUl

class TodoCollectionList: RComponent<TodoCollectionListProps, RState>() {
    override fun RBuilder.render() {

        aside {
            h3 {
                +"Collections"
            }

            styledUl {
                css {
                    listStyleType = ListStyleType.none
                    padding(0.px)
                }

                props.collectionList?.mapIndexed { index: Int, collection: TodoCollection ->
                    li {
                        +collection.name

                        attrs {
                            onClickFunction = { props.onListSelected?.invoke(index) }
                        }
                    }
                }
            }
        }
    }
}

class TodoCollectionListProps(
        var collectionList: ArrayList<TodoCollection>?,
        var onListSelected: ((index: Int) -> Unit)?
) : RProps

fun RBuilder.todoCollectionList() = connector.connect(this, TodoCollectionList::class, { store ->
    TodoCollectionListProps(
            store.todoCollections,
            { index: Int -> store.selectTodoCollection(index) }
    )
})