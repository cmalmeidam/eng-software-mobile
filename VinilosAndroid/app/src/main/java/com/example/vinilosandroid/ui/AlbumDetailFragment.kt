package com.example.vinilosandroid.ui

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.vinilosandroid.R
import com.example.vinilosandroid.databinding.AlbumDetailFragmentBinding
import com.example.vinilosandroid.models.Album
import com.squareup.picasso.Picasso

/**
 * A simple [Fragment] subclass.
 * Use the [AlbumDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AlbumDetailFragment : Fragment() {

    private lateinit var binding: AlbumDetailFragmentBinding

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        activity.actionBar?.title = getString(R.string.tialbumes)
        val args: AlbumDetailFragmentArgs by navArgs()

       binding.album = Album(
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
        Picasso.get()
            .load(args.cover)
            .placeholder(R.drawable.ic_album2)
            .error(R.drawable.ic_album2)
            .into(binding.imageAlbum)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AlbumDetailFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

}