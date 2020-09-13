package com.example.rxkotlinsample.model.postmodel

data class PostResponse(
   val postList : List<Post> = emptyList()
) {
}