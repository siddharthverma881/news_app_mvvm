package com.example.okcreditassignment.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.okcreditassignment.R
import com.example.okcreditassignment.appData.Utils
import com.example.okcreditassignment.model.news.Article

class NewsAdapter(val context : Context, val list : List<Article>) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_news,parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = list[position]
        holder.tvHeadline.text = article.title
        if(!Utils.isEmpty(article.source?.name))
            holder.tvSource.text = "( ${article.source?.name} )"

        holder.tvTime.text = Utils.convertToLocal(article.publishedAt!!, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "yyyy-MM-dd hh:mm aa")
//        holder.tvTime.text = article.publishedAt

        Glide.with(context).asBitmap()
            .load(article.urlToImage)
            .apply(RequestOptions().centerCrop().fitCenter())
            .into(holder.ivNews)

    }

    override fun getItemCount() = list.size

    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvHeadline = itemView.findViewById<TextView>(R.id.tv_headline)
        val ivNews = itemView.findViewById<ImageView>(R.id.iv_news)
        val tvSource = itemView.findViewById<TextView>(R.id.tv_source)
        val tvTime = itemView.findViewById<TextView>(R.id.tv_time)
    }
}