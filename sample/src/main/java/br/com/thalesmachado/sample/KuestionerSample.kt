package br.com.thalesmachado.sample

import br.com.thalesmachado.kuestioner.Kuestioner
import br.com.thalesmachado.sample.models.Film
import br.com.thalesmachado.sample.service.StarWarsService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

fun main(args: Array<String>) {

    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

    val retrofit = Retrofit.Builder().baseUrl("http://graphql-swapi.parseapp.com/")
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    val service = retrofit.create(StarWarsService::class.java)
    service
            .query(Kuestioner.queryOn(Film::class.java, mapOf("id" to "\"ZmlsbXM6MQ==\"")))
            .subscribe(
                    {
                        println("SUCCESS")
                    },
                    {
                        println("ERROR + $it")
                    })

}