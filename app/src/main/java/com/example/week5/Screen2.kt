package com.example.week5

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.example.week5.R.id.datenasa
import okhttp3.Headers
import java.util.*
import kotlin.random.Random

class Screen2 : AppCompatActivity() {
    var NasaPictureURL = ""
    var titleURL=""
    var dateURL=""
    override fun onCreate(savedInstanceState: Bundle?) {
        getNasaPictureURL()
        Log.d("NasaPictureURL", "Nasa image URL set")
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_screen2)

        val button = findViewById<Button>(R.id.button)
        val imageView = findViewById<ImageView>(R.id.NasaPicture)
        val textView = findViewById<TextView>(R.id.datenasa)
        val textView1 = findViewById<TextView>(R.id.titlenasa)

        getNextImage(button,imageView, textView)
    }

    @GlideModule
    class MyAppGlideModule : AppGlideModule() {

        override fun applyOptions(context: Context, builder: GlideBuilder) {
            super.applyOptions(context, builder)
            builder.apply { RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL) }
        }

    }
    private fun getNasaPictureURL()
    {
        val client = AsyncHttpClient()
        client["https://api.nasa.gov/planetary/apod?api_key=pZFIQjVpbuaBMsP3bLbIQx9IGqIPeTyY5f2bWjkO&", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: okhttp3.Headers?, json: JsonHttpResponseHandler.JSON) {
                Log.d("Nasa", "response successful$json")
                NasaPictureURL = json.jsonObject.getString("url")
                titleURL = json.jsonObject.getString("title")
                dateURL = json.jsonObject.getString("date")
            }

            override fun onFailure(
                statusCode: Int,
                headers: okhttp3.Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("Nasa Error", errorResponse)
            }
        }]
    }

    private fun getNextImage(button: Button, imageView: ImageView,textView: TextView) {
        button.setOnClickListener {

            getNasaPictureURL()


            Glide.with(this)
                . load(NasaPictureURL)
                .fitCenter()
                .into(imageView)
        }
    }
}

