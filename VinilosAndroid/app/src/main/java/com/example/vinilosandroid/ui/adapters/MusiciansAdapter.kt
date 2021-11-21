package com.example.vinilosandroid.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilosandroid.R
import com.example.vinilosandroid.databinding.MusicianItemBinding
import com.example.vinilosandroid.models.Musician
import com.example.vinilosandroid.ui.MusicianFragmentDirections
import com.squareup.picasso.Picasso


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
            Picasso.get()
                .load(it.musician?.image)
                .placeholder(R.drawable.ic_artist)
                .error(R.drawable.ic_face)
                .into(it.itemImageIv)
        }
        val musician: Musician = musicians[position]
        holder.viewDataBinding.root.setOnClickListener {
            val action = MusicianFragmentDirections.actionMusicianFragmentToMusicianDetailFragment(
                musician.musicianId,
                musician.birthDate,
                musician.description,
                musician.image,
                musician.name
            )
            holder.viewDataBinding.root.findNavController().navigate(action)
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
    }

}