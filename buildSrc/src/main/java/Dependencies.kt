import Versions.assertJVersion
import Versions.daggerVersion
import Versions.glassfishAnnotationVersion
import Versions.glideVersion
import Versions.gsonVersion
import Versions.jUnitVersion
import Versions.javaxAnnotationVersion
import Versions.javaxInjectVersion
import Versions.kotlinVersion
import Versions.lifecycleVersion
import Versions.loggingInterceptorVersion
import Versions.mockitoKotlinVersion
import Versions.moshiVersion
import Versions.okHttpProfilingVersion
import Versions.okHttpVersion
import Versions.retrofitVersion
import Versions.roboelectricVersion
import Versions.roomVersion
import Versions.rxKotlinVersion
import Versions.rxandroidVersion
import Versions.timberVersion

object Config {
    val compileSdkVersion = 28
    val minSdkVersion = 17
    val targetSdkVersion = 28
    val versionCode = 1
    val versionName =  "1.0.0"

}
object Versions {
    val kotlinVersion = "1.3.31"
    val javaxAnnotationVersion = "1.0"
    val javaxInjectVersion = "1"
    val rxJavaVersion = "2.2.12"
    val rxKotlinVersion = "2.4.0"
    val jUnitVersion = "4.12"
    val mockitoKotlinVersion = "2.2.0"
    val assertJVersion = "3.8.0"
    val lifecycleVersion = "2.1.0"
    val roomVersion = "2.2.0-rc01"
    val roboelectricVersion = "4.3"
    val okHttpVersion = "4.2.0"
    val retrofitVersion = "2.4.0"
    val loggingInterceptorVersion = "3.10.0"
    val okHttpProfilingVersion = "1.0.4"
    val daggerVersion = "2.16"
    val glideVersion = "4.6.1"
    val moshiVersion = "1.4.0"
    val gsonVersion = "2.8.2"
    val timberVersion = "4.7.0"
    val rxandroidVersion = "2.1.1"
    val glassfishAnnotationVersion = "10.0-b28"
}

object Libraries {
    val javaxAnnotation = "javax.annotation:jsr250-api:$javaxAnnotationVersion"
    val javaxInject = "javax.inject:javax.inject:$javaxInjectVersion"
    val rxKotlin = "io.reactivex.rxjava2:rxkotlin:$rxKotlinVersion"
    val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion"

    //room
    val roomRunTime = "androidx.room:room-runtime:$roomVersion"
    val roomCompiler = "androidx.room:room-compiler:$roomVersion"
    val roomExtensionAndCoroutine = "androidx.room:room-ktx:$roomVersion"
    val roomRxJava = "androidx.room:room-rxjava2:$roomVersion"
    // optional - Guava support for Room, including Optional and ListenableFuture
    val roomGuava =  "androidx.room:room-guava:$roomVersion"

    //lifecycle libraries
    val lifecyleRuntime = "androidx.lifecycle:lifecycle-runtime:$lifecycleVersion"
    val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:$lifecycleVersion"
    val lifecycleCompiler = "androidx.lifecycle:lifecycle-compiler:$lifecycleVersion"
    val lifecycleRxStreams = "androidx.lifecycle:lifecycle-reactivestreams:$lifecycleVersion"

    //android
    val rxandroid = "io.reactivex.rxjava2:rxandroid:$rxandroidVersion"
    //networking
    val moshi = "com.squareup.moshi:moshi:$moshiVersion"
    val gson = "com.google.code.gson:gson:$gsonVersion"
    val okHttp = "com.squareup.okhttp3:okhttp:$okHttpVersion"

    val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
    val retrofitConverter =  "com.squareup.retrofit2:converter-gson:$retrofitVersion"
    val retrofitAdapter =    "com.squareup.retrofit2:adapter-rxjava2:$retrofitVersion"
    val rxjavaAdapter = "com.squareup.retrofit2:adapter-rxjava2:$retrofitVersion"
    val moshiConverter = "com.squareup.retrofit2:converter-moshi:$retrofitVersion"
    val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:$loggingInterceptorVersion"
    val okHttpProfiling = "com.itkacher.okhttpprofiler:okhttpprofiler:$okHttpProfilingVersion"

    //dagger
    val dagger = "com.google.dagger:dagger-android:$daggerVersion"
    val daggerSupport = "com.google.dagger:dagger-android-support:$daggerVersion"
    val daggerCompiler = "com.google.dagger:dagger-compiler:$daggerVersion"
    val daggerProcessor = "com.google.dagger:dagger-android-processor:$daggerVersion"

    val glide = "com.github.bumptech.glide:glide:$glideVersion"
    val glideCompiler = "com.github.bumptech.glide:compiler:$glideVersion"

    //logging
    val timber = "com.jakewharton.timber:timber:$timberVersion"

    //others
    val glassfishAnnotation = "org.glassfish:javax.annotation:$glassfishAnnotationVersion"

}

object TestLibraries {
    //Testing dependencies
    val jUnit = "junit:junit:$jUnitVersion"
    val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:$mockitoKotlinVersion"
    val assertJ = "org.assertj:assertj-core:$assertJVersion"
    val roboelectric = "org.robolectric:robolectric:$roboelectricVersion"
    val lifecycleTest = "androidx.arch.core:core-testing:$lifecycleVersion"
    val roomTest = "androidx.room:room-testing:$roomVersion"
}