package dynamic.modules.chris.dynamicfeature

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var moduleUseCase: ModuleUseCase
    }

    private var installedModuleList: ArrayList<Module> = arrayListOf()
    private var notInstalledModuleList: ArrayList<Module> = arrayListOf()

    private lateinit var installedRecyclerView: RecyclerView
    private lateinit var notInstalledRecyclerView: RecyclerView

    private lateinit var installedAdapter: InstalledModuleAdapter
    private lateinit var notInstalledAdapter: NotInstalledModuleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        installedRecyclerView = findViewById(R.id.rv_installed)
        notInstalledRecyclerView = findViewById(R.id.rv_not_installed)

        installedAdapter = InstalledModuleAdapter(installedModuleList)
        notInstalledAdapter = NotInstalledModuleAdapter(notInstalledModuleList)

        EventBus.getDefault().register(this)

        moduleUseCase = ModuleUseCase(SplitInstallManagerFactory.create(this))

        val iLayoutManager = LinearLayoutManager(this)
        val nILayoutManager = LinearLayoutManager(this)

        installedRecyclerView.layoutManager = iLayoutManager
        installedRecyclerView.adapter = installedAdapter

        notInstalledRecyclerView.layoutManager = nILayoutManager
        notInstalledRecyclerView.adapter = notInstalledAdapter

        installedAdapter.notifyDataSetChanged()
        notInstalledAdapter.notifyDataSetChanged()
    }


    @Subscribe
    fun on(iModule: InstalledModule) {
        installedModuleList.add(iModule.installedModule)
        installedAdapter.notifyDataSetChanged()
    }

    @Subscribe
    fun on(nIModule: NotInstalledModule) {
        notInstalledModuleList.add(nIModule.notInstalledModule)
        notInstalledAdapter.notifyDataSetChanged()
    }

    @Subscribe
    fun on(e: ResetModuleLists) {
        installedModuleList.clear()
        notInstalledModuleList.clear()
    }


}
