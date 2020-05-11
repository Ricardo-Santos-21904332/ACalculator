package com.example.acalculator.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.acalculator.ui.viewmodels.HistoryViewModel
import com.example.acalculator.ui.listeners.OnListChanged
import com.example.acalculator.entities.Operation
import com.example.acalculator.R
import com.example.acalculator.ui.adapters.HistoryAdapter
import kotlinx.android.synthetic.main.fragment_history.list_historic


class HistoryFragment : Fragment(),
    OnListChanged, HistoryAdapter.OnOperationClickedListener {
    private lateinit var viewModel: HistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        viewModel = ViewModelProviders.of(this).get(HistoryViewModel::class.java)
        return view
    }

    override fun onStart() {
        viewModel.registerListListener(this)
        list_historic.layoutManager = LinearLayoutManager(activity as Context)
        list_historic.adapter =
            HistoryAdapter(
                activity as Context,
                R.layout.item_expression,
                viewModel.lista, this
            )
        super.onStart()
    }

    override fun onListChanged(list: MutableList<Operation>?) {
        list?.let {
            list_historic.adapter =
                HistoryAdapter(
                    activity as Context,
                    R.layout.item_expression,
                    it, this
                )
        }
    }

    override fun onDestroy() {
        viewModel.unregisterListListener()
        super.onDestroy()
    }

    override fun onOperationCLicked(posicao: Int) {
        viewModel.removerLista(posicao)
        Toast.makeText(activity as Context, "Operação removida!", Toast.LENGTH_SHORT).show()
    }
}
