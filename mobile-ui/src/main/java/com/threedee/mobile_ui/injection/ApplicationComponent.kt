package com.threedee.mobile_ui.injection

import android.app.Application
import com.threedee.mobile_ui.BufferoosApplication
import com.threedee.mobile_ui.injection.module.ApplicationModule
import com.threedee.mobile_ui.injection.module.CacheModule
import com.threedee.mobile_ui.injection.module.DataModule
import com.threedee.mobile_ui.injection.module.PresentationModule
import com.threedee.mobile_ui.injection.module.RemoteModule
import com.threedee.mobile_ui.injection.module.UiModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by Emem on 11/8/18.
 */
@Singleton
@Component(modules = [AndroidSupportInjectionModule::class,
        ApplicationModule::class,
        UiModule::class,
        PresentationModule::class,
        DataModule::class,
        CacheModule::class,
        RemoteModule::class])
interface ApplicationComponent: AndroidInjector<BufferoosApplication> {

    // old way for component builder this is very stable keeping this for reference
    /*@Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }*/

    /*fun inject(app: RideonApplication)*/

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun create(app: Application):Builder

        fun build(): ApplicationComponent
    }

    //new way for component builder works with 2.14.1
    /*@Component.Builder
    abstract class Builder : AndroidInjector.Builder<RideonApplication>()*/

}