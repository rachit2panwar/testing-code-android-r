package biz.filmeroo.premier.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import biz.filmeroo.premier.R
import biz.filmeroo.premier.api.FilmService
import biz.filmeroo.premier.api.SimilarMovie
import biz.filmeroo.premier.databinding.ItemSimilarMovieBinding
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import javax.inject.Inject

internal class SimilarFilmAdapter @Inject constructor(private val picasso: Picasso) :
    ListAdapter<SimilarMovie, SimilarFilmAdapter.Holder>(diffCallback) {

    private var onClick: ((Long) -> Unit)? = null

    fun setOnClick(onClick: (Long) -> Unit) {
        this.onClick = onClick
    }

    inner class Holder(private val similarMovieBinding: ItemSimilarMovieBinding) :
        RecyclerView.ViewHolder(similarMovieBinding.root) {
        fun bind(item: SimilarMovie) {
            similarMovieBinding.apply {
                movieNameTxt.text = item.title
                rating.text = String.format("%.1f", item.averageVote)
                val cornerRadius =
                    root.resources.getDimensionPixelSize(R.dimen.image_corner_radius_8dp)
                picasso
                    .load(FilmService.buildImageUrl(item.posterPath ?: ""))
                    .transform(RoundedCornersTransformation(cornerRadius, 0))
                    .into(similarMovieImg)
                root.setOnClickListener { onClick?.invoke(item.id) }
            }
        }
    }

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarFilmAdapter.Holder {
        val binding =
            ItemSimilarMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) = holder.bind(getItem(position))

    override fun getItemId(position: Int) = getItem(position).id

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<SimilarMovie>() {
            override fun areItemsTheSame(old: SimilarMovie, new: SimilarMovie) = old.id == new.id
            override fun areContentsTheSame(old: SimilarMovie, new: SimilarMovie) = old == new
        }
    }
}
