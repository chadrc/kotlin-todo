package components

import TodoCollection
import kotlinx.css.ListStyleType
import kotlinx.css.padding
import kotlinx.css.px
import kotlinx.html.js.onClickFunction
import react.RBuilder
import react.dom.aside
import react.dom.h3
import react.dom.li
import styled.css
import styled.styledUl

fun RBuilder.todoCollectionList(
        collectionList: ArrayList<TodoCollection>,
        onListSelected: (index: Int) -> Unit) {
    aside {
        h3 {
            +"Collections"
        }

        styledUl {
            css {
                listStyleType = ListStyleType.none
                padding(0.px)
            }

            collectionList.mapIndexed { index: Int, collection: TodoCollection ->
                li {
                    +collection.name

                    attrs {
                        onClickFunction = { onListSelected(index) }
                    }
                }
            }
        }
    }
}