import react.dom.*
import kotlin.browser.document

fun main(args: Array<String>) {
    render(document.getElementById("content")) {
        h1 {
            +"Hello Kotlin React"
        }
    }
}