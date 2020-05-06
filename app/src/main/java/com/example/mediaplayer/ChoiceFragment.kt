package com.example.mediaplayer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_choice.view.*

class ChoiceFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_choice, container, false)
        rootView.video_button.setOnClickListener{
            (activity as MainActivity).chooseVideo()
        }
        rootView.audio_button.setOnClickListener{
            (activity as MainActivity).chooseAudio()
        }
        return rootView
    }
}
