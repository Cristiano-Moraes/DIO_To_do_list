package edu.moraes.mobile.todolist.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.moraes.mobile.todolist.model.Task
import todolist.R
import todolist.databinding.ItemTaskBinding

class TaskListAdapter : ListAdapter<Task,TaskListAdapter.TaskViewHolder>(DiffCallBack()) {
    var listenerEdit : ( Task ) -> Unit = {}
    var listenerResume : ( Task ) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTaskBinding.inflate(inflater,parent,false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    inner class TaskViewHolder ( private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: Task){
            binding.itemTvTitle.text = item.titulo
            binding.itemData.text = "${item.data} ${item.hora}"
            binding.menu.setOnClickListener{
                showPopUp(item)
            }
        }
        private fun showPopUp( item : Task ){
            val imageMenu = binding.menu
            val popUpMenu = PopupMenu(imageMenu.context,imageMenu)
            popUpMenu.menuInflater.inflate(R.menu.popupmenu_more,popUpMenu.menu)
            popUpMenu.setOnMenuItemClickListener {
                when( it.itemId ){
                    R.id.edit_item -> listenerEdit(item)
                    R.id.resume_item -> listenerResume(item)

                }
                return@setOnMenuItemClickListener true
            }
            popUpMenu.show()
        }

    }
}
class DiffCallBack : DiffUtil.ItemCallback<Task>(){
    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem

    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.id == newItem.id
    }

}