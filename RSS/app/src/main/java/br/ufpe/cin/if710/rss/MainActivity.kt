package br.ufpe.cin.if710.rss

import android.app.Activity
import android.os.Bundle
import android.widget.TextView
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.URL

const val RSS_FEED = "http://leopoldomt.com/if1001/g1brasil.xml";

class MainActivity : Activity() {
    var conteudoRSS: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        conteudoRSS = findViewById(R.id.conteudoRSS)
    }

    override fun onStart() {
        super.onStart()
        try {
            conteudoRSS?.setText(getRssFeed(RSS_FEED))
        } catch (e: IOException) {

            e.printStackTrace()
        }
    }

    private fun getRssFeed(feed: String): String {
        var inputStream: InputStream? = null
        var rssFeed: String

        try {
            val url = URL(feed)
            val conn = url.openConnection()
            inputStream = conn.getInputStream()
            val out = ByteArrayOutputStream()
            val buffer = ByteArray(1024)
            while (true) {
                val count = inputStream.read(buffer)
                if (count == -1) {
                    break
                }
                out.write(buffer, 0, count)
            }
            val response = out.toByteArray()
            rssFeed = String(response, charset("UTF-8"))
        } finally {
            inputStream?.close()
        }

        return rssFeed
    }
}
