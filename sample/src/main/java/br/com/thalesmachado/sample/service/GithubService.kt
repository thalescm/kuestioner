package br.com.thalesmachado.sample.service

import br.com.thalesmachado.sample.models.ViewerQuery
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import rx.Observable

interface GithubService {

    // Refer to https://developer.github.com/early-access/graphql/guides/accessing-graphql/
    @POST("graphql")
    @Headers("Authorization: bearer <<place github access token here>>")
    fun query(@Body body : ViewerQuery) : Observable<String>
}
