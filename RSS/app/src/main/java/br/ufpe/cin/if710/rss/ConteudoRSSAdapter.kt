package br.ufpe.cin.if710.rss

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.jetbrains.anko.sdk25.coroutines.onClick

class ConteudoRSSAdapter(private var list: List<ItemRSS>, private var onClickTitle: (ItemRSS) -> Unit) : RecyclerView.Adapter<ConteudoRSSAdapter.ConteudoRSSViewHolder>() {
    class ConteudoRSSViewHolder(view: View, private var onClickTitle: (Int) -> Unit) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.item_titulo)
        private var date: TextView = view.findViewById(R.id.item_data)

        fun bind(itemRSS: ItemRSS) {
            title.text = itemRSS.title
            title.setOnClickListener {
                onClickTitle(adapterPosition)
            }
            date.text = itemRSS.pubDate
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConteudoRSSViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.itemlista, parent, false)
        return ConteudoRSSViewHolder(view) {
            val listItem = list[it]
            onClickTitle(listItem)
        }
    }

    override fun onBindViewHolder(holder: ConteudoRSSViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setList(list: List<ItemRSS>) {
        this.list = list
    }
}