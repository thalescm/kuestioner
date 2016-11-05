package br.com.thalesmachado.sample

import br.com.thalesmachado.kuestioner.Kuestioner
import br.com.thalesmachado.sample.models.Viewer
import br.com.thalesmachado.sample.models.ViewerQuery
import br.com.thalesmachado.sample.service.GithubService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class KuestionerSample {

    init {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit = Retrofit.Builder().baseUrl("https://api.github.com/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

        val service = retrofit.create(GithubService::class.java)
        service
                .query(ViewerQuery(Kuestioner.queryOn(Viewer::class.java)))
                .subscribe(
                        {println("SUCCESS") }
                        , { println("ERROR") })
    }
}