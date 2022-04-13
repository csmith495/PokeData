package com.example.pokedata.model


import com.google.gson.annotations.SerializedName

data class PastType(
    val generation: Generation,
    val types: List<Type>
)