package com.example.vinilosandroid.ui

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import com.example.vinilosandroid.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*

private const val MUSICIAN_ID = "musicianId"
private const val NAME = "name"
private const val BIRTH_DATE = "birthDate"
private const val DESCRIPTION = "description"
private const val IMAGE = "image"

/**
 * A simple [Fragment] subclass.
 * Use the [MusicianDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */


class MusicianDetailFragment : Fragment() {
    private var musicianId: Int? = null
    private var name: String? = null
    private var birthDate: String? = null
    private var description: String? = null
    private var image: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            musicianId = it.getInt(MUSICIAN_ID)
            name = it.getString(NAME)
            birthDate = it.getString(BIRTH_DATE)
            description = it.getString(DESCRIPTION)
            image = it.getString(IMAGE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.musician_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val name_tv: TextView = view.findViewById(R.id.textView11)
        val nombre = arguments?.get("name")
        name_tv.text = nombre.toString()

        val desc_tv: TextView = view.findViewById(R.id.textView12)
        val desc = arguments?.get("description")
        desc_tv.text = desc.toString()

        val fecha_tv: TextView = view.findViewById(R.id.textView13)
        val fecha = arguments?.get("birthDate")
        var odt = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var odt = OffsetDateTime.parse(fecha.toString())
            var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH)
            fecha_tv.text = formatter.format(odt)
        } else {
            val originalFormat : DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
            val targetFormat : DateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
            val originDate : Date = originalFormat.parse(fecha.toString())
            fecha_tv.text = targetFormat.format(originDate)
            //fecha_tv.text = fecha.toString()
        }
        val image_view: ImageView = view.findViewById(R.id.detail_image_iv)
        val image = arguments?.get("image")
        Picasso.get()
            .load(image?.toString())
            .placeholder(R.drawable.ic_artist)
            .error(R.drawable.ic_face)
            .into(image_view)
        val button : FloatingActionButton = view.findViewById(R.id.detail_musician_button)
        button.setOnClickListener{
            val action = MusicianDetailFragmentDirections.actionMusicianDetailFragmentToMusicianFragment()
            view.findNavController().navigate(action)
        }
    }
}