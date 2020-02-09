# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#Timber
-dontwarn org.jetbrains.annotations.**
# OkHttp
-keepattributes Signature
-keepattributes *Annotation*
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**
-dontwarn okio.**
#Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
#Crashlytics
-keep class com.crashlytics.** { *; }
-dontwarn com.crashlytics.**
#FragmentArgs
-keep class com.hannesdorfmann.fragmentargs.** { *; }
#Mockito
-dontwarn org.mockito.**
#Junit
-dontwarn android.test.**
#Turn off Logging
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
    public static *** e(...);
    public static *** w(...);
    public static *** wtf(...);
}

#############################################
#
# 对于一些基本指令的添加
#
#############################################
# 代码混淆压缩比，在 0~7 之间，默认为 5，一般不做修改
-optimizationpasses 5

# 混合时不使用大小写混合，混合后的类名为小写
-dontusemixedcaseclassnames

# 指定不去忽略非公共库的类
-dontskipnonpubliclibraryclasses

# 这句话能够使我们的项目混淆后产生映射文件
# 包含有类名->混淆后类名的映射关系
-verbose

# 指定不去忽略非公共库的类成员
-dontskipnonpubliclibraryclassmembers

# 不做预校验，preverify 是 proguard 的四个步骤之一，Android 不需要 preverify，去掉这一步能够加快混淆速度。
-dontpreverify

# 保留 Annotation 不混淆
-keepattributes *Annotation*,InnerClasses

# 避免混淆泛型
-keepattributes Signature

# 抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable

# 指定混淆是采用的算法，后面的参数是一个过滤器
# 这个过滤器是谷歌推荐的算法，一般不做更改
-optimizations !code/simplification/cast,!field/*,!class/merging/*


#############################################
#
# Android开发中一些需要保留的公共部分
#
#############################################

# 保留我们使用的四大组件，自定义的 Application 等等这些类不被混淆
# 因为这些子类都有可能被外部调用
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Appliction
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService


# 保留 support 下的所有类及其内部类
-keep class android.support.** { *; }

# 保留继承的
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.v7.**
-keep public class * extends android.support.annotation.**

# 保留 R 下面的资源
-keep class **.R$* { *; }

# 保留本地 native 方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}

# 保留在 Activity 中的方法参数是view的方法，
# 这样以来我们在 layout 中写的 onClick 就不会被影响
-keepclassmembers class * extends android.app.Activity {
    public void *(android.view.View);
}

# 保留枚举类不被混淆
-keepclassmembers enum * {
    public static ** valueOf(java.lang.String);
}

# 保留我们自定义控件（继承自 View）不被混淆
-keep public class * extends android.view.View {
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# 保留 Parcelable 序列化类不被混淆
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# 对于带有回调函数的 onXXEvent、**On*Listener 的，不能被混淆
-keepclassmembers class * {
    void *(**On*Event);
    void *(**On*Listener);
}

# webView 处理，项目中没有使用到 webView 忽略即可
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
    public *;
}
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.webView, java.lang.String);
}

# js
-keepattributes JavascriptInterface
-keep class android.webkit.JavascriptInterface { *; }
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

# @Keep
-keep @android.support.annotation.Keep class *
-keepclassmembers class * {
    @android.support.annotation.Keep *;
}

# https://proguard-rules.blogspot.com/2017/05/glide-proguard-rules.html
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# for DexGuard only
#-keepresourcexmlelements manifest/application/meta-data@value=GlideModule

# https://github.com/bumptech/glide/blob/master/library/proguard-rules.txt
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# https://github.com/Kotlin/kotlinx.coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepnames class kotlinx.coroutines.android.AndroidExceptionPreHandler {}
-keepnames class kotlinx.coroutines.android.AndroidDispatcherFactory {}

-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}

# https://github.com/square/okhttp
# okhttp
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.squareup.okhttp.* { *; }
-keep interface com.squareup.okhttp.** { *; }
-dontwarn com.squareup.okhttp.**

# okhttp 3
-keepattributes Signature
-keepattributes *Annotation*
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**

# Okio
-dontwarn com.squareup.**
-dontwarn okio.**
-keep public class org.codehaus.* { *; }
-keep public class java.nio.* { *; }

# https://square.github.io/retrofit
# Retrofit
-dontwarn retrofit.**
-keep class retrofit.** { *; }
-keepattributes Signature
-keepattributes Exceptions

# Retrofit2
-keep class retrofit2.** { *; }
-dontwarn retrofit2.**
-keepattributes Signature
-keepattributes Exceptions
-dontwarn okio.**
-dontwarn javax.annotation.**

-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontpreverify
-verbose
-dump class_files.txt
-printseeds seeds.txt
-printusage unused.txt
-printmapping mapping.txt
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

-allowaccessmodification
-keepattributes *Annotation*
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable
-repackageclasses ''

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
-dontnote com.android.vending.licensing.ILicensingService

# Preserve all native method names and the names of their classes.
-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# Preserve static fields of inner classes of R classes that might be accessed
# through introspection.
-keepclassmembers class **.R$* {
  public static <fields>;
}

# Preserve the special static methods that are required in all enumeration classes.
-keepclassmembers enum * {
    public static ** valueOf(java.lang.String);
}

-keep public class * {
    public protected *;
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
##---------------End: proguard configuration common for all Android apps ----------

#---------------Begin: proguard configuration for support library  ----------
-keep class android.support.v4.app.** { *; }
-keep interface android.support.v4.app.** { *; }
-keep class com.actionbarsherlock.** { *; }
-keep interface com.actionbarsherlock.** { *; }

# The support library contains references to newer platform versions.
# Don't warn about those in case this app is linking against an older
# platform version. We know about them, and they are safe.
-dontwarn android.support.**
-dontwarn com.google.ads.**
##---------------End: proguard configuration for Gson  ----------

##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.example.model.** { *; }

##---------------End: proguard configuration for Gson  ----------

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}