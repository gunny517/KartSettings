package jp.ceed.kart.settings.ui.settings.viewModel

import android.app.Application
import android.content.pm.PackageManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class SettingsFragmentViewModel(application: Application) : AndroidViewModel(application) {

    var versionName: MutableLiveData<String> = MutableLiveData()

    private val packageManager: PackageManager = application.applicationContext.packageManager

    init {
        init()
    }

    private fun init(){
        versionName.value = packageManager.getPackageInfo(getApplication<Application>().packageName, 0).versionName
    }
}