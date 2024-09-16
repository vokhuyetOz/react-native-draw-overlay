package com.vokhuyet.reactnativedrawoverlay

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.Promise

abstract class ReactNativeDrawOverlaySpec internal constructor(context: ReactApplicationContext) :
  ReactContextBaseJavaModule(context) {

  abstract fun requestOverlayPermission(promise: Promise)
  abstract fun isOverlayPermissionGranted(promise: Promise)
}
