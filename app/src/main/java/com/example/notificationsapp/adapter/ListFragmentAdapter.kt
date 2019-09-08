package com.example.notificationsapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notificationsapp.R
import com.example.notificationsapp.R.mipmap.ic_flow_negative_round
import com.example.notificationsapp.R.mipmap.ic_flow_positive_round
import com.example.notificationsapp.model.Task
import com.example.notificationsapp.view_model.TaskViewModel

class ListFragmentAdapter(
    var context: Context?,
    var taskList: MutableList<Task>,
    var taskViewModel: TaskViewModel
) :
    RecyclerView.Adapter<ListFragmentViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListFragmentViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_list_item, parent, false)

        return ListFragmentViewHolder(view)
    }

    override fun getItemCount() = taskList.size

    override fun onBindViewHolder(holder: ListFragmentViewHolder, position: Int) {
        holder.viewDateText.text = taskList.get(position).date
        holder.viewHourText.text = taskList.get(position).hour
        holder.viewNameText.text = taskList.get(position).name

        when (taskList.get(position).flow) {
            true -> holder.viewImageFlow.setImageResource(ic_flow_positive_round)
            else -> holder.viewImageFlow.setImageResource(ic_flow_negative_round)
        }
    }

    fun setTasks(tasks: MutableList<Task>) {
        taskList = tasks
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int) {

        taskViewModel.delete(taskList[position])
        taskList.removeAt(position)
        notifyItemRemoved(position)

        //showUndoSnackbar()
    }

    /*private fun showUndoSnackbar() {
        val view = activity.findViewById(R.id.coordinator_layout)
        val snackbar = Snackbar.make(
            view, R.string.snack_bar_text,
            Snackbar.LENGTH_LONG
        )
        snackbar.setAction(R.string.snack_bar_undo, { v -> undoDelete() })
        snackbar.show()
    }

    private fun undoDelete() {
        mListItems.add(
            mRecentlyDeletedItemPosition,
            mRecentlyDeletedItem
        )
        notifyItemInserted(mRecentlyDeletedItemPosition)
    }*/
}