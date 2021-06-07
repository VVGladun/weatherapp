package gladun.vlad.weather.ui.home.list

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabsPageAdapter(hostFragment: Fragment) : FragmentStateAdapter(hostFragment) {

    companion object {
        private const val PAGE_COUNT = 3
    }

    override fun getItemCount(): Int = PAGE_COUNT

    override fun createFragment(position: Int): Fragment {
        return WeatherListPageFragment(SortSettings.values()[position])
    }
}