package com.vokhuyet.reactnativedrawoverlay

import com.facebook.react.bridge.ReactApplicationContext

import android.app.Activity
import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Binder
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.WindowManager
import com.facebook.react.bridge.*

class ReactNativeDrawOverlayModule internal constructor(private val reactContext: ReactApplicationContext) :
  ReactNativeDrawOverlaySpec(reactContext), ActivityEventListener {

  override fun getName(): String {
    return NAME
  }

  private var permissionPromise: Promise? = null

  private fun canDrawOverlays(context: Context): Boolean {
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M && Settings.canDrawOverlays(context)) {
        return true
    }
    //USING APP OPS MANAGER
    val manager = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager?
    if (manager != null) {
        try {
            val result: Int = manager.checkOp(AppOpsManager.OPSTR_SYSTEM_ALERT_WINDOW, Binder.getCallingUid(), context.packageName)
            return result == AppOpsManager.MODE_ALLOWED
        } catch (ignore: Exception) {
        }
    }
    try { //IF This Fails, we definitely can't do it
        val mgr = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            ?: return false
        //getSystemService might return null
        val viewToAdd = View(context)
        val params = WindowManager.LayoutParams(0, 0, if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY else WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSPARENT)
        viewToAdd.layoutParams = params
        mgr.addView(viewToAdd, params)
        mgr.removeView(viewToAdd)
        return true
    } catch (ignore: Exception) {
    }
    return false
}

override fun onActivityResult(activity: Activity?, requestCode: Int, resultCode: Int, data: Intent?) {
    if (requestCode == REQUEST_CODE_CAN_DRAW_OVERLAYS) {
        permissionPromise?.resolve(canDrawOverlays(reactContext))
        permissionPromise = null
    }
}

override fun onNewIntent(intent: Intent?) {}

@ReactMethod
override fun requestOverlayPermission(promise: Promise) {
    try {
        if (canDrawOverlays(reactContext)) {
            promise.resolve(true)
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                permissionPromise = promise
                val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + reactContext.packageName))
                reactContext.startActivityForResult(intent, REQUEST_CODE_CAN_DRAW_OVERLAYS, null)
            } else {
                promise.resolve(false)
            }
        }
    } catch (e: Error) {
        promise.reject(e)
    }
}

  @ReactMethod
  override fun isOverlayPermissionGranted(promise: Promise) {
    try {
        promise.resolve(canDrawOverlays(reactContext))
    } catch (e: Error) {
        promise.reject(e)
    }
}

  companion object {
    const val NAME = "ReactNativeDrawOverlay"
    private const val REQUEST_CODE_CAN_DRAW_OVERLAYS = 3215
  }
}
