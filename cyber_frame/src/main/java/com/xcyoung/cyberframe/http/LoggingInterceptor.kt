//package me.solidev.common.http.intercepter
//
//import okhttp3.Interceptor
//import okhttp3.Response
//import timber.log.Timber
//import java.io.IOException
//import java.util.*
//
///**
// * Created by _SOLID
// * GitHub:https://github.com/burgessjp
// * Date:2017/9/28
// * Time:15:55
// */
//
//class LoggingInterceptor : Interceptor {
//
//    @Throws(IOException::class)
//    override fun intercept(chain: Interceptor.Chain): Response {
//        val request = chain.request()
//        val t1 = System.nanoTime()
//        Timber.d(String.format(Locale.CHINA, "Sending request %s on %s%n%s",
//                request.url(), chain.connection(), request.headers()))
//
//        val response = chain.proceed(request)
//
//        val t2 = System.nanoTime()
//        Timber.d(String.format(Locale.CHINA, "Received response for %s in %.1fms%n%s",
//                response.request().url(), (t2 - t1) / 1e6,
//                "headers:" + response.headers()))
//
//        return response
//    }
//}
