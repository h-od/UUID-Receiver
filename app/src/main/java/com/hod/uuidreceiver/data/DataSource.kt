package com.hod.uuidreceiver.data

import com.google.gson.annotations.SerializedName
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface DataContract {
    fun fetch(): Observable<Entity>
    fun fetchPrevious(): Single<Entity>
}

interface Remote {
    @GET("{path}")
    fun fetch(@Path("path") string: String = ""): Observable<Entity>
}

interface Local {
    fun fetch(): Entity
    fun put(code: String): Entity
}

data class Entity(
        @SerializedName("next_path") val nextPath: String = "",
        @SerializedName("path") val path: String = "",
        @SerializedName("response_code") val responseCode: String = "",
        val count: Int = 0)
