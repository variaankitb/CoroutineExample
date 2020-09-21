package com.varitech.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import com.varitech.myapplication.model.Album
import com.varitech.myapplication.model.AlbumItem
import com.varitech.myapplication.network.RestService
import com.varitech.myapplication.network.RetrofitInstance
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Retrofit
        val restService: RestService = RetrofitInstance.getRetrofitInstance().create(
            RestService::class.java
        )

        //Path response
        val pathResponse: LiveData<Response<AlbumItem>> = liveData {
            val response: Response<AlbumItem> = restService.getAlbums(3)
            emit(response)
        }

        val responseLiveData: LiveData<Response<Album>> = liveData {
            val response: Response<Album> = restService.getSortedAlbums(3)
            emit(response)
        }

        pathResponse.observe(this, Observer {
            val title = it.body()?.title
            Toast.makeText(this, title, Toast.LENGTH_LONG).show()
        })

        responseLiveData.observe(this, Observer {
            val albumList: MutableListIterator<AlbumItem>? = it.body()?.listIterator()
            albumList?.let {
                while (albumList.hasNext()) {
                    val albumItem: AlbumItem = albumList.next()
                    val result: String = " Album title: ${albumItem.title} \n" +
                            " Album id: ${albumItem.id} \n" +
                            " User id: ${albumItem.userId} \n\n\n"
                    tv_title?.append(result)
                }
            }
        })
    }
}