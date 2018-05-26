package components

import components.styles.TodoStyles
import kotlinx.html.Entities
import kotlinx.html.js.onClickFunction
import org.w3c.dom.events.Event
import react.RBuilder
import styled.css
import styled.styledButton

fun RBuilder.deleteButton(onClick: (Event) -> Unit) {
    styledButton {
        css {
            +TodoStyles.deleteButton
        }

        consumer.onTagContentUnsafe {
            +Entities.times
        }

        attrs {
            onClickFunction = {
                onClick(it)
            }
        }
    }
}