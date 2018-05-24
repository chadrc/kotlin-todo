import components.styles.TodoStyles
import components.todoApp
import kotlinext.js.invoke
import react.dom.render
import styled.StyledComponents
import kotlin.browser.document

external var Kotlin: dynamic

fun main(args: Array<String>) {
    render(document.getElementById("content")) {
        todoApp()
    }

    // Suppressing "defineModule not a function" error until it can be fixed
    Kotlin.defineModule = { module: String -> module }

    StyledComponents.injectGlobal(TodoStyles.global.toString())
}