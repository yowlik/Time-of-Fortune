# Onesignal
-dontwarn com.onesignal.**

-keep class com.onesignal.ActivityLifecycleListenerCompat** {*;}

# Facebook
-keep class com.facebook.** {
   *;
}



-keepnames class * implements android.os.Parcelable
-keepclassmembers class * implements android.os.Parcelable {
  public static final *** CREATOR;
}
#Lottie
-dontwarn com.airbnb.lottie.**
-keep class com.airbnb.lottie.** {*;}

-keep class kotlinx.coroutines.**
-dontwarn kotlinx.coroutines.**#-renamesourcefileattribute SourceFile