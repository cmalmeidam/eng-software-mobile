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
import com.example.vinilosandroid.databinding.FragmentCollectorDetailBinding
import com.example.vinilosandroid.models.Collector
import com.example.vinilosandroid.viewmodels.CollectorViewModel

class CollectorDetailFragment : Fragment() {

    private lateinit var binding: FragmentCollectorDetailBinding
    private lateinit var viewModel: CollectorViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCollectorDetailBinding.inflate(inflater, container, false)
        var view = binding.root
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        val args: CollectorDetailFragmentArgs by navArgs()
        activity.actionBar?.title = getString(R.string.coleccionistas)
        viewModel = ViewModelProvider(this, CollectorViewModel.Factory(activity.application)).get(
            CollectorViewModel::class.java)
        binding.collector = Collector(
            args.collectorId, args.name, args.telephone, args.email)
        var tmp = getString(R.string.coleccionista_placeholder)
        print(tmp)
        Glide.with(activity)
            .load(tmp)
            .apply(
                RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .placeholder(R.drawable.ic_collector)
                    .error(R.drawable.ic_collector))
            .into(binding.itemAvatarIv)
    }

}