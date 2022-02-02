package com.jesse.rickymortyapp.network

import android.os.Parcelable
//import kotlinx.android.parcel.Parcelize
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class RickMortyCharacter(
    val created: String,
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: Location,
    val name: String,
    val origin: Origin,
    val species: String,
    val status: String,
    val type: String,
    val url: String
): Parcelable

@Parcelize
data class Origin(
    val name: String,
    val url: String
): Parcelable

@Parcelize
data class Location(
    val name: String,
    val url: String
): Parcelable

data class Info(
    val count: Int,
    val next: String,
    val pages: Int,
    val prev: String?
)

data class RickMortyObject(
    val info: Info,
    val results: List<RickMortyCharacter>
)
