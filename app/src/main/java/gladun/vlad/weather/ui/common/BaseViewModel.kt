package gladun.vlad.weather.ui.common

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Base view model class to implement common VM logic:
 * error handling, navigation actions, loading state etc
 */
abstract class BaseViewModel : ViewModel() {

    val loading = MutableLiveData(false)

    //TODO: implement base navigation logic and error handling
}