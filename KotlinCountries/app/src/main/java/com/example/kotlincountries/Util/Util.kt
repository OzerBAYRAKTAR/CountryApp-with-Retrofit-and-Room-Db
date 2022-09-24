package com.example.kotlincountries.Util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.kotlincountries.R
import kotlin.contracts.contract

//Extension


fun ImageView.downloadFromUrl(url:String?,progressDrawable: CircularProgressDrawable){


    //place holder ,error vs kodları için bu kodlama yapılır.
    val options=RequestOptions()
        .placeholder(progressDrawable)
        .error(R.drawable.ic_launcher_foreground)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)




}
fun placeHolderProgressBar(context: Context): CircularProgressDrawable {
    //apply dedikten sonra vermek istediğimiz özellikleri yazarız.
    return CircularProgressDrawable(context).apply {
        strokeWidth=8f
        centerRadius=40f
        start()


    }

}


