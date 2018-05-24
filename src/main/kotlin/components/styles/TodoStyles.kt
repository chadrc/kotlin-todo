package components.styles

import extensions.rem
import kotlinx.css.*
import kotlinx.css.properties.borderBottom
import styled.StyleSheet

object TodoStyles : StyleSheet("TodoStyles", isStatic = true) {
    val todoListItem by css {
        fontSize = 1.25.rem
        cursor = Cursor.default
        borderBottom(2.px, BorderStyle.solid, Color.white)

        hover {
            borderBottomColor = Color.black
        }

        label {
            padding(0.5.rem)
        }
    }

    val todoCollectionListItem by css {
        fontSize = 1.25.rem
        borderBottom(2.px, BorderStyle.solid, Color.white)
        padding(0.5.rem)
        cursor = Cursor.default

        hover {
            borderBottomColor = Color.black
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
}