package com.example.testside.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testside.R
import com.example.testside.model.Record
import kotlinx.android.synthetic.main.layout_toilet_row.view.*

class ToiletListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mItems: MutableList<Record>? = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ToiletViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_toilet_row, parent, false)
        )

    override fun getItemCount(): Int = mItems?.size ?: 0

    fun setItem(items: List<Record>?) {
        items?.let { mItems?.addAll(it) }
        notifyDataSetChanged()

    }
    fun clear() {
        mItems?.clear()
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ToiletViewHolder) {
            holder.bind(mItems?.get(position) as Record)
        }
    }

    class ToiletViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View = v
        private var area = "arrondissement"

        fun bind(record: Record) {
            view.row_addresse.text = listOfNotNull(
                record.fields?.numero_de_voie,
                record.fields?.nom_de_voie
            ).joinToString(" ")
            if (record.fields?.arrondissement == 1){
                view.row_arrondissement.text = "${record.fields?.arrondissement} er ${area}"
            }else{
                view.row_arrondissement.text = "${record.fields?.arrondissement} ème ${area}"
            }
            view.row_opening_hours.text = record.fields?.horaires_ouverture
        }
    }
}
