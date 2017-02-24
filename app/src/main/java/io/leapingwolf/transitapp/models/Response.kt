package io.leapingwolf.transitapp.models

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import io.mironov.smuggler.AutoParcelable
import java.util.*

/**
 * Created by akshata on 12/10/16.
 */
data class Response(
        val routes: ArrayList<Route>,
        val provider_attributes: HashMap<String, Attributes>
): AutoParcelable{

    class Deserializer : ResponseDeserializable<Response> {
        override fun deserialize(content: String) = Gson().fromJson(content, Response::class.java)
    }
}