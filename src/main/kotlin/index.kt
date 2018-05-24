import components.styles.TodoStyles
import components.todoApp
import kotlinext.js.invoke
import kotlinx.css.CSSBuilder
import kotlinx.css.body
import kotlinx.css.px
import react.dom.render
import styled.StyledComponents
import kotlin.browser.document

external var Kotlin: dynamic

val styles = CSSBuilder().apply {
    body {
        fontSize = 16.px
        fontFamily = "Arial"
    }
}

fun main(args: Array<String>) {
    render(document.getElementById("content")) {
        todoApp()
    }

    // Suppressing "defineModule not a function" error until it can be fixed
    Kotlin.defineModule = {module: String -> module}

    StyledComponents.injectGlobal(TodoStyles.global.toString())
}