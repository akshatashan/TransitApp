package io.leapingwolf.transitapp.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.widget.Toast
import com.github.kittinunf.result.failure
import com.github.kittinunf.result.success
import io.leapingwolf.transitapp.R
import io.leapingwolf.transitapp.adapters.RouteRecyclerViewDataAdapter
import io.leapingwolf.transitapp.models.Response
import io.leapingwolf.transitapp.network.ResponseService
import org.jetbrains.anko.async
import java.io.IOException

/* View for the list of view_routes
  * inflates  activity_route */
class RouteActivity : AppCompatActivity() {
    private val TAG = "RouteActivity"
    private var response: Response? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val recyclerViewRouteVertical = findViewById(R.id.recyclerViewRouteVertical) as RecyclerView
        recyclerViewRouteVertical.setHasFixedSize(true)
        recyclerViewRouteVertical.layoutManager = LinearLayoutManager(this@RouteActivity, LinearLayoutManager.VERTICAL, false)

        //load json from network
        async(){
            val result = ResponseService.get(resources.getString(R.string.url))
            result.success {
                runOnUiThread {
                    val adapter = RouteRecyclerViewDataAdapter(this@RouteActivity, result.component1()?.routes, result.component1()?.provider_attributes!!)
                    recyclerViewRouteVertical.adapter = adapter
                    recyclerViewRouteVertical.adapter.notifyDataSetChanged()
                }
            }
            result.failure {
                runOnUiThread {
                    Toast.makeText(this@RouteActivity,"Failed to load data",Toast.LENGTH_SHORT).show()
                }
            }
        }
//        //load json into data objects
//        val json = loadJSONFromAsset()
//        val gson = Gson()
//        response = gson.fromJson(json, Response::class.java)

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
