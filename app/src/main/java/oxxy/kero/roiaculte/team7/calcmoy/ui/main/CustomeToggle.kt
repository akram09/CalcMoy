package oxxy.kero.roiaculte.team7.calcmoy.ui.main

import android.app.Activity
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.MenuItem

class CustomeToggle (
    activity: Activity?,
    val drawerLayout: DrawerLayout?,
    openDrawerContentDescRes: Int,
    closeDrawerContentDescRes: Int
): ActionBarDrawerToggle(activity,drawerLayout,openDrawerContentDescRes,closeDrawerContentDescRes) {



    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if(item != null && item.itemId == android.R.id.home){
            if (drawerLayout?.isDrawerOpen(Gravity.RIGHT)!!){
                drawerLayout.closeDrawer(Gravity.RIGHT)
            }else drawerLayout.openDrawer(Gravity.RIGHT)
        }
        return super.onOptionsItemSelected(item)
    }
}