package com.example.acalculator


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageSwitcher
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_expression.view.*


class HistoryAdapter(
    private val context: Context,
    private val layout: Int,
    private val items: MutableList<Operation>
) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(LayoutInflater.from(context).inflate(layout, parent, false))
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.expression.text = items[position].expression
        holder.result.text = items[position].result.toString()
    }

    override fun getItemCount() = items.size

    class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val logo : Unit = view.logo.setImageResource(R.drawable.logo)
        val expression: TextView = view.text_expression
        val result: TextView = view.text_result
    }
}