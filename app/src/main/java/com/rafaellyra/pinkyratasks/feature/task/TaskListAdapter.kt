package com.rafaellyra.pinkyratasks.feature.task

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rafaellyra.pinkyratasks.R
import com.rafaellyra.pinkyratasks.data.model.TaskModel
import com.rafaellyra.pinkyratasks.data.repository.impl.user.UserRetrofitApi
import com.rafaellyra.pinkyratasks.retrofit.base.RetrofitConfig
import com.rafaellyra.pinkyratasks.retrofit.user.event.UserFetchEvent
import kotlinx.android.synthetic.main.task_list_item.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class TaskListAdapter(private var tasks: List<TaskModel> = ArrayList<TaskModel>()): RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {

    val userRepository = UserRetrofitApi(RetrofitConfig().init())

    init {
        EventBus.getDefault().register(this)
    }

    fun finalize() {
        EventBus.getDefault().unregister(this)
    }

    fun changeDataset(value: List<TaskModel>) {
        tasks = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = tasks[position]
        holder.title.text = task.title
        holder.content.text = task.body
        if(task.user == null) {
            userRepository.getForAdapterPosition(task.id, position)
        } else {
            holder.username.text = task.user.username
        }
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val title = view.text_task_title
        val content = view.text_task_content
        val username = view.text_task_username
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onUserFecth(event: UserFetchEvent) {
        if (event.adapterPosition == -1) {
            return
        }
        if (event.data.size == 0) {
            return
        }

        tasks.get(event.adapterPosition).user = event.data[0]
        notifyItemChanged(event.adapterPosition)
    }
}