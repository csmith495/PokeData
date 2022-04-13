package com.example.pokedata.model


import com.google.gson.annotations.SerializedName

data class HeldItem(
    val item: Item,
    @SerializedName("version_details")
    val versionDetails: List<VersionDetail>
)