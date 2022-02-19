package com.alwa.testhub.controller

object Parameters {

    fun groupsOrDefault(groups: Array<String>?) =
        when (groups?.size) {
            0    -> defaultGroup()
            else -> groups?.toList()
        } ?: defaultGroup()

    private fun defaultGroup() = listOf("default")
}