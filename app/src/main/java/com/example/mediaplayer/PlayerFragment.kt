package com.example.mediaplayer

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_player.view.*

class PlayerFragment : Fragment() {
    private var requestCode: Int? = null
    private var uri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_player, container, false)
        rootView.stop_button.setOnClickListener{
            (activity as MainActivity).closePlayer()
        }
        requestCode = arguments?.getInt("requestCode")
        uri = arguments?.getParcelable("uri")
        if(requestCode == 1){
            val videoFragment = VideoFragment.newInstance(uri)
            childFragmentManager.beginTransaction().replace(R.id.fragment_player, videoFragment).commit()
        }
        else if(requestCode == 2){
            val audioFragment = AudioFragment.newInstance(uri)
            childFragmentManager.beginTransaction().replace(R.id.fragment_player, audioFragment).commit()
        }
        return rootView
    }

    companion object {
        @JvmStatic
        fun newInstance(requestCode: Int, uri: Uri?) : PlayerFragment {
            val fragment = PlayerFragment()
            val args = Bundle().apply{
                putInt("requestCode", requestCode)
                putParcelable("uri", uri)
            }
            fragment.arguments = args
            return fragment
        }
    }
}
