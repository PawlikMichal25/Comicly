package io.spacecowboyapps.comicly.ui.comic

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import io.spacecowboyapps.comicly.R
import io.spacecowboyapps.comicly.data.db.Comic
import kotlinx.android.synthetic.main.comic_item.view.*
import javax.inject.Inject

class ComicsAdapter
@Inject constructor(
    private val picasso: Picasso
) : RecyclerView.Adapter<ComicsAdapter.ViewHolder>() {

    private var comics: List<Comic> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comic_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = comics.size

    fun setComics(comics: List<Comic>) {
        this.comics = comics
    }

    fun updateComics(comics: List<Comic>) {
        setComics(comics)
        notifyDataSetChanged()
    }

    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val backgroundView = item.imageView_comic_background
        private val titleView = item.textView_comic_title

        fun bind(position: Int) {
            val comic = comics[position]
            picasso
                .load(comic.thumbnail.getCompleteUrl())
                .placeholder(R.drawable.comic_placeholder)
                .into(backgroundView)

            titleView.text = comic.title
        }
    }
}