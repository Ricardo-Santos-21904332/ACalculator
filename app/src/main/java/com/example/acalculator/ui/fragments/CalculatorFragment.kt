package com.example.acalculator.ui.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import butterknife.ButterKnife
import butterknife.OnClick
import com.example.acalculator.*
import com.example.acalculator.domain.auth.token
import com.example.acalculator.entities.Operation
import com.example.acalculator.ui.adapters.HistoryAdapter
import com.example.acalculator.ui.listeners.OnDisplayChanged
import com.example.acalculator.ui.listeners.OnListChanged
import com.example.acalculator.ui.utils.VerificarInternet
import com.example.acalculator.ui.viewmodels.CalculatorViewModel
import kotlinx.android.synthetic.main.fragment_calculator.*
import kotlinx.android.synthetic.main.fragment_calculator.list_historic
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class CalculatorFragment : Fragment(),
    OnDisplayChanged,
    OnListChanged,
    HistoryAdapter.OnOperationClickedListener {
    private lateinit var viewModel: CalculatorViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calculator, container, false)
        viewModel = ViewModelProviders.of(this).get(CalculatorViewModel::class.java)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onStart() {
        viewModel.registerListener(this)
        viewModel.registerListListener(this)
        viewModel.removerListaLocal()
        val pref: SharedPreferences = activity!!.getSharedPreferences("save", 0)
        val editor = pref.edit()
        editor.putString("token", token)
        editor.apply()
        super.onStart()
    }

    override fun onDisplayChanged(value: String?) {
        value?.let { text_visor.text = it }
    }

    override fun onListChanged(list: MutableList<Operation>?) {
        list?.let {
            list_historic?.layoutManager = LinearLayoutManager(activity as Context)
            list_historic?.adapter =
                HistoryAdapter(
                    activity as Context,
                    R.layout.item_expression,
                    it.toMutableList(),
                    this
                )
        }
    }

    override fun onDestroy() {
        viewModel.unregisterListener()
        viewModel.unregisterListListener()
        super.onDestroy()
    }

    @OnClick(
        R.id.button_0,
        R.id.button_1,
        R.id.button_2,
        R.id.button_3,
        R.id.button_4,
        R.id.button_5,
        R.id.button_6,
        R.id.button_7,
        R.id.button_8,
        R.id.button_9,
        R.id.button_adition,
        R.id.button_apagarUltimo,
        R.id.button_clear,
        R.id.button_dividir,
        R.id.button_multiplicar,
        R.id.button_ponto,
        R.id.button_subtrair
    )
    fun onClickSymbol(view: View) {
        viewModel.onClickSymbol(view.tag.toString())
        Toast.makeText(
            activity as Context,
            "button_${view.tag} " + calculaData(),
            Toast.LENGTH_SHORT
        ).show()
    }

    @OnClick(R.id.button_equals)
    fun onClickEquals(view: View) {
        if (VerificarInternet.temInternet(activity!!)) {
            viewModel.enviarOperacoesNaoEnviadas()
            viewModel.onClickEqualsOnline()
        } else {
            viewModel.onClickEqualsOffline()
        }
        list_historic?.adapter =
            HistoryAdapter(
                activity as Context,
                R.layout.item_expression,
                viewModel.getList(),
                this
            )
        Toast.makeText(activity as Context, "button_equals " + calculaData(), Toast.LENGTH_SHORT)
            .show()
    }

    fun calculaData(): String {
        val date = Date()
        val strDateFormat = "HH:mm:ss"
        val dateFormat: DateFormat = SimpleDateFormat(strDateFormat)
        val formattedDate: String = dateFormat.format(date)
        return formattedDate
    }

    override fun onOperationCLicked(posicao: Int) {
        viewModel.removerLista()
        Toast.makeText(activity as Context, "Operaçã removida!", Toast.LENGTH_SHORT).show()
    }
}
