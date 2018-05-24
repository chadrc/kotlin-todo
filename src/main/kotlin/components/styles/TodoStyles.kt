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