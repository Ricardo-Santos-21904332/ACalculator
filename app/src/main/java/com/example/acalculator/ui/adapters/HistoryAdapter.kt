package com.example.acalculator.ui.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.acalculator.entities.Operation
import com.example.acalculator.R
import kotlinx.android.synthetic.main.item_expression.view.*


class HistoryAdapter(
    private val context: Context,
    private val layout: Int,
    private val items: MutableList<Operation>,
    var clickListener: OnOperationClickedListener
) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(
            LayoutInflater.from(context).inflate(layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.init(items.get(position), clickListener)
    }

    override fun getItemCount() = items.size


    class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val logo: Unit = itemView.logo.setImageResource(R.drawable.logo)
        val expression: TextView = itemView.text_expression
        val result: TextView = itemView.text_result

        fun init(operation: Operation, action: OnOperationClickedListener) {
            expression.text = operation.expression
            result.text = operation.result.toString()
            itemView.setOnLongClickListener {
                action.onOperationCLicked(adapterPosition)
                true
            }
        }

    }

    interface OnOperationClickedListener {
        fun onOperationCLicked(posicao: Int)
    }
}