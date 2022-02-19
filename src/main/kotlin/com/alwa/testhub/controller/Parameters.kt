package com.alwa.testhub.controller

object Parameters {

    const val DEFAULT_GROUP = "default"

    fun groupsOrDefault(groups: Array<String>?) =
        when (groups?.size) {
            0    -> listOf(DEFAULT_GROUP)
            else -> groups?.toList()
        } ?: listOf(DEFAULT_GROUP)

}