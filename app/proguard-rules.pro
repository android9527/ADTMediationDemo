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



 -dontwarn com.adtiming.mediationsdk.**.*
 -dontwarn com.mopub.**.*
 -dontwarn com.aiming.mdt.**.*
 -dontskipnonpubliclibraryclasses
 #AdTiming
 -keep class com.adtiming.mediationsdk.mediation.**{*;}
 -keep class com.mopub.**{*;}
 -keep class com.aiming.mdt.**{*;}
 -keep class com.adtiming.mediationsdk.mobileads.**{*;}

 #R
 -keepclassmembers class **.R$* {
 public static <fields>;
 }
 -keepattributes *Annotation*
 -keepattributes InnerClasses
 -keepnames class * implements android.os.Parcelable {
 public static final ** CREATOR;
}

-keep class com.applovin.** { *;}