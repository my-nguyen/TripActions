package com.nguyen.tripactions

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.nguyen.tripactions.databinding.ItemArticleBinding

class ArticleAdapter(val context: Context, val articles: List<Article>) :
    RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    companion object {
        const val TAG = "ArticleAdapter"
    }

    inner class ViewHolder(private val binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val article = articles[layoutPosition]
            val intent = DetailActivity.newIntent(context, article)
            context.startActivity(intent)
        }

        fun bind(article: Article) {
            if (!TextUtils.isEmpty(article.imageUrl)) {
                val options = RequestOptions().transform(CenterCrop(), RoundedCorners(20))
                Glide.with(context)
                    .load(article.imageUrl)
                    .apply(options)
                    .into(binding.imageThumbnail)
            } else {
                binding.imageThumbnail.visibility = View.GONE
            }

            if (!article.subsection.isNullOrEmpty() || article.section.isNotEmpty()) {
                binding.textSubsection.visibility = View.VISIBLE
                if (!article.subsection.isNullOrEmpty()) {
                    binding.textSubsection.text = article.subsection
                } else {
                    binding.textSubsection.text = article.section
                }
            } else {
                binding.textSubsection.visibility = View.GONE
            }

            binding.textTitle.text = article.title
            if (article.abstract.isEmpty()) {
                binding.textAbstract.visibility = View.GONE
            } else {
                binding.textAbstract.visibility = View.VISIBLE
                binding.textAbstract.text = article.abstract
            }
            binding.textByline.text = article.byline
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = ItemArticleBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articles[position]
        holder.bind(article)
    }

    override fun getItemCount(): Int {
        return articles.size
    }
}
