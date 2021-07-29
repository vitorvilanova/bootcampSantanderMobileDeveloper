package br.com.originalsoftware.taxifone.todolist.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import br.com.originalsoftware.taxifone.todolist.databinding.ActivityMainBinding
import br.com.originalsoftware.taxifone.todolist.datasource.TaskDataSource

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy { TaskListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvTasks.adapter = adapter
        updateList()

        insertListener()
    }

    private fun insertListener(){
        binding.BotaoFab1.setOnClickListener {
            //inicia a ativity que voce criou
            //startActivity(Intent(this, AddTaskActivity::class.java))

            //inicia a atictivy com o resultado do dado inserido
            startActivityForResult(Intent(this, AddTaskActivity::class.java), CREATE_NEW_TASK)
        }
            adapter.listenerEdit = {
                val intent = Intent(this, AddTaskActivity::class.java)
                intent.putExtra(AddTaskActivity.TASK_ID, it.id)
                startActivityForResult(intent, CREATE_NEW_TASK)
            }

            adapter.listenerDelete = {
                TaskDataSource.deleteTask(it)
                updateList()
            }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CREATE_NEW_TASK && resultCode == Activity.RESULT_OK) updateList()
        }

    /* Primeira versao de update list
    private fun  updateList(){
        val list = TaskDataSource.getList()
        //binding.includeEmpty.emptyState.visibility = if (list.isEmpty()) View.VISIBLE
        //else View.GONE
        //adapter.submitList(TaskDataSource.getList())
        adapter.submitList(list)
    }*/

     //FORMA SIMPLIFICADA KOTLIN, SE TIVER TAREFA NAO EXIBE A EMPTY STATE
     private fun  updateList(){
        val list = TaskDataSource.getList()
        binding.includeEmpty.emptyState.visibility = if (list.isEmpty()) View.VISIBLE
           else View.GONE
        adapter.submitList(list)
    }

/*
    private fun  updateList(){
        val list = TaskDataSource.getList()
        if(list.isEmpty()){
            binding.includeEmpty.emptyState.visibility = View.VISIBLE
        } else {
            binding.includeEmpty.emptyState.visibility = View.GONE
        }
        adapter.submitList(list)
    }

 */

    companion object{
        private  const val  CREATE_NEW_TASK  = 1000
    }

}
