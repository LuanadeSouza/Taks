package br.com.luanadev.tasks.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import br.com.luanadev.tasks.R
import br.com.luanadev.tasks.data.Task
import br.com.luanadev.tasks.data.TaskPriority
import br.com.luanadev.tasks.databinding.TaskViewItemBinding
import java.text.SimpleDateFormat
import java.util.*

class TaskViewHolder(
    private val binding: TaskViewItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    private val dateFormat = SimpleDateFormat("MMM d, yyyy", Locale.US)

    fun bind(todo: Task) {
        binding.task.text = todo.name
        setTaskPriority(todo)
        binding.deadline.text = dateFormat.format(todo.deadline)
        // if a task was completed, show it grayed out
        val color = if (todo.completed) {
            R.color.greyAlpha
        } else {
            R.color.white
        }
        itemView.setBackgroundColor(
            ContextCompat.getColor(
                itemView.context,
                color
            )
        )
    }

    private fun setTaskPriority(todo: Task) {
        binding.priority.text = itemView.context.resources.getString(
            R.string.priority_value,
            todo.priority.name
        )
        // set the priority color based on the task priority
        val textColor = when (todo.priority) {
            TaskPriority.HIGH -> R.color.red
            TaskPriority.MEDIUM -> R.color.yellow
            TaskPriority.LOW -> R.color.green
        }
        binding.priority.setTextColor(ContextCompat.getColor(itemView.context, textColor))
    }

    companion object {
        fun create(parent: ViewGroup): TaskViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.task_view_item, parent, false)
            val binding = TaskViewItemBinding.bind(view)
            return TaskViewHolder(binding)
        }
    }
}