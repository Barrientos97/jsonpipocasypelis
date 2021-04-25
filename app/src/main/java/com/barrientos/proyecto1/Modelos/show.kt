package com.barrientos.proyecto1.Modelos

import java.io.Serializable

data class show(
        val id: Int,
        val url:String,
        val name:String,
        val officialSite: String?,
        val language: String,
        val genres:List<String>,
        val runtime:Int,
        val summary:String,
        val premiered:String,
        val image:ShowImage
):Serializable
