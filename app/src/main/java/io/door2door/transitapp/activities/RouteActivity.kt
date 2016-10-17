package io.door2door.transitapp.activities

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import com.google.gson.Gson
import io.door2door.transitapp.R
import io.door2door.transitapp.Utils
import io.door2door.transitapp.adapters.RouteRecyclerViewDataAdapter
import io.door2door.transitapp.models.Response
import io.door2door.transitapp.models.Route
import org.jetbrains.anko.async
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

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

        val adapter = RouteRecyclerViewDataAdapter(this, response!!.routes, response!!.provider_attributes)
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

    private fun downloadAll(routeList: ArrayList<Route>) {
        response!!.routes.forEach {r ->
            r.segments!!.forEach { s ->
                async(){
                    //load image
                    Log.d("test","load" + s.icon_url)
                    svgToBitmap1(this@RouteActivity,20, s.icon_url!!)
                }
            }
        }
    }

    fun svgToBitmap1(context: Context, size: Int, url: String) {
        var size = size
        var bmp: Bitmap? = null
        try {
            size = (size * context.resources.displayMetrics.density.toInt())
            val svg = Utils.readSvg(url)
            bmp = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bmp)
            svg!!.renderToCanvas(canvas)
            //compress and save to disk
            //saveImage(context, bmp, url.hashCode())
            saveImageSingleton(context, bmp, url.hashCode())

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // save to singleton
    private fun  saveImageSingleton(mContext: Context, bmp: Bitmap?, urlHashCode: Int) {
        if(Utils.ImageCache.sparseArrayIcons.indexOfKey(urlHashCode) < 0){
            Log.d("test", "add in sparsearray")
            Utils.ImageCache.sparseArrayIcons.put(urlHashCode,bmp)
        }
    }

    // save to disk
    private fun  saveImage(mContext: Context, bmp: Bitmap?, urlHashCode: Int) {
        val file = File(mContext.getExternalFilesDir(null).absolutePath,urlHashCode.toString())
        if (!file!!.exists()) {
            try {
                file?.createNewFile()
                val ostream = FileOutputStream(file)
                bmp!!.compress(Bitmap.CompressFormat.PNG, 100, ostream)
                ostream.flush()
                ostream.close()
            } catch (e: IOException) {
                Log.e("IOException", e.message)
            }
        }
    }

}
