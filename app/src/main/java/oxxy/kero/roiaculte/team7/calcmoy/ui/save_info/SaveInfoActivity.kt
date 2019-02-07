package oxxy.kero.roiaculte.team7.calcmoy.ui.save_info

import android.content.Context
import android.content.Intent
import android.os.Bundle
import oxxy.kero.roiaculte.team7.calcmoy.R
import oxxy.kero.roiaculte.team7.calcmoy.base.BaseActivity
import oxxy.kero.roiaculte.team7.calcmoy.ui.save_info.fragmnets.fragment1.Fragment1
import oxxy.kero.roiaculte.team7.calcmoy.ui.save_info.fragmnets.fragment2.Fragment2
import oxxy.kero.roiaculte.team7.calcmoy.utils.extension.inTransaction


class SaveInfoActivity :BaseActivity() {

    companion object { fun getIntent(context : Context) = Intent(context,SaveInfoActivity::class.java) }

    private var fragment1 : Fragment1? = null
    private var fragment2 : Fragment2? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.save_info)
        if(savedInstanceState == null) loadFragment1()
    }

    fun loadFragment1() {
        if(fragment1 == null) fragment1 = Fragment1.getInstance()
        supportFragmentManager.inTransaction { add(R.id.save_info_container, fragment1!!) }
    }

    fun loadFragment2(bundle: Bundle) {
        if(fragment2 == null) fragment2 = Fragment2.getInstance()
        fragment2?.arguments = bundle
        supportFragmentManager.inTransaction { add(R.id.save_info_container, fragment2!!)
            .addToBackStack("save_modules")
            .setCustomAnimations ( R.anim.entre_from_right,R.anim.exit_to_left,R.anim.entre_from_left,R.anim.exit_to_right )
        }
    }


}