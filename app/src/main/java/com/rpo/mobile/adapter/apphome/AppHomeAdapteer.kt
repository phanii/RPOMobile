package com.rpo.mobile.adapter.apphome

import android.graphics.drawable.GradientDrawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rpo.mobile.R
import com.rpo.mobile.modal.apphome.AppHomeRowBean
import com.rpo.mobile.modal.apphome.PosStatus
import kotlinx.android.synthetic.main.apphome_rcview_row.view.*

class AppHomeAdapteer(var poslist: ArrayList<AppHomeRowBean>) : RecyclerView.Adapter<AppHomeAdapteer.AppHomeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppHomeViewHolder {

        return AppHomeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.apphome_rcview_row, parent, false))
    }

    override fun getItemCount(): Int {
        return poslist.size
    }

    override fun onBindViewHolder(holder: AppHomeViewHolder, position: Int) {
        val posrow = poslist[position]
        holder.onBindRow(posrow)
    }

    class AppHomeViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun onBindRow(appHomeRowBean: AppHomeRowBean) {
            itemView.apphome_row_pos_name.text = appHomeRowBean.posname
            var background = itemView.apphome_row_pos_status.background
            val gradientDrawable = background as GradientDrawable
            when (appHomeRowBean.posstatus) {
                PosStatus.ACTIVE -> {
                    gradientDrawable.setColor(ContextCompat.getColor(itemView.context, R.color.colorPrimary))
                }
                PosStatus.INACTIVE -> {
                    gradientDrawable.setColor(ContextCompat.getColor(itemView.context, R.color.yellow))
                }
                PosStatus.LOCKED -> {
                    gradientDrawable.setColor(ContextCompat.getColor(itemView.context, R.color.yellow_light))
                }
                PosStatus.OFFLINE -> {
                    gradientDrawable.setColor(ContextCompat.getColor(itemView.context, R.color.red)); }
            }
            itemView.apphome_row_pos_status.background = background
            itemView.apphome_row_pos_total.text = """${"$ "}${appHomeRowBean.postotal.toString()}"""
            if (appHomeRowBean.posnotificationcount!! > 0) {
                itemView.apphome_row_notification_count.text = appHomeRowBean.posnotificationcount.toString()
                itemView.apphome_row_notification_count.visibility = View.VISIBLE
            }


        }
    }
}