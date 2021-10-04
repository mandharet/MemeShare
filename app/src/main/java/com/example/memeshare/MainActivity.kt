package com.example.memeshare

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var currentImgUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showMeme()
    }
    private fun showMeme(){

        progressbar.visibility= View.VISIBLE
        val urllink = "https://meme-api.herokuapp.com/gimme"   //api urllink

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, urllink, null,
            { response ->
                currentImgUrl=response.getString("url")         //grab url as key-value string named url

                //glide library to load image
                Glide.with(this).load(currentImgUrl).listener(object: RequestListener<Drawable>{

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressbar.visibility= View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressbar.visibility= View.GONE
                        return false
                    }
                } ).into(memeImage)
                //glide call ends
            },
            { error ->
                // TODO: Handle error
                Toast.makeText(this, "Some Error Occured",Toast.LENGTH_LONG).show()
            })
    //call to singleton class instance
    MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
    fun shareMeme(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type= "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"$currentImgUrl")
        val chooser = Intent.createChooser(intent, "share this meme")
        startActivity(chooser)
    }
    fun nextMeme(view: View) {
        showMeme()
    }
}

