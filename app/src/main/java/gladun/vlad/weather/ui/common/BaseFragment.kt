package gladun.vlad.weather.ui.common

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import gladun.vlad.weather.R
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

        viewModel.action.observe {
            it.getContentIfNotHandled()?.let { unhandledAction ->
                executeAction(unhandledAction)
            }
        }

        (activity as? AppCompatActivity)?.apply {
            supportActionBar?.let {
                if (screenTitleResId == null) {
                    it.setDisplayShowTitleEnabled(false)
                } else {
                    it.setTitle(screenTitleResId!!)
                    it.setDisplayShowTitleEnabled(true)
                }
                it.setDisplayHomeAsUpEnabled(showUp)
            }
        }
    }

    open fun handleBack(): Boolean {
        // Give any child fragments the chance to answer first (from most recently attached/added to least)
        childFragmentManager.fragments.reversed().forEach {
            if (it.isVisible && (it as? BaseFragment<*>)?.handleBack() == true) {
                return true
            }
        }
        return viewModel.onBackPressed()
    }

    abstract val screenTitleResId: Int?
    abstract val showUp: Boolean
    open fun getBackButtonIcon() = R.drawable.ic_arrow_back_24dp

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

    open fun executeAction(action: Action) {
        when (action) {
            is Action.NavigateTo -> findNavController().navigate(action.directions, action.navOptions)
            is Action.NavigateBack -> {
                if (action.toDestinationId != null) {
                    findNavController().popBackStack(action.toDestinationId, action.inclusive)
                }
                else {
                    findNavController().navigateUp()
                }
            }
        }
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