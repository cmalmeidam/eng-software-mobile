package com.example.vinilosandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilosandroid.databinding.FragmentCreateAlbumBinding
import android.R
import android.widget.Button
import android.widget.ImageButton


class CreateAlbum : Fragment() {
    private var _binding: FragmentCreateAlbumBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView


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



    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun onNetworkError() {

    }
}
