package components.styles

import extensions.rem
import styled.StyleSheet

object TodoStyles : StyleSheet("TodoStyles", isStatic = true) {
    val listItem by css {
        fontSize = 1.25.rem
        marginBottom = .5.rem
    }
}