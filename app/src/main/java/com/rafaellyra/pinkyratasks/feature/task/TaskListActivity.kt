package com.rafaellyra.pinkyratasks.feature.task

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.rafaellyra.pinkyratasks.R
import com.rafaellyra.pinkyratasks.data.repository.impl.task.TaskRetrofitApi
import com.rafaellyra.pinkyratasks.data.repository.impl.task.exception.TaskFetchException
import com.rafaellyra.pinkyratasks.retrofit.base.RetrofitConfig
import com.rafaellyra.pinkyratasks.retrofit.task.event.TaskFetchEvent
import kotlinx.android.synthetic.main.activity_task_list.*
import kotlinx.android.synthetic.main.content_task_list.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class TaskListActivity : AppCompatActivity() {

    val taskListAdapter: TaskListAdapter = TaskListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)
        setSupportActionBar(toolbar)

        setupViews()
        initActivity()
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
        taskListAdapter.finalize()
    }

    private fun initActivity() {
        EventBus.getDefault().register(this)
        val taskRepo = TaskRetrofitApi(RetrofitConfig().init())
        taskRepo.list()
    }

    private fun setupViews() {
        fab.setOnClickListener { view ->
            Snackbar.make(view, "TODO: Criar tela de criação", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        rv_task_list.adapter = taskListAdapter
        rv_task_list.layoutManager = LinearLayoutManager(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_task_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    @Subscribe
    fun onTaskFetch(event: TaskFetchEvent){
        taskListAdapter.changeDataset(event.data)
    }

    @Subscribe
    fun onTaskFetchFail(error: TaskFetchException) {
        Snackbar.make(root, error.message ?: "Erro desconhecido" , Snackbar.LENGTH_LONG).show()
    }
}
