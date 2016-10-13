package io.door2door.transitapp.models

import io.mironov.smuggler.AutoParcelable

/**
 * Created by akshata on 12/10/16.
 */

data class Attributes(
        val provider_icon_url: String,
        val disclaimer: String,
        val display_name: String?
): AutoParcelable{

}