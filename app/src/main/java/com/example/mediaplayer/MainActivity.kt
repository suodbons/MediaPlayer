package com.example.mediaplayer

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(R.id.fragment_choice, ChoiceFragment()).commit()
        }
    }
    fun chooseVideo(){
        val intent = Intent()
        intent.type = "video/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 1)

    }
    fun chooseAudio(){
        val intent = Intent()
        intent.type = "audio/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 2)
    }
    fun closePlayer(){
        supportFragmentManager.beginTransaction().replace(R.id.fragment_choice, ChoiceFragment()).commit()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null){
            if(requestCode == 1){
                val playerFragment = PlayerFragment.newInstance(1, data.data)
                supportFragmentManager.beginTransaction().replace(R.id.fragment_choice, playerFragment).commit()
            }
            else if(requestCode == 2){
                val playerFragment = PlayerFragment.newInstance(2, data.data)
                supportFragmentManager.beginTransaction().replace(R.id.fragment_choice, playerFragment).commit()
            }
        }
    }
}
