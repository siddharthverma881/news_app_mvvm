package com.example.okcreditassignment.ui.news

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.okcreditassignment.R
import com.example.okcreditassignment.appData.Utils
import com.example.okcreditassignment.model.news.Article

class NewsAdapter(val context : Context, val list : List<Article>, val listener : ArticleListener) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_news,parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = list[position]
        holder.tvHeadline.text = article.title
        if(!Utils.isEmpty(article.source?.name))
            holder.tvSource.text = "(${article.source?.name})"

        val timePair = Utils.getHoursAndMinutes(article.publishedAt!!)
        if(timePair.first == 0){
            holder.tvTime.text = "${timePair.second}m ago"
        } else if(timePair.second == 0){
            holder.tvTime.text = "${timePair.first}h ago"
        } else {
            holder.tvTime.text = "${timePair.first}h ${timePair.second}m ago"
        }

        Log.e("NewsAdapter", "${article.urlToImage}")
        holder.ivNews.load(article.urlToImage)

//        Glide.with(context).asBitmap()
//            .load(article.urlToImage)
//            .apply(RequestOptions().centerCrop().fitCenter())
//            .into(holder.ivNews)

        holder.clMain.setOnClickListener { listener.onArticleSelected(article) }

    }

    override fun getItemCount() = list.size

    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val clMain = itemView.findViewById<ConstraintLayout>(R.id.cl_main)
        val tvHeadline = itemView.findViewById<TextView>(R.id.tv_headline)
        val ivNews = itemView.findViewById<ImageView>(R.id.iv_news)
        val tvSource = itemView.findViewById<TextView>(R.id.tv_source)
        val tvTime = itemView.findViewById<TextView>(R.id.tv_time)
    }

    interface ArticleListener{
        fun onArticleSelected(article: Article)
    }
}