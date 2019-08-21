package com.xcyoung.cyberframe.base

import android.content.Context
import android.content.pm.PackageManager

import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tbruyelle.rxpermissions2.RxPermissions
import com.xcyoung.cyberframe.http.XException
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * @author ChorYeung
 * @since 2018/10/26
 */
abstract class BaseViewModel : ViewModel(){
    private var compositeDisposable : CompositeDisposable = CompositeDisposable()   //用于rx订阅
    val exceptionLiveData = MutableLiveData<XException>()        //处理异常的LiveData
    val isLoadingLiveData = MutableLiveData<Boolean>()          //处理loading效果的LiveData
    val requestPermissionsLiveData = MutableLiveData<PermissionRequest>()
    override fun onCleared() {
        super.onCleared()
        if(compositeDisposable.isDisposed){
            compositeDisposable.dispose()
        }
    }

    protected fun addDisposable(disposable: Disposable){
        compositeDisposable.add(disposable)
    }

    // permission begin
    fun requestPermission(activity: FragmentActivity, permissionsArray: Array<String>) {
        val rxPermissions = RxPermissions(activity)
        addDisposable(rxPermissions.requestEachCombined(*permissionsArray)
            .subscribe {
                when {
                    it.granted -> {
                        requestPermissionsLiveData.value = PermissionRequest(true)
                    }
                    it.shouldShowRequestPermissionRationale -> {
                        requestPermissionsLiveData.value = PermissionRequest(false)
                    }
                    else -> {
                        redirectToPermissionSetting(activity)
                    }
                }
            })
    }

    fun redirectToPermissionSetting(context: Context) {
        //SettingCompat.start(context, 200)
    }

    fun hasPermissions(context: Context, permission: Array<String>): Boolean {
        permission.forEach {
            val isGranted = ContextCompat.checkSelfPermission(context, it) ==
                    PackageManager.PERMISSION_GRANTED
            if (!isGranted) return false
        }
        return true
    }
    // permission end

}