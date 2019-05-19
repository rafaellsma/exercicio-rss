package br.ufpe.cin.if710.rss

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.net.URL
import android.content.Intent
import android.net.Uri


const val RSS_FEED = "http://pox.globo.com/rss/g1/tecnologia/"

class MainActivity : Activity() {
    private lateinit var conteudoRSS: RecyclerView
    private lateinit var conteudoRSSAdapter: ConteudoRSSAdapter
    private lateinit var conteudoRSSManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        conteudoRSSAdapter = ConteudoRSSAdapter(listOf()) { item -> openWebPage(item) }
        conteudoRSSManager = LinearLayoutManager(this)
        conteudoRSS = findViewById<RecyclerView>(R.id.conteudoRSS).apply {
            setHasFixedSize(true)
            layoutManager = conteudoRSSManager
            adapter = conteudoRSSAdapter
        }
    }

    override fun onStart() {
        super.onStart()
        doAsync {
            val listRSS = ParserRSS.parse(getRssFeed(RSS_FEED))
            uiThread {
                conteudoRSSAdapter.setList(listRSS)
                conteudoRSSAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun openWebPage(itemRSS: ItemRSS) {
        print(itemRSS)

        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(itemRSS.link))
        startActivity(browserIntent)
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
