package gladun.vlad.weather.ui.common

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import gladun.vlad.weather.ui.ActivityViewModel

/**
 * Base fragment class to store common logic
 */
abstract class BaseFragment<VM: BaseViewModel> : Fragment {
    abstract val viewModel: VM

    constructor() : super()
    constructor(contentLayoutId: Int) : super(contentLayoutId)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TODO: implement base navigation logic and error handling
    }

    override fun onStart() {
        super.onStart()

        val activityVM: ActivityViewModel by activityViewModels()
        viewModel.loading.observe { isLoading ->
            if (!handleLoading(isLoading)) {
                activityVM.loading.value = isLoading
            }
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.loading.removeObservers(this)
    }

    /**
     * Handle the loading state change, or let the hostr Activity handle it
     *
     * @return true if the loading change handled in the fragment, false if should fallback to the host activity
     */
    abstract fun handleLoading(isLoading: Boolean): Boolean

    protected fun <T> LiveData<T>.observe(onChanged: (T) -> Unit) {
        observe(viewLifecycleOwner, Observer(onChanged))
    }
}