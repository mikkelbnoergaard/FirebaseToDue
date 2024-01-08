package com.example.todue.dataLayer.source.remote

data class GiphyApiResponse(
    val data: GifData,
    val meta: MetaData
)

data class GifData(
    val images: GifImages
)

data class GifImages(
    val fixed_height: GifImage
)

data class GifImage(
    val url: String
)

data class MetaData(
    val status: Int,
    val msg: String
)
