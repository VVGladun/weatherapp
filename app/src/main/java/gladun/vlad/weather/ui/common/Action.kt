package gladun.vlad.weather.ui.common

import androidx.annotation.IdRes
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions

/**
 * Different types of navigation inside (or outside) the app
 */
sealed class Action {

    data class NavigateTo(val directions: NavDirections, val navOptions: NavOptions? = null) : Action()

    data class NavigateBack(@IdRes val toDestinationId: Int? = null, val inclusive: Boolean = false) : Action()
}
