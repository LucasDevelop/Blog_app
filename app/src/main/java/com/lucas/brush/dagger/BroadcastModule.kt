package com.lucas.brush.dagger

import com.lucas.brush.receiver.NetworkConnChangeReceiver
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @package    com.lucas.brush.dagger
 * @anthor     luan
 * @date       2019/2/25
 * @des
 */
@Module
abstract class BroadcastModule {
    @ContributesAndroidInjector
    abstract fun injectNetworkConnChange():NetworkConnChangeReceiver
}