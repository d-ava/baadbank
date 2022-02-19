package com.example.baadbank.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.baadbank.R


fun ImageView.glideExtension(img: String?) {
    if (!img.isNullOrEmpty()) {
        Glide.with(context).load(img).error(R.drawable.ic_close)
            .into(this)

    } else setImageResource(R.drawable.ic__logo_splash)


}