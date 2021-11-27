package com.example.vinilosandroid.ui

import android.os.Bundle
import android.text.TextUtils
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
import java.lang.Exception

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
        _binding!!.album = Album(args.albumId,args.name,"","","","","")
        viewModel = ViewModelProvider(this, TrackViewModel.Factory(activity.application, args.albumId)).get(TrackViewModel::class.java)
        viewModel.tracks.observe(viewLifecycleOwner, Observer<List<Track>> {
            it.apply {
                viewModelAdapter!!.tracks = this
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
            processInformation()
            val action = TrackFragmentDirections.actionTrackFragmentSelf(
                args.albumId, args.name)
            _binding!!.btnasociar.findNavController().navigate(action)
        }
    }

    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
    fun processInformation() {
        when (true){
            TextUtils.isEmpty(_binding!!.txtTrackName.text) -> Toast.makeText(activity,"El nombre no puede estar vacio", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(_binding!!.txtSegundos.text) && TextUtils.isEmpty(_binding!!.txtMinutos.text) -> Toast.makeText(activity,"El track debe durar más de 0 segundos",Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(_binding!!.txtMinutos.text) -> Toast.makeText(activity, "Si el track dura menos de un minuto poner 00", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(_binding!!.txtSegundos.text) -> Toast.makeText(activity, "Si el track dura 0 segundos poner 00", Toast.LENGTH_LONG).show()
            (_binding!!.txtMinutos.text.toString().toInt()) > 59 -> Toast.makeText(activity, "Los minutos no pueden exceder de 59", Toast.LENGTH_LONG).show()
            (_binding!!.txtSegundos.text.toString().toInt()) > 59 -> Toast.makeText(activity, "Los segundos no pueden exceder de 59", Toast.LENGTH_LONG).show()
            !TextUtils.isEmpty(_binding!!.txtTrackName.text) && !TextUtils.isEmpty(_binding!!.txtMinutos.text) && !TextUtils.isEmpty(_binding!!.txtSegundos.text) ->
                try {
                    var num = _binding!!.txtSegundos.text.toString()
                    if((_binding!!.txtSegundos.text.toString().toInt()) <10){
                        num = "0"+_binding!!.txtSegundos.text.toString()
                    }
                    val track = Track(
                        0,
                        _binding!!.txtTrackName.text.toString(),
                        (_binding!!.txtMinutos.text.toString() + ":" + num)
                    )
                    viewModel.postDataFromNetwork(track)
                } catch (e: Exception) {
                    Toast.makeText(activity, e.message, Toast.LENGTH_LONG).show()
                    println(e)
                }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}