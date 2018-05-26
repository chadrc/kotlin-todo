package components.styles

import extensions.rem
import kotlinx.css.*
import kotlinx.css.properties.border
import kotlinx.css.properties.borderBottom
import kotlinx.css.properties.s
import kotlinx.css.properties.transition
import styled.StyleSheet
import styled.animation

object TodoStyles : StyleSheet("TodoStyles", isStatic = true) {

    val global = CSSBuilder().apply {
        body {
            fontSize = 16.px
            fontFamily = "Arial"
        }
    }

    val todoListItem by css {
        +item

        label {
            padding(0.5.rem)
        }
    }

    val todoCollectionListItem by css {
        +item

        padding(0.5.rem)
    }

    private val item by css {
        fontSize = 1.25.rem
        borderBottom(2.px, BorderStyle.solid, Color.white)
        cursor = Cursor.default

        hover {
            borderBottomColor = Color.black
        }

        transition("border", .1.s)

        animation(duration = 0.25.s) {
            from {
                opacity = 0
            }

            to {
                opacity = 1.0
            }
        }
    }

    val input by css {
        fontSize = 1.25.rem

        width = 100.pct

        padding(0.5.rem)

        boxSizing = BoxSizing.borderBox
        borderStyle = BorderStyle.none
        borderBottomStyle = BorderStyle.solid
        outline = Outline.none
    }

    val completeToggle by css {
        adjacentSibling("span") {
            display = Display.inlineBlock
            verticalAlign = VerticalAlign.bottom

            width = 1.25.em
            height = 1.25.em

            backgroundColor = Color.white

            boxSizing = BoxSizing.borderBox

            border(3.px, BorderStyle.solid, rgb(0, 240, 0))
            borderRadius = 50.pct

            marginRight = .5.rem

            active {
                backgroundColor = rgb(0, 240, 0)
            }
        }

        adjacentSibling("span.completed") {
            backgroundColor = rgb(0, 240, 0)

            active {
                backgroundColor = Color.white
            }
        }
    }

    val deleteButton by css {
        fontSize = 1.25.rem
        width = 1.25.em
        height = 1.25.em

        borderStyle = BorderStyle.none
        outline = Outline.none

        color = Color.white
        backgroundColor = rgb(240, 0, 0)

        borderRadius = 50.pct

        hover {
            backgroundColor = rgb(220, 0, 0)
        }

        active {
            backgroundColor = rgb(200, 0, 0)
        }
    }

    val modal by css {
        position = Position.fixed
        top = 0.px
        left = 0.px

        width = 100.pct
        height = 100.pct

        backgroundColor = rgba(0, 0, 0, .25)

        animation(duration = .1.s) {
            from {
                opacity = 0
            }

            to {
                opacity = 1.0
            }
        }

        aside {
            display = Display.flex
            alignItems = Align.center
            flexDirection = FlexDirection.column

            backgroundColor = Color.white
            width = 50.pct
            margin(50.px, LinearDimension.auto)
            padding(20.px)
            boxSizing = BoxSizing.borderBox

            h3 {
                marginTop = 0.px
            }

            animation(duration = .2.s) {
                from {
                    opacity = 0
                }

                to {
                    opacity = 1.0
                }
            }

            section {
                display = Display.flex
                justifyContent = JustifyContent.center

                button {
                    cursor = Cursor.default
                    fontSize = 1.25.rem

                    borderStyle = BorderStyle.none
                    outline = Outline.none

                    marginRight = .5.rem
                    padding(.5.rem)

                    minWidth = 5.rem

                    backgroundColor = rgb(240, 240, 240)

                    hover {
                        backgroundColor = rgb(220, 220, 220)
                    }

                    active {
                        backgroundColor = rgb(200, 200, 200)
                    }

                    lastChild {
                        marginRight = 0.rem
                    }
                }
            }
        }
    }
}