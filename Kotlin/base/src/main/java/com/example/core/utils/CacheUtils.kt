package com.example.core.utils

import android.annotation.SuppressLint
import android.content.Context
import com.example.core.BaseApplication
import com.example.core.R

@SuppressLint("StaticFieldLeak")
private val context: Context = BaseApplication.currentApplication
private val SP = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

fun String.save(value: String?) = SP.edit().putString(this, value).apply()

object CacheUtils {
    operator fun get(key: String?) = SP.getString(key, null)
}