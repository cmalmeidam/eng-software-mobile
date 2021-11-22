package com.example.vinilosandroid.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.vinilosandroid.R
import com.example.vinilosandroid.databinding.FragmentMusicianDetailBinding
import com.example.vinilosandroid.models.Musician
import com.example.vinilosandroid.viewmodels.MusicianViewModel


class MusicianDetailFragment : Fragment() {

    private lateinit var binding : FragmentMusicianDetailBinding
    private lateinit var viewModel: MusicianViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMusicianDetailBinding.inflate(inflater, container, false)
        var view = binding.root
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        val args: MusicianDetailFragmentArgs by navArgs()
        activity.actionBar?.title = getString(R.string.tiartistas)
        viewModel = ViewModelProvider(this, MusicianViewModel.Factory(activity.application)).get(
            MusicianViewModel::class.java)
        var finalDate = viewModel.formateandoDate(args.birthDate)
        binding.musician = Musician(
            args.musicianId, args.name, args.image, args.description, finalDate
        )
        Glide.with(activity)
            .load(args.image)
            .apply(
                RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .placeholder(R.drawable.ic_artist)
                    .error(R.drawable.ic_face))
            .into(binding.itemImageIv)
    }

}