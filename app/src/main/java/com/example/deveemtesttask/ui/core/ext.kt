package com.example.deveemtesttask.ui.core

import android.os.Build
import android.os.Bundle
import java.io.Serializable

fun <T : Serializable?> getSerializableExt(bundle: Bundle, name: String, clazz: Class<T>): T
{
    return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        bundle.getSerializable(name, clazz)!!
    else
        bundle.getSerializable(name) as T
}