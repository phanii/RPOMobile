package com.rpo.mobile.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rpo.mobile.modal.EventNotifyPosition
import com.rpo.mobile.R
import com.rpo.mobile.modal.BarcodeBean
import kotlinx.android.synthetic.main.barcode_row_data.view.*
import org.greenrobot.eventbus.EventBus
import java.util.*

class BarcodeAdapter(private val barcodeList: ArrayList<BarcodeBean>?) : RecyclerView.Adapter<BarcodeAdapter.ViewHolder>() {
    companion object {
        val TAG: String = "BarcodeAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.barcode_row_data, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return barcodeList?.size!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bbean = barcodeList?.get(position)
        if (bbean != null) {
            holder.bindView(bbean)
        }
        holder.itemView.img_delete.setOnClickListener {
            barcodeList?.remove(bbean)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, barcodeList!!.size)
            EventBus.getDefault().postSticky(EventNotifyPosition(bbean!!))
        }

    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun bindView(barcodeBean: BarcodeBean) {
            itemView.barcode.text = barcodeBean.barcode
            itemView.quantity.text = barcodeBean.quantity.toString()


        }
    }
}