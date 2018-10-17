package com.alway.lequ_kotlin.ui.base

import android.Manifest
import android.app.AlertDialog
import android.support.annotation.StringRes
import com.alway.lequ_kotlin.R
import permissions.dispatcher.*


/**
 * 创建人: Jeven
 * 邮箱:   Osboenjie@163.com
 * 功能:   request permission
 */

@RuntimePermissions
abstract class PermissionCheckerDelegate : BaseDelegate() {

    @NeedsPermission(Manifest.permission.CAMERA)
    fun showCamera() {

    }

    @OnShowRationale(Manifest.permission.CAMERA)
    fun showRationaleForCamera(request: PermissionRequest) {
        showRationaleDialog(R.string.permission_camera_rationale, request)
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    fun onCameraDenied() {
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    fun onCameraNeverAskAgain() {
    }

    private fun showRationaleDialog(@StringRes messageResId: Int, request: PermissionRequest) {
        AlertDialog.Builder(_mActivity)
                .setPositiveButton(R.string.button_allow) { _, _ -> request.proceed() }
                .setNegativeButton(R.string.button_deny) { _, _ -> request.cancel() }
                .setCancelable(false)
                .setMessage(messageResId)
                .show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

}
