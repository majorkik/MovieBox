package com.majorik.library.base.utils

const val PACKAGE_NAME = "com.majorik.moviebox"

private val classMap = mutableMapOf<String, Class<*>>()

private inline fun <reified T : Any> Any.castOrReturnNull() = this as? T

internal fun <T> String.loadClassOrReturnNull(): Class<out T>? =
    classMap.getOrPut(this) {
        try {
            Class.forName(this)
        } catch (e: ClassNotFoundException) {
            return null
        }
    }.castOrReturnNull()
