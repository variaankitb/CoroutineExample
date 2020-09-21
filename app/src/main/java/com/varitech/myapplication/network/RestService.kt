package com.varitech.myapplication.network

import com.varitech.myapplication.model.Album
import com.varitech.myapplication.model.AlbumItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RestService {
    @GET(value = "/albums")
    suspend fun getAlbums(): Response<Album>

    @GET(value = "/albums")
    suspend fun getSortedAlbums(@Query(value = "userId") userId: Int): Response<Album>

    @GET(value = "/albums/{id}")
    suspend fun getAlbums(@Path(value = "id") albumId: Int): Response<AlbumItem>
}