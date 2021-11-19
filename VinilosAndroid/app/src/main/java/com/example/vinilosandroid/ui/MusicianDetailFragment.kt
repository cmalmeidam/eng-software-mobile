package com.example.vinilosandroid.ui

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.vinilosandroid.R
import com.example.vinilosandroid.databinding.FragmentMusicianDetailBinding
import com.example.vinilosandroid.models.Musician
import com.squareup.picasso.Picasso
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [MusicianDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MusicianDetailFragment : Fragment() {

    private lateinit var binding : FragmentMusicianDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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
        var birthDate = args.birthDate.toString()
        var finalDate: String? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var odt = OffsetDateTime.parse(birthDate)
            var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH)
            finalDate = formatter.format(odt)
        } else {
            val originalFormat : DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
            val targetFormat : DateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
            val originDate : Date = originalFormat.parse(birthDate)
            finalDate = targetFormat.format(originDate)
        }
        binding.musician = Musician(
            args.musicianId, args.name, args.image, args.description, finalDate
        )
        Picasso.get()
            .load(args.image)
            .placeholder(R.drawable.ic_artist)
            .error(R.drawable.ic_face)
            .into(binding.detailImageIv)
    }

}