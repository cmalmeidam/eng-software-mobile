package com.example.vinilosandroid.ui

import android.app.Application
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.vinilosandroid.R
import com.example.vinilosandroid.databinding.FragmentCreateAlbumBinding
import com.example.vinilosandroid.repositories.AlbumsRepository
import java.lang.Exception
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
            if(!TextUtils.isEmpty(_binding!!.albumname.text) && !TextUtils.isEmpty(_binding!!.albumcover.text) && !TextUtils.isEmpty(_binding!!.albumdescription.text) && !TextUtils.isEmpty(_binding!!.albumReleaseDate.text)) {
                try {
                    val isoDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")

                    val albumnName = _binding!!.albumname.text.toString()
                    val albumCover = _binding!!.albumcover.text.toString()
                    val albumGenre = _binding!!.genreSelector.selectedItem.toString()
                    val albumDescription = _binding!!.albumdescription.text.toString()
                    val albumRecordLabel = _binding!!.recordLabelSelector.selectedItem.toString()
                    var albumDate = isoDate.format(SimpleDateFormat("dd/MM/yyyy").parse(_binding!!.albumReleaseDate.text.toString()))
                    postDataFromNetwork(activity.application, albumnName, albumCover, albumGenre, albumDescription, albumRecordLabel, albumDate)
                    val action = CreateAlbumDirections.actionCreateAlbumToAlbumDetailFragment(
                        0,
                        albumDescription,
                        albumCover,
                        albumGenre,
                        albumnName,
                        albumRecordLabel,
                        albumDate)
                    _binding!!.crearAlbumBtn.findNavController().navigate(action)
                } catch (e: Exception) {
                    Toast.makeText(activity,e.message, Toast.LENGTH_LONG).show()
                    println(e)
                }
            } else {
                if(TextUtils.isEmpty(_binding!!.albumname.text)) {
                    Toast.makeText(activity,"El nombre no puede estar vacio", Toast.LENGTH_LONG).show()
                }

                if(TextUtils.isEmpty(_binding!!.albumcover.text)) {
                    Toast.makeText(activity,"La url de la portada del album no puede estar vacia", Toast.LENGTH_LONG).show()
                }

                if(TextUtils.isEmpty(_binding!!.albumdescription.text)) {
                    Toast.makeText(activity,"La descripcion no puede estar vacia", Toast.LENGTH_LONG).show()
                }

                if(TextUtils.isEmpty(_binding!!.albumReleaseDate.text)) {
                    Toast.makeText(activity,"La fecha no puede estar vacia", Toast.LENGTH_LONG).show()
                }

            }
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
