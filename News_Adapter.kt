package com.example.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide

class News_Adapter( private val listener:NewsItemClicked): RecyclerView.Adapter<Viewholder>() {

     val items : ArrayList<News> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news , parent , false)
        val viewholder = Viewholder(view)
        view.setOnClickListener{
            //pass an interface through the listener
            //to know what item is clicked we have to use callback.
            listener.onItemclicked(items[viewholder.adapterPosition])
        }
        return viewholder
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val currentView = items[position]
        holder.titleView.text = currentView.title
        holder.author.text = currentView.author
        Glide.with(holder.itemView.context).load(currentView.urlToImage).into(holder.image)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateNews(updatenews: ArrayList<News>){
        items.clear()
        items.addAll(updatenews)

        notifyDataSetChanged() //to change the data everytime it loads.
    }

}
class Viewholder(itemView: View):RecyclerView.ViewHolder(itemView){

    val titleView : TextView = itemView.findViewById(R.id.title)
    val image:ImageView = itemView.findViewById(R.id.image1)
    val author : TextView = itemView.findViewById(R.id.author)

}

interface NewsItemClicked{
    fun onItemclicked(item:News)
    //abstract fun JsonObjectRequest(get: Int, url: String, nothing: Nothing?, function: () -> Unit, function1: () -> Unit, function2: () -> Unit): JsonObjectRequest

}