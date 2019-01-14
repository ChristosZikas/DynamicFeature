package dynamic.modules.chris.dynamicfeature

import android.util.Log
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallRequest
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

data class InstalledModule(val installedModule: Module)
data class NotInstalledModule(val notInstalledModule: Module)

data class UninstallModuleCmd(val moduleName: String)
data class InstallModuleCmd(val moduleName: String)

object ResetModuleLists

class ModuleUseCase(private val splitInstallManager: SplitInstallManager) {
    private val bus = EventBus.getDefault()

    init {
        bus.register(this)
        updateModuleViews()
    }

    private fun updateModuleViews() {
        bus.post(ResetModuleLists)
        MODULES.forEach {
            if (splitInstallManager.installedModules.contains(it.key))
                bus.post(InstalledModule(Module(it.value)))
            else
                bus.post(NotInstalledModule(Module(it.value)))
        }
    }

    companion object {
        private val MODULES = mapOf(
            "dynamic_feature1" to "Module 1",
            "dynamic_feature2" to "Module 2",
            "dynamic_feature3" to "Module 3",
            "dynamic_featureX" to "Module X"
        )
    }

    private fun getKey(moduleName: String) = MODULES.filter { it.value == moduleName }
        .map { it.key }

    @Subscribe
    fun on(u: InstallModuleCmd) {
        val key = getKey(u.moduleName)

        val request = SplitInstallRequest
            .newBuilder()
            .addModule(key[0])
            .build()

        splitInstallManager
            .startInstall(request)
            .addOnSuccessListener { updateModuleViews() }
            .addOnFailureListener { Log.e("InstallModuleFailure", it.toString()) }
    }

    @Subscribe
    fun on(u: UninstallModuleCmd) {
        val key = getKey(u.moduleName)
        splitInstallManager
            .deferredUninstall(key)
            .addOnSuccessListener { updateModuleViews() }
            .addOnFailureListener { Log.e("UnInstallModuleFailure", it.toString()) }
    }

}