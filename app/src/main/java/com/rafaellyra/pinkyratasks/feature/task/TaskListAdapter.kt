package com.rafaellyra.pinkyratasks.feature.task

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rafaellyra.pinkyratasks.R
import com.rafaellyra.pinkyratasks.data.model.TaskModel
import kotlinx.android.synthetic.main.task_list_item.view.*

class TaskListAdapter(private var tasks: List<TaskModel> = ArrayList<TaskModel>()): RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {

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
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val title = view.text_task_title
        val content = view.text_task_content
    }
}