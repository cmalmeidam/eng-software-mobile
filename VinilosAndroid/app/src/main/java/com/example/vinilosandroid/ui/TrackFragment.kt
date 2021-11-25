package com.example.vinilosandroid.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.vinilosandroid.R
import com.example.vinilosandroid.databinding.TrackFragmentBinding
import com.example.vinilosandroid.models.Album
import com.example.vinilosandroid.models.Track
import com.example.vinilosandroid.ui.adapters.TracksAdapter
import com.example.vinilosandroid.viewmodels.TrackViewModel

class TrackFragment : Fragment() {
    private var _binding: TrackFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: TrackViewModel
    private var viewModelAdapter: TracksAdapter? = null
    private lateinit var track:Track

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = TrackFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModelAdapter = TracksAdapter()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.tracksRv
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModelAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        activity.actionBar?.title = getString(R.string.asociar_tracks)

        val args:  TrackFragmentArgs by navArgs()
        Log.d("Args", args.albumId.toString())
        binding.album = Album(args.albumId,args.name,"","","","","")
        viewModel = ViewModelProvider(this, TrackViewModel.Factory(activity.application, args.albumId)).get(TrackViewModel::class.java)
        viewModel.tracks.observe(viewLifecycleOwner, Observer<List<Track>> {
            it.apply {
                viewModelAdapter!!.tracks = this
                println("esta es el binding text no comments")
                println(this)
                if(this.isEmpty()){
                    binding.txtNoComments.visibility = View.VISIBLE
                }else{
                    binding.txtNoComments.visibility = View.GONE
                }
            }
        })
        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })
        binding.btnasociar.setOnClickListener {
            viewModel.processInformation(binding, activity)
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