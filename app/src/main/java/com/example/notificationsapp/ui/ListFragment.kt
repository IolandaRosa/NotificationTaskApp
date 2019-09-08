package com.example.notificationsapp.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notificationsapp.R
import com.example.notificationsapp.adapter.ListFragmentAdapter
import com.example.notificationsapp.handlers.SwipeDeleteCallback
import com.example.notificationsapp.model.Task
import com.example.notificationsapp.view_model.TaskViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListFragment : Fragment() {

    private lateinit var recyclerViewTaskList: RecyclerView
    private var listener: OnFragmentListTaskAddListener? = null
    private lateinit var floatingButtonAdd: FloatingActionButton
    private lateinit var taskList: MutableList<Task>
    private lateinit var recyclerViewTaskListAdapter: ListFragmentAdapter
    private lateinit var taskViewModel:TaskViewModel

    companion object {

        private const val ARGS_VIEW_MODEL="tasks_view_model"

        fun newInstance(tasksViewModel: TaskViewModel):ListFragment{

            val fragment = ListFragment()

            val args = Bundle().apply {
                putSerializable(ARGS_VIEW_MODEL,tasksViewModel)
            }

            fragment.arguments = args

            return fragment
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(arguments!=null){
            taskViewModel = arguments!!.getSerializable(ARGS_VIEW_MODEL) as TaskViewModel
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        val view = inflater.inflate(R.layout.fragment_list, container, false)
        recyclerViewTaskList = view.findViewById(R.id.taskListRecyclerView)
        floatingButtonAdd = view.findViewById(R.id.floatingActionButtonAdd)

        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupFragmentList()
        setupOnClickButtonListener()
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnFragmentListTaskAddListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentListTaskAddListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun setupFragmentList() {
        recyclerViewTaskList.setHasFixedSize(true)

        recyclerViewTaskList.layoutManager = LinearLayoutManager(activity)

        recyclerViewTaskListAdapter =
            ListFragmentAdapter(activity?.applicationContext, taskList, taskViewModel)

        recyclerViewTaskList.adapter = recyclerViewTaskListAdapter

        //adicionar o item touch helper à recycler view
        val itemTouchHelper = ItemTouchHelper(SwipeDeleteCallback(recyclerViewTaskListAdapter))

        itemTouchHelper.attachToRecyclerView(recyclerViewTaskList)

    }

    fun updateAdapter() {
        recyclerViewTaskListAdapter.setTasks(taskList)
    }

    private fun setupOnClickButtonListener() {

        floatingButtonAdd.setOnClickListener {

            listener?.onClickAddButton()
        }
    }

    fun resetTaskList(tasks: MutableList<Task>) {
        taskList = tasks
    }

    interface OnFragmentListTaskAddListener {
        fun onClickAddButton()
    }
}
