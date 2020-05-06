package com.example.mediaplayer

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import kotlinx.android.synthetic.main.fragment_video.view.*

class VideoFragment : Fragment() {
    private var uri: Uri? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_video, container, false)
        uri = arguments?.getParcelable("uri")
        rootView.video_view.setVideoURI(uri)
        val mediaController = MediaController(activity)
        rootView.video_view.setMediaController(mediaController)
        rootView.video_view.requestFocus(0)
        rootView.video_view.start()
        return rootView
    }
    companion object {
        @JvmStatic
        fun newInstance( uri: Uri?) : VideoFragment {
            val fragment = VideoFragment()
            val args = Bundle().apply{
                putParcelable("uri", uri)
            }
            fragment.arguments = args
            return fragment
        }
    }
}
