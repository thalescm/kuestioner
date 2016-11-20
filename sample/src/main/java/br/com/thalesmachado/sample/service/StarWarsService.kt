package br.com.thalesmachado.sample.service

import br.com.thalesmachado.kuestioner.APIModel
import com.google.gson.JsonObject
import retrofit2.http.Body
import retrofit2.http.POST
import rx.Observable

interface StarWarsService {

    @POST("?")
    fun query(@Body body : APIModel) : Observable<JsonObject>
}
