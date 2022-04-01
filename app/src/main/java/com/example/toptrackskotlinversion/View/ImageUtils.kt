package com.example.toptrackskotlinversion.View

import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop

object ImageUtils {
    fun loadCircleImage(imageView: ImageView,bitmap: Bitmap){
        Glide.with(imageView.context).load(bitmap).transform(CenterCrop()).into(imageView)
    }
}