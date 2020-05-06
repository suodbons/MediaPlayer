package com.example.mediaplayer

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Message
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import kotlinx.android.synthetic.main.fragment_audio.view.*


class AudioFragment : Fragment() {
    private var uri: Uri? = null
    private var totalTime : Int = 0
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_audio, container, false)
        uri = arguments?.getParcelable("uri")
        mediaPlayer = MediaPlayer.create(activity, uri)
        mediaPlayer.start()
        rootView.play_button.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
                rootView.play_button.setBackgroundResource(R.drawable.play)
            } else {
                mediaPlayer.start()
                rootView.play_button.setBackgroundResource(R.drawable.pause)
            }
        }

        totalTime = mediaPlayer.duration
        rootView.music_bar.max = totalTime
        rootView.music_bar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    if(fromUser){
                        mediaPlayer.seekTo(progress)
                    }
                }
                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }
                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                }
            }
        )
        @SuppressLint("HandlerLeak")
        val handler = object : Handler(){
            override fun handleMessage(msg: Message) {
                val currentPosition = msg.what
                rootView.music_bar.progress = currentPosition
                val timeFromStart = countTime(currentPosition)
                rootView.time_from_start.text = timeFromStart
                var timeToFinish = countTime(totalTime - currentPosition)
                timeToFinish = "-$timeToFinish"
                rootView.time_to_finish.text = timeToFinish
                super.handleMessage(msg)
            }
        }
        Thread(Runnable {
            while(true){
                try{
                    val message = Message()
                    message.what = mediaPlayer.currentPosition
                    handler.sendMessage(message)
                    Thread.sleep(1000)
                } catch (e: InterruptedException){
                }
            }
        }).start()
        return rootView
    }

    fun countTime(time: Int) : String {
        val min = time / 1000 / 60
        val sec = time / 1000 % 60
        var timeText = "$min:"
        if(sec < 10) timeText += 0
        timeText += sec
        return timeText
    }

    override fun onDetach() {
        mediaPlayer.stop()
        super.onDetach()
    }

    companion object {
        @JvmStatic
        fun newInstance( uri: Uri?) : AudioFragment {
            val fragment = AudioFragment()
            val args = Bundle().apply{
                putParcelable("uri", uri)
            }
            fragment.arguments = args
            return fragment
        }
    }
}
