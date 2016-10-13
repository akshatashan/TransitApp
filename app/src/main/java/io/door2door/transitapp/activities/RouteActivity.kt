package io.door2door.transitapp.activities

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.SparseArray
import com.google.gson.Gson
import io.door2door.transitapp.R
import io.door2door.transitapp.adapters.RouteRecyclerViewDataAdapter
import io.door2door.transitapp.models.Response
import java.io.IOException

/* View for the list of routes
  * inflates  activity_route */
class RouteActivity : AppCompatActivity() {
    private val TAG = "RouteActivity"
    private var response: Response? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        //load json into data objects
        val json = loadJSONFromAsset()
        val gson = Gson()
        response = gson.fromJson(json, Response::class.java)

        val recyclerViewRouteVertical = findViewById(R.id.recyclerViewRouteVertical) as RecyclerView
        //height/width of items in the view do not change.
        recyclerViewRouteVertical.setHasFixedSize(true)
        val sparseArrayIcons = SparseArray<Bitmap>()

        val adapter = RouteRecyclerViewDataAdapter(this, sparseArrayIcons, response!!.routes, response!!.provider_attributes)
        recyclerViewRouteVertical.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerViewRouteVertical.adapter = adapter
    }

    fun loadJSONFromAsset(): String? {
        var json: String? = null
        try {
            val iStream = assets.open("data.json")
            val size = iStream.available()
            val buffer = ByteArray(size)
            iStream.read(buffer)
            iStream.close()
            json = String(buffer,Charsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }

        return json
    }
}
