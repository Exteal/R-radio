package com.example.radio2

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.client.request.post
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import io.ktor.http.headers
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.coroutines.launch

@Composable
fun createWebsocketClient(): HttpClient {
    val client = remember {
        HttpClient {
            install(WebSockets)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            client.close()
        }
    }

    return client
}
@Composable
fun ConnectedWebsocket() {

    var status by remember { mutableStateOf("Nothing connected!") }

    val client = createWebsocketClient()
    val scope = rememberCoroutineScope()

    Column {
        Text(status)

        Button(onClick = {
            val addr = "wss://listen.moe/gateway_v2"
            status = "Connecting..."

            scope.launch {
                try {
                    client.webSocket(addr) {
                        status = "Success"

                        for (frame in incoming) {
                            when (frame) {
                                is Frame.Text -> {
                                    println("Received ${frame.readText()}")
                                }
                                else -> {}
                            }

                        }
                    }
                }
                catch (e: Throwable) {
                    println("Error : ${e.message}")
                }
            }

        }) {
            Text("Connect")
        }
    }

}

@Composable
fun LookForStations() {
    val scope = rememberCoroutineScope()


    Button(onClick = {
        println("Looking for stations")
        scope.launch {
            val client = HttpClient()

            val resp = client.post("https://httpbin.org/post") {
                headers {
                    append(HttpHeaders.UserAgent, "Test Radio App")
                    append("Connection", "close")
                }
            }
            println(resp.bodyAsText())

        }

    }) {
        Text("Contact stations API")
    }

}