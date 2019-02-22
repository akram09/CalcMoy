package oxxy.kero.roiaculte.team7.calcmoy.ui.main

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.util.Log
import android.view.MenuItem
import oxxy.kero.roiaculte.team7.calcmoy.R
import oxxy.kero.roiaculte.team7.calcmoy.base.BaseActivity
import oxxy.kero.roiaculte.team7.calcmoy.databinding.DrawerLayoutBinding
import oxxy.kero.roiaculte.team7.calcmoy.ui.main.eventsfragment.EventsFragment
import oxxy.kero.roiaculte.team7.calcmoy.ui.main.mainfragment.MainFragment
import oxxy.kero.roiaculte.team7.calcmoy.ui.main.modulesfragment.ModulesFragment
import oxxy.kero.roiaculte.team7.calcmoy.ui.main.moyennefragment.MoyenneFragment
import javax.inject.Inject
private const val LOG_TAG ="MAIN_ACTIVITY"
class MainActivity :BaseActivity(){

    val viewModel :MainActivityViewModel by lazy {
        ViewModelProviders.of(this)[MainActivityViewModel::class.java]
    }
    companion object {
        fun getIntent(context : Context)= Intent(context ,MainActivity::class.java)
    }
    lateinit var datBinding:DrawerLayoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        datBinding= DataBindingUtil.setContentView(this , R.layout.drawer_layout)
        if(savedInstanceState!=null) viewModel.init()
        viewModel.observe(this ){
            val whichFragment =it?.navigationEvent?.getContentIfNotHandled()
            if(whichFragment!=null){
                handleNavigationEvent(whichFragment)
            }
        }

        datBinding.mainActivityIncluded.navigation.setOnNavigationItemSelectedListener {
             viewModel.onNavigationBottomClicked(when(it.itemId){
                 R.id.navigation_home->0
                 R.id.navigation_module->1
                 R.id.navigation_events->2
                 R.id.navigation_moyen->3
                 else-> 0
             })
            true
        }

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
       return if(item?.itemId==android.R.id.home){
             datBinding.drawerLayout.openDrawer(GravityCompat.END)
            true
        }else super.onOptionsItemSelected(item)
    }

    private fun handleNavigationEvent(whichFragment: WhichFragment) {
 Log.e(LOG_TAG , " go to the fragment $whichFragment")
        showToast(whichFragment.toString())
        val titleFragment = when(whichFragment){
            WhichFragment.MAIN_FRAGMENT-> R.string.main to MainFragment.getInstance()
            WhichFragment.MODULES_FRAGMENT->R.string.module to ModulesFragment.getInstance()
            WhichFragment.EVENTS_FRAGMENT->R.string.evnts to EventsFragment.getInstance()
            WhichFragment.MOYENNE_FRAGMENT->R.string.moyene to MoyenneFragment.getInstance()
        }

       datBinding.mainActivityIncluded.mainToolbar.setTitle(titleFragment.first)
        setSupportActionBar(datBinding.mainActivityIncluded.mainToolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.nav_button)
        }
        supportFragmentManager.beginTransaction().replace(
            R.id.main_container,titleFragment.second
        ).addToBackStack(titleFragment.first.toString()).commit()
    }


}
interface MainActivityCallback{
    fun init()
    fun onNavigationBottomClicked(id:Int)
}