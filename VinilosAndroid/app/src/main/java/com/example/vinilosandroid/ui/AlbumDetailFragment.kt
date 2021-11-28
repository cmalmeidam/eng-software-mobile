package com.example.vinilosandroid.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.vinilosandroid.R
import com.example.vinilosandroid.databinding.AlbumDetailFragmentBinding
import com.example.vinilosandroid.models.Album


class AlbumDetailFragment : Fragment() {

    private var _binding: AlbumDetailFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AlbumDetailFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        activity.actionBar?.title = getString(R.string.tialbumes)

        val args: AlbumDetailFragmentArgs by navArgs()
       _binding?.album = Album(
            args.albumId,
            args.name,
            args.cover,
            args.releaseDate.substring(8, 10) + "-" +
                    args.releaseDate.substring(5, 7) + "-" +
                    args.releaseDate.substring(0, 4),
            args.descripcion,
            args.genre,
            args.recordLabel
        )
        Glide.with(activity)
            .load(args.cover)
            .apply(
                RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .placeholder(R.drawable.ic_album2)
                    .error(R.drawable.ic_album2))
            .into(_binding!!.itemCoverIv)
        _binding!!.btnTracks!!.setOnClickListener{
            val action = AlbumDetailFragmentDirections.actionAlbumDetailFragmentToTrackFragment(args.albumId, args.name)
            _binding!!.root.findNavController().navigate(action)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
