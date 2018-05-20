import components.todoApp
import react.dom.render
import kotlin.browser.document

external var Kotlin: dynamic

fun main(args: Array<String>) {
    render(document.getElementById("content")) {
        todoApp()
    }

    // Suppressing "defineModule not a function" error until it can be fixed
    Kotlin.defineModule = {module: String -> module}
}