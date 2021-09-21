package apo.mor.movierama.Services

import android.util.Log
import androidx.multidex.BuildConfig
import apo.mor.movierama.Services.ApiConstants.Companion.HTTP_CONNECT_TIMEOUT
import apo.mor.movierama.Services.ApiConstants.Companion.HTTP_READ_TIMEOUT
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

class RestClient {

    companion object {
        val REST_CLIENT: ApiCalls? by lazy {
            setupRestClient()
        }

        private fun setupRestClient(): ApiCalls? {
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(ApiConstants.TMDB_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getHttpClient())
                .build()
            return retrofit.create(ApiCalls::class.java)
        }

        private fun getHttpClient(): OkHttpClient {
            val httpBuilder = OkHttpClient().newBuilder()
            httpBuilder.readTimeout(HTTP_READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
            httpBuilder.connectTimeout(HTTP_CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
            httpBuilder.addInterceptor { chain ->
                val request =
                    chain.request().newBuilder().addHeader("ID", UUID.randomUUID().toString()).build()
                chain.proceed(request)
            }
            if (BuildConfig.DEBUG) {
                getLoggingInterceptor()?.let { httpBuilder.addInterceptor(it) }
            }
            return httpBuilder.build()
        }

        private fun getLoggingInterceptor(): HttpLoggingInterceptor? {
            // Log requests & responses.
            var interceptor: HttpLoggingInterceptor? = null
            if (BuildConfig.DEBUG) {
                interceptor =
                    HttpLoggingInterceptor { message ->
                        var mMessage = message
                        Log.e("RetrofitLogger", mMessage)
                        val filteredMessage = mMessage.split("/".toRegex()).toTypedArray()
                        if (filteredMessage.size > 4) {
                            mMessage = ""
                            for (i in 0..4) {
                                mMessage += filteredMessage[i] + "/"
                            }
                            Log.d("ServiceLog:", mMessage)
                        }
                    }
            } else {
                interceptor = HttpLoggingInterceptor()
            }
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            return interceptor
        }
    }
}