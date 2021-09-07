package edu.moraes.mobile.todolist.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import edu.moraes.mobile.todolist.datasource.TaskDataSource
import todolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    private val adapter by lazy { TaskListAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        mainBinding.rvTasks.adapter = adapter
        upDateList()
        insertListeners()
    }

    fun insertListeners(){
        mainBinding.btnFloat.setOnClickListener(){
            startActivityForResult(Intent(this, AddTask::class.java), CREATE_NEW_TASK)
        }
        adapter.listenerEdit = {
            val intent = Intent( this, AddTask::class.java)
            intent.putExtra(AddTask.TASK_ID, it.id)
            startActivityForResult(intent, CREATE_NEW_TASK)

        }
        adapter.listenerResume = {
            TaskDataSource.deleteTask(it)
            upDateList()
        }
    }
    override fun onActivityResult(requestCode: Int,resultCode: Int, data: Intent? ){
        super.onActivityResult(requestCode,resultCode,data)
           if(requestCode == CREATE_NEW_TASK && resultCode == Activity.RESULT_OK ){
               upDateList()
           }

    }
    private fun upDateList(){
        val list = TaskDataSource.getList()
        if (list.isEmpty()){
            mainBinding.emptyView.screenEmptyState.visibility = View.VISIBLE
        } else {
            mainBinding.emptyView.screenEmptyState.visibility = View.GONE
        }
        adapter.submitList(TaskDataSource.getList())
    }

    companion object{
        private const val CREATE_NEW_TASK = 1000
    }
}