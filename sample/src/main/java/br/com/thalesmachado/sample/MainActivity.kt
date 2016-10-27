package br.com.thalesmachado.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import br.com.thalesmachado.kuestioner.Kuestioner
import br.com.thalesmachado.sample.models.Viewer
import br.com.thalesmachado.sample.models.ViewerQuery
import br.com.thalesmachado.sample.service.GithubService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { Toast.makeText(this@MainActivity, "SUCCESS", Toast.LENGTH_LONG).show() }
                        , { Toast.makeText(this@MainActivity, "ERROR", Toast.LENGTH_LONG).show() })
    }
}