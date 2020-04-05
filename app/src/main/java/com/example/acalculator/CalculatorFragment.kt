package com.example.acalculator

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import butterknife.ButterKnife
import butterknife.OnClick
import kotlinx.android.synthetic.main.fragment_calculator.*
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.reflect.Array.newInstance
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

var operacoes = mutableListOf<Operation>()

class CalculatorFragment : Fragment() {
    private val TAG = MainActivity::class.java.simpleName


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calculator, container, false)
        ButterKnife.bind(this, view)
        return view
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
        val symbol = view.tag.toString()
        button_00?.setOnClickListener {
            Log.i(TAG, "Cliquei no botão 00")
            if (text_visor.text == "0") {
                text_visor.text = "00"
            } else {
                text_visor.append("00")
            }
            Toast.makeText(activity as Context, "button_0 " + calculaData(), Toast.LENGTH_SHORT)
                .show()
        }
        button_dummie?.setOnClickListener {
            Log.i(TAG, "Cliquei no botão 2")
            if (text_visor.text == "0") {
                text_visor.text = "2"
            } else {
                text_visor.append("2")
            }
            Toast.makeText(activity as Context, "button_2 " + calculaData(), Toast.LENGTH_SHORT)
                .show()
        }
        if (symbol == "<") {
            Log.i(TAG, "Cliquei no botão <")
            if (text_visor.text.isEmpty() || text_visor.text.length == 1) {
                text_visor.text = ""
            } else {
                text_visor.text = text_visor.text.substring(0, text_visor.text.length - 1)
            }
            Toast.makeText(
                activity as Context,
                "button_apagarUltimo " + calculaData(),
                Toast.LENGTH_SHORT
            ).show()
        } else if (symbol == "C") {
            Log.i(TAG, "Cliquei no botão C")
            text_visor.text = ""
            Toast.makeText(activity as Context, "button_clear " + calculaData(), Toast.LENGTH_SHORT)
                .show()
        } else {
            Log.i(TAG, "Cliquei no botão $symbol")
            if (text_visor.text == "0") {
                text_visor.text = symbol
            } else {
                text_visor.append(symbol)
            }
            Toast.makeText(
                activity as Context,
                "button_$symbol " + calculaData(),
                Toast.LENGTH_SHORT
            ).show()
        }
        /*remover botão histórico
        button_historico?.setOnClickListener {
            onClickHistory(view)
        }
         */
    }

    @OnClick(R.id.button_equals)
    fun onClickEquals(view: View) {
        Log.i(TAG, "Cliquei no botão =")
        val expressao = text_visor.text.toString()
        val expression = ExpressionBuilder(text_visor.text.toString()).build()
        val resultado = expression.evaluate().toString()
        text_visor.text = expression.evaluate().toString()
        val operacao = Operation(expressao, resultado.toDouble())
        operacoes.add(operacao)
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            list_historic?.layoutManager = LinearLayoutManager(activity as Context)
            list_historic?.adapter =
                HistoryAdapter(activity as Context, R.layout.item_expression, operacoes)
        }
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            text_historico.text =
                ("Último cálculo: " + expressao + " = " + expression.evaluate().toString())
        }
        Log.i(TAG, "O resultado da expressão é ${text_visor.text}")
        Toast.makeText(activity as Context, "button_equals " + calculaData(), Toast.LENGTH_SHORT)
            .show()
    }
/* remover botão histórico
    fun onClickHistory(view: View) {
        button_historico?.setOnClickListener {
            startActivity(Intent(activity as Context, HistoryActivity::class.java))
        }
    }

 */


    fun calculaData(): String {
        val date = Date()
        val strDateFormat = "HH:mm:ss"
        val dateFormat: DateFormat = SimpleDateFormat(strDateFormat)
        val formattedDate: String = dateFormat.format(date)
        return formattedDate
    }
}
