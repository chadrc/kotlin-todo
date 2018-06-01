import org.w3c.fetch.Headers
import org.w3c.fetch.RequestInit
import kotlin.browser.window

object RequestHeaders {
    val headers: Headers = Headers()

    init {
        headers.append("Content-Type", "application/json")
        headers.append("Accept", "application/json")
    }
}

typealias Callback = (data: dynamic) -> Unit

fun fetch(url: String, method: String, body: Any? = null, cb: Callback?) {
    window.fetch(url, object : RequestInit {
        override var method: String? = method
        override var headers: Headers = RequestHeaders.headers
        override var body: dynamic = JSON.stringify(body)
    }).then {
        it.json().then {
            console.log("$url request successful", it)
            cb?.invoke(it)
        }.catch {
            console.error("$url request failed", it)
        }
    }.catch {
        console.error("$url request failed", it)
    }
}

fun getFetch(url: String, cb: Callback = {}) = fetch(url, "GET", cb = cb)
fun postFetch(url: String, body: Any, cb: Callback = {}) = fetch(url, "POST", body, cb)
fun patchFetch(url: String, body: Any, cb: Callback = {}) = fetch(url, "PATCH", body, cb)