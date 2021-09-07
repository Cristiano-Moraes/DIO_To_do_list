package edu.moraes.mobile.todolist.ui

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import edu.moraes.mobile.todolist.datasource.TaskDataSource
import edu.moraes.mobile.todolist.extensions.text
import edu.moraes.mobile.todolist.extensions.format
import edu.moraes.mobile.todolist.model.Task
import todolist.databinding.ActivityAddTaskBinding
import java.util.*

class AddTask : AppCompatActivity() {
    private lateinit var addTaskBinding: ActivityAddTaskBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addTaskBinding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(addTaskBinding.root)
        if (intent.hasExtra(TASK_ID)) {
            val taskId = intent.getIntExtra(TASK_ID,0)
            TaskDataSource.findById(taskId)?.let{
                addTaskBinding.txtfdTitulo.text = it.titulo
                addTaskBinding.txtfdData.text = it.data
                addTaskBinding.txtfdHora.text = it.hora
            }

        }
        insertListeners()
    }
    private fun insertListeners(){
        addTaskBinding.txtfdData.editText?.setOnClickListener{
           val datePicker =  MaterialDatePicker.Builder.datePicker().build()
            datePicker.addOnPositiveButtonClickListener {
                val timeZone = TimeZone.getDefault()
                val offset = timeZone.getOffset(Date().time) * -1
                addTaskBinding.txtfdData.text = Date(it + offset).format()
            }
            datePicker.show(supportFragmentManager,"DATE_PICKER_TAG_1")
        }
        addTaskBinding.txtfdHora.editText?.setOnClickListener{
            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build()
            timePicker.addOnPositiveButtonClickListener{
                val minute = if (timePicker.minute in 0..9 ) "0${timePicker.minute}" else timePicker.minute
                val hour = if (timePicker.hour in 0..9 ) "0${timePicker.hour}" else timePicker.hour
                addTaskBinding.txtfdHora.text = "$hour:$minute"
            }
            timePicker.show(supportFragmentManager,null)
        }
        addTaskBinding.btnCriarTarefa.setOnClickListener{
            val t = Task(
                titulo = addTaskBinding.txtfdTitulo.text,
                data = addTaskBinding.txtfdData.text,
                hora = addTaskBinding.txtfdHora.text,
                id = intent.getIntExtra(TASK_ID,0)
            )
            TaskDataSource.insertTask(t)
            setResult(Activity.RESULT_OK)
            finish()
        }
        addTaskBinding.btnCancelar.setOnClickListener{
            finish()
        }//
    }
    companion object {
        const val TASK_ID = "task_id"
    }
}