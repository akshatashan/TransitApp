package io.door2door.transitapp.models

import io.mironov.smuggler.AutoParcelable
import java.util.*

/**
 * Created by akshata on 12/10/16.
 */
data class Response(
        val routes: ArrayList<Route>,
        val provider_attributes: HashMap<String, Attributes>
): AutoParcelable{

}