package com.lucas.brush.dagger


import com.lucas.brush.main.SplashActivity
import com.lucas.brush.main.auther.forget.ForgetActivity
import com.lucas.brush.main.auther.login.LoginActivity
import com.lucas.brush.main.auther.register.RegisterActivity
import com.lucas.brush.main.home.HomeActivity
import com.lucas.brush.main.personal.blogs.BlogsActivity
import com.lucas.brush.main.personal.blogs.add.AddBlogActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @package     com.heid.frame20181119
 * @author      lucas
 * @date        2018/11/23
 * @des
 */
@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun injectHomeActivity(): HomeActivity

    @ContributesAndroidInjector
    abstract fun injectLoginActivity(): LoginActivity

    @ContributesAndroidInjector
    abstract fun injectRegisterActivity(): RegisterActivity

    @ContributesAndroidInjector
    abstract fun injectForgetActivity(): ForgetActivity

    @ContributesAndroidInjector
    abstract fun injectSplashActivity(): SplashActivity

    @ContributesAndroidInjector
    abstract fun injectBlogsActivity(): BlogsActivity

    @ContributesAndroidInjector
    abstract fun injectAddBlogActivity(): AddBlogActivity



}