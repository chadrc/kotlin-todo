import react.dom.*
import kotlin.browser.document

external var Kotlin: dynamic

fun main(args: Array<String>) {
    render(document.getElementById("content")) {
        h1 {
            +"Hello Kotlin React"
        }
    }

    // Suppressing defineModule not a function error until it can be fixed
    Kotlin.defineModule = {module: String -> module}
}