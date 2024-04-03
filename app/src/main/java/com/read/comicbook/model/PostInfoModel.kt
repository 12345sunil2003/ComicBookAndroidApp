package com.read.comicbook.model

data class PostInfoModel (
    var postId: Long?=null,
    var title: String?=null,
    var desc: String?=null,
    var thumbnail: String?=null,
    var category: String?=null,
    var story: String?=null,
    var imgFiles: List<String>?=null,
    var isFav: Boolean=false,
    var rating: String?=null,
    var price: String?=null,
    var isPaid: Boolean=false

):java.io.Serializable