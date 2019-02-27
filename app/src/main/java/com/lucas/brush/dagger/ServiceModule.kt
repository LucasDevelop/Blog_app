package com.lucas.brush.dagger

import com.lucas.brush.server.TaskServer
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @package    com.lucas.brush.dagger
 * @anthor     luan
 * @date       2019/2/22
 * @des
 */
@Module
abstract class ServiceModule {

    @ContributesAndroidInjector
    abstract fun injectTaskServer():TaskServer
}