package components

import kotlinx.css.*
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.h1
import styled.css
import styled.styledDiv
import styled.styledSection

class TodoApp : RComponent<RProps, RState>() {
    override fun RBuilder.render() {
        styledSection {
            css {
                display = Display.flex
                flexDirection = FlexDirection.column
                alignItems = Align.center

                width = 80.pct
                margin(LinearDimension.auto)
            }

            h1 {
                +"Todo App"
            }

            styledSection {
                css {
                    display = Display.flex
                    width = 100.pct
                }

                styledDiv {
                    css {
                        flexGrow = 1.0
                    }

                    todoCollectionList()
                }

                styledSection {
                    css {
                        flexGrow = 2.0
                    }

                    selectedTodo()
                }
            }
        }
    }
}

fun RBuilder.todoApp() = child(TodoApp::class) {}