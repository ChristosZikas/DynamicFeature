package dynamic.modules.chris.dynamicfeature

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.rv_not_installed_module.view.*
import org.greenrobot.eventbus.EventBus

class NotInstalledModuleAdapter(private val moduleList: ArrayList<Module>) : RecyclerView.Adapter<NotInstalledModuleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotInstalledModuleViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_not_installed_module, parent, false)

        return NotInstalledModuleViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return moduleList.size
    }

    override fun onBindViewHolder(holder: NotInstalledModuleViewHolder, position: Int) {
        holder.notInstalledView.text = moduleList[position].title
    }

}

class NotInstalledModuleViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    private val bus = EventBus.getDefault()!!
    val notInstalledView = view.title!!
    val removeModule = view.install_module.setOnClickListener {
        bus.post(InstallModuleCmd(notInstalledView.text.toString()))
    }
}