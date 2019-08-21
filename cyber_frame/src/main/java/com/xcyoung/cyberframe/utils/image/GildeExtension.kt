package com.xcyoung.cyberframe.utils.image

import android.content.Context
import android.graphics.Bitmap
import android.media.Image
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.bumptech.glide.request.RequestOptions

class GlideWrapper {

    var url: String? = null

    var image: ImageView? = null

    var placeholder: Int = ImageConfig.placeHolderRes

    var error: Int = ImageConfig.errorRes

    var transform: Transformation<Bitmap>? = null

}

fun load(init: GlideWrapper.() -> Unit) {

    val wrap = GlideWrapper()

    wrap.init()

    execute(wrap)
}

private fun execute(wrap: GlideWrapper) {

    wrap.image?.let {

        val request = it.get(wrap.url).transition(BitmapTransitionOptions.withCrossFade())

        val options = RequestOptions()
            .placeholder(wrap.placeholder)
            .error(wrap.error)

        if (wrap.transform != null) {
            options.transform(wrap.transform!!)
        }

        request.apply(options).into(it)
    }

}