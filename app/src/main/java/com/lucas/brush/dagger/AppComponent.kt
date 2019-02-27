package com.lucas.brush.dagger

import com.heid.frame.dagger.FrameAppModule
import com.heid.frame.data.api.ApiModule
import com.lucas.brush.App
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * @package     com.heid.frame20181119
 * @author      lucas
 * @date        2018/11/23
 * @des
 */
@Singleton
@Component(
    modules = [
        ActivityModule::class,
        FragmentModule::class,
        ServiceModule::class,
        BroadcastModule::class,
        AppModule::class,
        ApiModule::class,
        CommonModule::class,
        FrameAppModule::class,
        AndroidSupportInjectionModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()
}