package gladun.vlad.weather.ui.common

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gladun.vlad.weather.data.network.KnownException
import gladun.vlad.weather.data.network.UnknownException
import gladun.vlad.weather.util.postIfRequired
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

/**
 * Base view model class to implement common VM logic:
 * error handling, navigation actions, loading state etc
 */
abstract class BaseViewModel : ViewModel() {

    val loading = MutableLiveData(false)

    //TODO: implement base navigation logic

    /**
     * Launches a [block] in the [viewModelScope] and gives you an opportunity to handle an error directly, otherwise falls back to [onError].
     */
    inline fun launchWithErrorHandling(
        crossinline onLoading: (Boolean) -> Unit = {},
        crossinline block: suspend () -> Unit,
    ) = viewModelScope.launch {
        try {
            onLoading(true)
            block()
        }
        catch (e: Throwable) {
            if (e !is CancellationException) {
                val error = e as? KnownException ?: UnknownException(e)
                this@BaseViewModel.onError(error)
            }
            // else ignore
        }
        finally {
            onLoading(false)
        }
    }

    /**
     * Override the error handling here if needed
     */
    open fun onError(error: KnownException): Boolean {
        Log.e("launchWithErrorHandling", "Error: ${error.cause}")
        loading.postIfRequired(false)
        //TODO: generic error dialogs
        return true
    }
}