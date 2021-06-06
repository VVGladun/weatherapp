package gladun.vlad.weather.util

import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.asLiveData(): LiveData<T> = this

fun <T> MutableLiveData<T>.postIfRequired(value: T) {
    if (Looper.myLooper() === Looper.getMainLooper()) {
        this.value = value
    }
    else {
        postValue(value)
    }
}