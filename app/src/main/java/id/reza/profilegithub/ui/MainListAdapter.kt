package id.reza.profilegithub.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.reza.profilegithub.databinding.ListItemBinding
import id.reza.profilegithub.model.OneData

class MainListAdapter(val list: List<OneData>) : RecyclerView.Adapter<MainListAdapter.ViewHolder>() {

    lateinit var context: Context

    var mInterface: Interface? = null

    fun setInterface(mInterface: Interface?) {
        this.mInterface = mInterface
    }

    interface Interface {
        fun onClickDetail(item: OneData, position: Int)
    }

    class ViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        context = parent.context
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]

        holder.binding.txtId.text = "${data.id}"
        holder.binding.txtUsername.text = "${data.login}"
        holder.binding.txtRepoUrl.text = "${data.repos_url}"

        Glide.with(context).load(data.avatar_url).into(holder.binding.imgThumbnail)

        holder.binding.container.setOnClickListener {
            mInterface!!.onClickDetail(data, position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}