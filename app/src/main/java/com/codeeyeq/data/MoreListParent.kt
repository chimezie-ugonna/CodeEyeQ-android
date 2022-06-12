package com.codeeyeq.data

data class MoreListParent(
    val section: String,
    val img: String = "",
    val name: String = "",
    val email: String = "",
    val list: ArrayList<MoreListChild>
)