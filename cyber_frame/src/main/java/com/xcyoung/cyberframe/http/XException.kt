package com.xcyoung.cyberframe.http

/**
 * @author ChorYeung
 * @since 2018/10/26
 */
data class XException(val code: String, val msg: String) : Exception(msg)