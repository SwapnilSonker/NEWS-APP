package com.example.news

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest

class MainActivity : AppCompatActivity(), NewsItemClicked {

    private lateinit var mAdapter: News_Adapter //global method variable to be used within the function.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
         recyclerView.layoutManager = LinearLayoutManager(this)

         fetchdata()
        //linking adapterView to recyclerView
        mAdapter = News_Adapter(this)
        //adding recyclerview to adapter
        recyclerView.adapter = mAdapter
    }
    private fun fetchdata(){
        val url = "https://saurav.tech/NewsAPI/everything/cnn.json"
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET,url ,
        null,
            {
                Log.d("sdasas","$it")
                val newsJsonArray = it.getJSONArray("articles") //to extract array from Json
                val newsArray = ArrayList<News>()
                for(i in 0 until newsJsonArray.length()){
                    val newsJsonObject = newsJsonArray.getJSONObject(i)

                    //parsing of data is done below.
                    val news = News(
                        newsJsonObject.getString("title"),
                    newsJsonObject.getString("author"),
                    newsJsonObject.getString("url"),
                    newsJsonObject.getString("urlToImage")
                    )
                    newsArray.add(news)

                }
                mAdapter.updateNews(newsArray) //updateNews function will take and update the news.
                //Log.i(String(),"no error here" )

            },
            {
                //Log.e(String(),"error found")

            })


        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemclicked(item: News) {
        //Toast.makeText(this , "$item is clicked" , Toast.LENGTH_SHORT).show()


        val builder =CustomTabsIntent.Builder();
        val customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(item.url));
    }
}