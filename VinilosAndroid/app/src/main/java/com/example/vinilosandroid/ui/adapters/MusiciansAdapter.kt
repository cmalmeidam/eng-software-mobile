package com.example.vinilosandroid.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.vinilosandroid.R
import com.example.vinilosandroid.databinding.MusicianItemBinding
import com.example.vinilosandroid.models.Musician
import com.example.vinilosandroid.ui.MusicianFragmentDirections
import com.example.vinilosandroid.ui.navigateSafe


class MusiciansAdapter : RecyclerView.Adapter<MusiciansAdapter.MusicianViewHolder>(){
    var musicians :List<Musician> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MusicianViewHolder {
        val withDataBinding:MusicianItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            MusicianViewHolder.LAYOUT,
            parent,
            false
        )
        return MusicianViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: MusicianViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.musician = musicians[position]
        }
        holder.bind(musicians[position])
        val musician: Musician = musicians[position]
        holder.viewDataBinding.root.setOnClickListener {
            val action = MusicianFragmentDirections.actionMusicianFragmentToMusicianDetailFragment(
                musician.musicianId,
                musician.birthDate,
                musician.description,
                musician.image,
                musician.name
            )
            holder.viewDataBinding.root.findNavController().navigateSafe(action.actionId, action.arguments)
        }
    }

    override fun getItemCount(): Int {
        return musicians.size
    }
    class MusicianViewHolder(val viewDataBinding: MusicianItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.musician_item
        }
        fun bind(musician: Musician) {
            Glide.with(itemView)
                .load(musician.image.toUri().buildUpon().scheme("https").build())
                .apply(
                    RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.ic_artist)
                        .error(R.drawable.ic_face))
                .into(viewDataBinding.itemImageIv)
        }
    }
}