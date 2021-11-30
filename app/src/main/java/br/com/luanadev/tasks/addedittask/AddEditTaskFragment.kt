package br.com.luanadev.tasks.addedittask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.luanadev.tasks.EventObserver
import br.com.luanadev.tasks.R
import br.com.luanadev.tasks.databinding.AddtaskFragBinding
import br.com.luanadev.tasks.task.ADD_EDIT_RESULT_OK
import br.com.luanadev.tasks.util.setupRefreshLayout
import br.com.luanadev.tasks.util.setupSnackbar
import com.google.android.material.snackbar.Snackbar

class AddEditTaskFragment : Fragment() {

    private lateinit var viewDataBinding: AddtaskFragBinding

    private val args: AddEditTaskFragmentArgs by navArgs()

    private val viewModel by viewModels<AddEditTaskViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.addtask_frag, container, false)
        viewDataBinding = AddtaskFragBinding.bind(root).apply {
            this.viewmodel = viewModel
        }
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupSnackbar()
        setupNavigation()
        this.setupRefreshLayout(viewDataBinding.refreshLayout)
        viewModel.start(args.taskId)
    }

    private fun setupSnackbar() {
        view?.setupSnackbar(this, viewModel.snackbarText, Snackbar.LENGTH_SHORT)
    }

    private fun setupNavigation() {
        viewModel.taskUpdatedEvent.observe(viewLifecycleOwner, EventObserver {
            val action = AddEditTaskFragmentDirections
                .actionAddEditTaskFragmentToTasksFragment(ADD_EDIT_RESULT_OK)
            findNavController().navigate(action)
        })
    }
}