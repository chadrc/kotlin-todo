package components.styles

import extensions.rem
import kotlinx.css.*
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

    val todoCollectionListItem by css {
        +item

        padding(0.5.rem)
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
}