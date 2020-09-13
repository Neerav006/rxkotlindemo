
import android.content.SharedPreferences
import com.example.rxkotlinsample.BuildConfig
import com.example.rxkotlinsample.retrofit.AuthInterceptor
import com.example.rxkotlinsample.utils.Constants
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent


import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class APIManager private constructor() {

    companion object {
        val instance: APIManager by lazy { APIManager() }
    }

    val api: API by lazy {
        val httpClient = OkHttpClient.Builder()
                .readTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .addInterceptor(AuthInterceptor())


        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        if (BuildConfig.DEBUG) {
            httpClient.addInterceptor(loggingInterceptor)

        }
        val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL_POST)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()

        retrofit.create(API::class.java)
    }


}
