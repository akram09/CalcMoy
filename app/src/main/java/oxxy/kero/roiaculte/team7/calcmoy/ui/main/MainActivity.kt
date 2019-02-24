package oxxy.kero.roiaculte.team7.calcmoy.ui.main

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import oxxy.kero.roiaculte.team7.calcmoy.R
import oxxy.kero.roiaculte.team7.calcmoy.base.BaseActivity
import oxxy.kero.roiaculte.team7.calcmoy.base.BaseFragment
import oxxy.kero.roiaculte.team7.calcmoy.databinding.DrawerLayoutBinding
import oxxy.kero.roiaculte.team7.calcmoy.ui.main.eventsfragment.EventsFragment
import oxxy.kero.roiaculte.team7.calcmoy.ui.main.mainfragment.MainFragment
import oxxy.kero.roiaculte.team7.calcmoy.ui.main.modulesfragment.ModulesFragment
import oxxy.kero.roiaculte.team7.calcmoy.ui.main.moyennefragment.MoyenneFragment
import javax.inject.Inject
private const val LOG_TAG ="MAIN_ACTIVITY"
class MainActivity :BaseActivity(){
    val viewModel :MainActivityViewModel by lazy { ViewModelProviders.of(this)[MainActivityViewModel::class.java] }
    val callback :MainActivityCallback by lazy { viewModel }
    companion object { fun getIntent(context : Context)= Intent(context ,MainActivity::class.java) }

    lateinit var datBinding:DrawerLayoutBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        datBinding= DataBindingUtil.setContentView(this , R.layout.drawer_layout)

        if(savedInstanceState!=null) callback.init()

        viewModel.observe(this ){
            val whichFragment =it?.navigationFragment?.getContentIfNotHandled()
            if(whichFragment!=null){
                handleNavigationEvent(whichFragment , it.showAddMenu.getContentIfNotHandled())
            }
        }

        datBinding.mainActivityIncluded.navigation.setOnNavigationItemSelectedListener {
             callback.onNavigationBottomClicked(when(it.itemId){
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
       return when(item?.itemId){
           android.R.id.home-> {
               datBinding.drawerLayout.openDrawer(GravityCompat.END)
               true
           }
           R.id.main_menu_add-> {
               Log.e(LOG_TAG , "add button clicked")
               true
           }
           else ->super.onOptionsItemSelected(item)
        }
    }

    private fun handleNavigationEvent(fragment :Pair<Int , BaseFragment> , boolean: Boolean?) {
        if(boolean !=null  ) invalidateOptionsMenu()
        setToolbarAndDrawer(fragment.first)
        supportFragmentManager.beginTransaction().replace(
            R.id.main_container,fragment.second
        ).commit()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu )
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
           menu?.findItem(R.id.main_menu_add)?.isVisible = callback.getShowAddButton()
     return true
    }
    fun setToolbarAndDrawer( title:Int ){
        datBinding.mainActivityIncluded.mainToolbar.setTitle(title)
        setSupportActionBar(datBinding.mainActivityIncluded.mainToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val toggle = CustomeToggle(this,datBinding.drawerLayout,
            R.string.navigation_drawer_open,R.string.navigation_drawer_close)
        datBinding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }
}

interface MainActivityCallback{
    fun init()
    fun onNavigationBottomClicked(id:Int)
    fun getShowAddButton():Boolean
}