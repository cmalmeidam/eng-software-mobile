package com.example.vinilosandroid.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.vinilosandroid.R
import com.example.vinilosandroid.databinding.FragmentCollectorDetailBinding
import com.example.vinilosandroid.models.Collector

class CollectorDetailFragment : Fragment() {

    private lateinit var binding: FragmentCollectorDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCollectorDetailBinding.inflate(inflater, container, false)
        var view = binding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.root.announceForAccessibility(getString(R.string.detallecolec))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        val args: CollectorDetailFragmentArgs by navArgs()
        binding.collector = Collector(
            args.collectorId, args.name, args.telephone, args.email)
    }

}