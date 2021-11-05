package com.example.vinilosandroid.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilosandroid.R
import com.example.vinilosandroid.databinding.AlbumItemBinding
import com.example.vinilosandroid.models.Album
import com.example.vinilosandroid.ui.AlbumFragmentDirections
import com.squareup.picasso.Picasso




class AlbumsAdapter : RecyclerView.Adapter<AlbumsAdapter.AlbumViewHolder>(){
    var albums :List<Album> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val withDataBinding: AlbumItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            AlbumViewHolder.LAYOUT,
            parent,
            false)
        return AlbumViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.album = albums[position]
            Picasso.get()
                .load(it.album?.cover)
                .placeholder(R.drawable.ic_album)
                .error(R.drawable.ic_artist)
                .into(it.itemCoverIv)
        }
        holder.viewDataBinding.root.setOnClickListener {
        val action = AlbumFragmentDirections.actionAlbumFragmentToCollectorFragment()
        holder.viewDataBinding.root.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return albums.size
    }

    class AlbumViewHolder(val viewDataBinding: AlbumItemBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.album_item
        }
    }

}