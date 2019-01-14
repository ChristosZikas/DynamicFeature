package dynamic.modules.chris.dynamicfeature

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.rv_installed_module.view.*
import org.greenrobot.eventbus.EventBus

class InstalledModuleAdapter(private val moduleList: ArrayList<Module>) :
    RecyclerView.Adapter<InstalledModuleViewHolder>() {

    val bus = EventBus.getDefault()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstalledModuleViewHolder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_installed_module, parent, false)

        return InstalledModuleViewHolder(itemView)

    }

    override fun getItemCount(): Int {
        return moduleList.size
    }

    override fun onBindViewHolder(holder: InstalledModuleViewHolder, position: Int) {
        holder.installedView.text = moduleList[position].title
    }

}

class InstalledModuleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val bus = EventBus.getDefault()!!
    val installedView = view.title!!
    val removeModule = view.uninstall_module.setOnClickListener { bus.post(
        UninstallModuleCmd(installedView.text.toString()
        )
    ) }
}

