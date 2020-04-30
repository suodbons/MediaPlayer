package com.example.mediaplayer

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.MediaController
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        video_button.setOnClickListener{
            val intent = Intent()
            intent.type = "video/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, 1)
        }
        audio_button.setOnClickListener{
            val intent = Intent()
            intent.type = "audio/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, 2)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null){
            if(requestCode == 1){
                val uri : Uri? = data.data
                val selectedItem: String = getPath(uri)
                if (selectedItem != ""){
                    Toast.makeText(this, "VIDEO", Toast.LENGTH_SHORT).show()
                    video_view.setVideoPath(selectedItem)
                    val mediaController = MediaController(this)
                    video_view.setMediaController(mediaController)
                    video_view.requestFocus(0);
                    video_view.start()
                }
            }
            else if(requestCode == 2){
                Toast.makeText(this, "AUDIO", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getPath(uri: Uri?): String {
        val projectionArray =arrayOf(MediaStore.Video.Media.DATA)
        val cursor : Cursor? = uri?.let { applicationContext.contentResolver.query(it, projectionArray, null, null, null) }
        if (cursor != null){
            val columnIndex : Int = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(columnIndex)
        }
        else{
            return ""
        }
    }

}
