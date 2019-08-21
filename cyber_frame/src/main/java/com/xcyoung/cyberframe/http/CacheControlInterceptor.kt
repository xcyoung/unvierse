package com.xcyoung.cyberframe.http

import android.content.Context
import android.text.TextUtils
import com.xcyoung.cyberframe.utils.NetworkUtil
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Created by _SOLID
 * GitHub:https://github.com/burgessjp
 * Date:2017/9/28
 * Time:15:53
 */

class CacheControlInterceptor(var context: Context) : okhttp3.Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val isNetworkConnected = NetworkUtil.isConnected(context)
        var request = chain.request()
        if (!isNetworkConnected) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build()
        }

        var response = chain.proceed(request)
        if (isNetworkConnected) {
            val maxAge = 10
            var cacheControl = request.cacheControl().toString()
            if (TextUtils.isEmpty(cacheControl)) {
                cacheControl = "public, max-age=$maxAge"
            }
            response = response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", cacheControl)
                    .build()

        } else {
            val maxStale = 60 * 60 * 24 * 30
            response = response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                    .build()
        }
        return response
    }
}
