package com.xcyoung.cyberframe.base

data class PermissionRequest(val isGranted: Boolean,
                             val permissions: List<String> = emptyList()) {

}

