package com.heid.frame.dagger

import com.heid.frame.bus.AppBus
import com.squareup.otto.Bus
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @package     com.heid.frame.dagger
 * @author      lucas
 * @date        2018/11/23
 * @des         提供一些通用型工具
 */
@Module
class FrameAppModule {
    @Singleton
    @Provides
    fun provideBus() = AppBus
}