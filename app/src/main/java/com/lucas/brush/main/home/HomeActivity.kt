package com.lucas.brush.main.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import com.heid.frame.base.activity.BaseNetActivity
import com.heid.frame.data.api.BaseModel
import com.heid.frame.data.bean.IBean
import com.lucas.brush.R
import com.lucas.brush.main.personal.PersonalFragment
import com.lucas.brush.main.task.TaskFragment
import kotlinx.android.synthetic.main.activity_home.*

/**
 * @package    HomeActivity.kt
 * @anthor     luan
 * @date       9:35 AM
 * @des        主架构
 */
class HomeActivity : BaseNetActivity<HomePresenter>() {

    companion object {
        fun launch(activity:Activity){
            val intent = Intent(activity, HomeActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun reRequest() {
    }

    override fun initView(savedInstanceState: Bundle?) {
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        val taskFragment = TaskFragment()
        val personalFragment = PersonalFragment()
        supportFragmentManager.beginTransaction()
                .add(R.id.v_content,MainFragment(),"home")
                .add(R.id.v_content,taskFragment,"task").hide(taskFragment)
                .add(R.id.v_content,personalFragment,"my").hide(personalFragment)
                .commitAllowingStateLoss()
    }

    override fun initData() {
    }

    override fun getLayoutID(): Int =R.layout.activity_home

    override fun requestSuccess(bean: IBean, requestMode: BaseModel.RequestMode, requestTag: Any) {
    }


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val homeFragment = supportFragmentManager.findFragmentByTag("home")
        val taskFragment = supportFragmentManager.findFragmentByTag("task")
        val myFragment = supportFragmentManager.findFragmentByTag("my")
        when (item.itemId) {
            R.id.navigation_home -> {
                supportFragmentManager.beginTransaction().show(homeFragment!!)
                        .hide(taskFragment!!).hide(myFragment!!).commitAllowingStateLoss()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_task -> {
                supportFragmentManager.beginTransaction().hide(homeFragment!!)
                        .show(taskFragment!!).hide(myFragment!!).commitAllowingStateLoss()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_my -> {
                supportFragmentManager.beginTransaction().hide(homeFragment!!)
                        .hide(taskFragment!!).show(myFragment!!).commitAllowingStateLoss()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

}
