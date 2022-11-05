package co.diwakar.marvelcharacters.util

import android.os.Build
import android.os.Bundle
import android.os.Parcelable

inline fun <reified T : Parcelable?> Bundle.getParcelableData(key: String): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelable(key, T::class.java)
    } else {
        @Suppress("DEPRECATION")
        getParcelable(key)
    }
}