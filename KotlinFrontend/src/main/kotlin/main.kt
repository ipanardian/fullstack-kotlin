import org.w3c.dom.*
import org.w3c.dom.events.Event
import org.w3c.xhr.XMLHttpRequest
import kotlin.browser.document
import kotlin.browser.window
import kotlin.js.Json

fun main(args: Array<String>) {
    window.onload = {
        val head = document.getElementsByTagName("head")
//        head[0]?.appendChild(createStylesheetLink("style.css"))

        //bind elements
        val market = document.getElementById("market") as HTMLSelectElement
        fetch(market.value)

        //bind click listener on button
        market.addEventListener("change", fun(event: Event) {
            fetch(market.value)
        })
    }
}

fun fetch(market: String): Unit {
    val url = "http://localhost:8080/api/$market"
    val req = XMLHttpRequest()
    req.onloadend = fun(event: Event){
        val text = req.responseText
        println(text)
//        val objArray  = JSON.parse<Array<Json>>(text)
        val textarea = document.getElementById("textarea_id") as HTMLTextAreaElement
        textarea.value = text
    }
    req.open("GET", url, true)
    req.send()
}

fun createStylesheetLink(filePath: String): Element {
    val style = document.createElement("link")
    style.setAttribute("rel", "stylesheet")
    style.setAttribute("href", filePath)
    return style
}

external fun alert(message: Any?): Unit