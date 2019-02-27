package com.lucas.brush.dagger

import com.lucas.brush.main.home.MainFragment
import com.lucas.brush.main.personal.PersonalFragment
import com.lucas.brush.main.task.TaskFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @package     com.heid.frame20181119.dagger
 * @author      lucas
 * @date        2018/11/26
 * @des         提供fragment的注入
 */
@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun injectMainFragment(): MainFragment

    @ContributesAndroidInjector
    abstract fun injectTaskFragment(): TaskFragment

    @ContributesAndroidInjector
    abstract fun injectPersonalFragment(): PersonalFragment
}