package com.example.vinilosandroid.ui

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.vinilosandroid.R
import com.example.vinilosandroid.databinding.FragmentCreateAlbumBinding
import com.example.vinilosandroid.repositories.AlbumsRepository
import java.text.SimpleDateFormat


class CreateAlbum : Fragment() {
    private var _binding: FragmentCreateAlbumBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateAlbumBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        activity.actionBar?.title = getString(R.string.createAlbumMenuTitle)

        _binding!!.crearAlbumBtn.setOnClickListener {
            val isoDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")

            val albumnName = _binding!!.albumname.text.toString()
            val albumCover = _binding!!.albumcover.text.toString()
            val albumGenre = _binding!!.genreSelector.selectedItem.toString()
            val albumDescription = _binding!!.albumdescription.text.toString()
            val albumRecordLabel = _binding!!.recordLabelSelector.selectedItem.toString()
            var albumDate = isoDate.format(SimpleDateFormat("dd/MM/yyyy").parse(_binding!!.albumReleaseDate.text.toString()))
            postDataFromNetwork(activity.application, albumnName, albumCover, albumGenre, albumDescription, albumRecordLabel, albumDate)
            val action = CreateAlbumDirections.actionCreateAlbumToAlbumFragment()
            _binding!!.crearAlbumBtn.findNavController().navigate(action)
        }
    }

    private fun postDataFromNetwork(
        application: Application,
        albumnName: String,
        albumCover: String,
        albumGenre: String,
        albumDescription: String,
        albumRecordLabel: String,
        albumDate: String
    ) {
        val albumsRepository =AlbumsRepository(application)
        albumsRepository.postData({

        },{
        }, albumnName, albumCover, albumGenre, albumDescription, albumRecordLabel, albumDate)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
