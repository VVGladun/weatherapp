package gladun.vlad.weather.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import dagger.hilt.android.AndroidEntryPoint
import gladun.vlad.weather.R
import gladun.vlad.weather.databinding.ActivityMainBinding
import gladun.vlad.weather.ui.common.BaseFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: ActivityViewModel by viewModels()

    private val navController by lazy { findNavController(R.id.nav_host_fragment) }
    private val appBarConfiguration by lazy { AppBarConfiguration(navController.graph) }
    private val currentFragments
        get() = supportFragmentManager.primaryNavigationFragment?.childFragmentManager?.fragments

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel.loading.observe(this) {
            binding.activityLoader.isVisible = it
        }
    }

    override fun onBackPressed() {
        if (!handleBack()) {
            super.onBackPressed()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return handleBack()
                || navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    private fun handleBack(): Boolean {
        currentFragments?.reversed()?.forEach {
            if (it.isVisible && (it as? BaseFragment<*>)?.handleBack() == true) {
                return true
            }
        }
        return false
    }
}