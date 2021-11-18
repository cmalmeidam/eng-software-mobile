package com.example.vinilosandroid.ui

import android.media.Image
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.vinilosandroid.R
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat

/**
 * A simple [Fragment] subclass.
 * Use the [AlbumDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AlbumDetailFragment : Fragment() {

    private var albumId = 0
    private var descripcion = ""
    private var cover = ""
    private var genre = ""
    private var name = ""
    private var recordLabel = ""
    private var releaseDate = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args: AlbumDetailFragmentArgs by navArgs()
        this.albumId = args.albumId
        this.descripcion = args.descripcion
        this.cover = args.cover
        this.genre = args.genre
        this.name = args.name
        this.recordLabel = args.recordLabel
        this.releaseDate= args.releaseDate.substring(8,10) + "-" + args.releaseDate.substring(5,7) + "-" + args.releaseDate.substring(0,4)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.album_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val textName =  view.findViewById<View>(R.id.textName) as TextView
        textName.setText(this.name)
        val imageAlbum = view.findViewById<View>(R.id.imageAlbum) as ImageView
        Picasso.get()
            .load(this.cover)
            .placeholder(R.drawable.ic_album)
            .error(R.drawable.ic_artist)
            .into(imageAlbum)
        val textDescription =  view.findViewById<View>(R.id.textdescription) as TextView
        textDescription.setText(this.descripcion)
        val textGenero = view.findViewById<View>(R.id.textGeneroValue) as TextView
        textGenero.setText(this.genre)
        val textFechaLanz = view.findViewById<View>(R.id.textFechaLanzValue) as TextView
        textFechaLanz.setText(this.releaseDate)
        val textCasaDisco = view.findViewById<View>(R.id.textCasaDiscoValue) as TextView
        textCasaDisco.setText(this.recordLabel)
        val btnReturn = view.findViewById<View>(R.id.returnButton) as ImageButton
        btnReturn.setOnClickListener(){
            val action = AlbumDetailFragmentDirections.actionAlbumDetailFragmentToAlbumFragment()
            view.findNavController().navigate(action)
        }
    }
}