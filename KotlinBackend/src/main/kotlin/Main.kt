import com.google.gson.Gson
import khttp.get
import org.jetbrains.ktor.host.embeddedServer
import org.jetbrains.ktor.http.ContentType
import org.jetbrains.ktor.netty.Netty
import org.jetbrains.ktor.response.header
import org.jetbrains.ktor.response.respondText
import org.jetbrains.ktor.routing.get
import org.jetbrains.ktor.routing.routing

fun main(args: Array<String>) {
    val endpoint = "https://vip.bitcoin.co.id/api/"
    embeddedServer(Netty, 8080) {
        routing {
            get("/api/{market?}") {
                var market: String = call.parameters["market"]?:"btc_idr"
                val endpoint_market = endpoint + market +"/ticker"
                val res = get(endpoint_market)

                var json: String
                if (res.statusCode == 200) {
                    json = res.text

                    var gson = Gson()
                    var jsonObj = gson.fromJson(json, Bitcoin)
                }
                else json = "{\"error\": true, \"message\": \"An error occurred\"}"

                call.response.header("Access-Control-Allow-Origin", "*")
                call.respondText(json, ContentType.Application.Json)
            }
        }
    }.start(true)
}

class Bitcoin {
    var ticker: Ticker? = null

    inner class Ticker {
        var high: String? = null
        var low: String? = null
        var vol_btc: String? = null
        var vol_idr: String? = null
        var last: String? = null
        var buy: String? = null
        var sell: String? = null
        var server_time: String? = null
    }
}

