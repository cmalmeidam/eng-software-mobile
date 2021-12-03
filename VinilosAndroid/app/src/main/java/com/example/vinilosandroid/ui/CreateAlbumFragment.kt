package com.example.vinilosandroid.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.vinilosandroid.R
import com.example.vinilosandroid.databinding.FragmentCreateAlbumBinding
import com.example.vinilosandroid.models.Album
import com.example.vinilosandroid.viewmodels.AlbumViewModel
import java.text.SimpleDateFormat


class CreateAlbumFragment : Fragment() {
    private var _binding: FragmentCreateAlbumBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AlbumViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateAlbumBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.root.announceForAccessibility(getString(R.string.createAlbum))
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        viewModel = ViewModelProvider(this, AlbumViewModel.Factory(activity.application)).get(AlbumViewModel::class.java)
        _binding!!.crearAlbumBtn.setOnClickListener {
        processInformation()
        }
        viewModel.eventNetworkError.observe(viewLifecycleOwner, { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })
    }
    fun processInformation(){
        if(!TextUtils.isEmpty(_binding!!.albumname.text) && !TextUtils.isEmpty(_binding!!.albumcover.text) && !TextUtils.isEmpty(_binding!!.albumdescription.text) && !TextUtils.isEmpty(_binding!!.albumRelease.text)) {
            try {
                var albumDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(SimpleDateFormat("dd/MM/yyyy").parse(_binding!!.albumRelease.text.toString()))
                var album= Album(0, _binding!!.albumname.text.toString(), _binding!!.albumcover.text.toString(),albumDate,  _binding!!.albumdescription.text.toString(), _binding!!.genreSelector.selectedItem.toString(), _binding!!.recordLabelSelector.selectedItem.toString())
                println(album)
                viewModel.inicio(album)
                val action = CreateAlbumFragmentDirections.actionCreateAlbumToAlbumDetailFragment(
                    0,
                    album.description,
                    album.cover,
                    album.genre,
                    album.name,
                    album.recordLabel,
                    album.releaseDate)
                _binding!!.crearAlbumBtn.findNavController().navigate(action)
            } catch (e: Exception) {
                Toast.makeText(activity,e.message, Toast.LENGTH_LONG).show()
                println(e)
            }
        } else {
            when(TextUtils.isEmpty(_binding!!.albumname.text) || TextUtils.isEmpty(_binding!!.albumcover.text) || TextUtils.isEmpty(_binding!!.albumdescription.text) || TextUtils.isEmpty(_binding!!.albumRelease.text)){
                TextUtils.isEmpty(_binding!!.albumname.text) -> Toast.makeText(activity,"El nombre no puede estar vacio", Toast.LENGTH_LONG).show()
                TextUtils.isEmpty(_binding!!.albumRelease.text) -> Toast.makeText(activity,"La fecha no puede estar vacia", Toast.LENGTH_LONG).show()
                TextUtils.isEmpty(_binding!!.albumcover.text) -> Toast.makeText(activity,"La url de la portada del album no puede estar vacia", Toast.LENGTH_LONG).show()
                TextUtils.isEmpty(_binding!!.albumdescription.text) -> Toast.makeText(activity,"La descripcion no puede estar vacia", Toast.LENGTH_LONG).show()

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}
