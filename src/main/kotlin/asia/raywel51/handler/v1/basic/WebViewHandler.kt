package asia.raywel51.handler.v1.basic

import io.ktor.server.application.*
import io.ktor.server.html.*
import kotlinx.html.*

class WebViewHandler {
    suspend fun welcomeView(c: ApplicationCall) {
        val osName = System.getProperty("os.name")

        c.respondHtml {
            head {
                meta(charset = "UTF-8")
                meta(name = "viewport", content = "width=device-width, initial-scale=1.0")
                title { +"Welcome to Ktor Framework!" }
                // Include Tailwind CSS from a CDN
                link(rel = "stylesheet", href = "https://cdn.jsdelivr.net/npm/tailwindcss@2.2.15/dist/tailwind.min.css")
                style {
                    unsafe {
                        raw("""
                    body {
                        display: flex;
                        flex-direction: column;
                        justify-content: center;
                        align-items: center;
                        height: 100vh;
                    }
                """.trimIndent())
                    }
                }
            }
            body(classes = "bg-gray-100") {
                div(classes = "text-center p-10") {
                    h1(classes = "text-4xl font-bold text-indigo-600") {
                        +"Welcome to Ktor Framework!"
                    }
                    hr(classes = "my-6")
                }
                div(classes = "text-center mt-4") {
                    a(href = "https://ktor.io/", classes = "text-lg text-blue-500") {
                        +"Ktor Framework/{{ 2.3.7 }} (on {{ $osName }})"
                    }
                }
            }
        }
    }
}