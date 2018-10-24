package com.rpo.mobile.apphome

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import com.rpo.mobile.R
import com.rpo.mobile.adapter.apphome.AppHomeAdapteer
import com.rpo.mobile.modal.apphome.AppHomeRowBean
import com.rpo.mobile.modal.apphome.PosStatus
import kotlinx.android.synthetic.main.activity_app_home.*


class AppHome : AppCompatActivity() {
    var poslist: ArrayList<AppHomeRowBean>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_home)
        poslist = arrayListOf()
        poslist!!.add(AppHomeRowBean("Pos1", PosStatus.OFFLINE, 123, 1))
        poslist!!.add(AppHomeRowBean("Pos2", PosStatus.INACTIVE, 345, 0))
        poslist!!.add(AppHomeRowBean("Pos3", PosStatus.ACTIVE, 6789, 5))
        poslist!!.add(AppHomeRowBean("Pos4", PosStatus.OFFLINE, 9875, 7))
        poslist!!.add(AppHomeRowBean("Pos5", PosStatus.ACTIVE, 678, 0))
        poslist!!.add(AppHomeRowBean("Pos6", PosStatus.OFFLINE, 5643, 0))
        poslist!!.add(AppHomeRowBean("Pos7", PosStatus.LOCKED, 4565, 10))
        poslist!!.add(AppHomeRowBean("Pos8", PosStatus.ACTIVE, 465, 1))
        /**
         * vertical
         */
        //apphome_rcview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        /**
         * grid
         */
        apphome_rcview.layoutManager = GridLayoutManager(this, 2)
        apphome_rcview.hasFixedSize()
        apphome_rcview.adapter = AppHomeAdapteer(poslist!!)

        val background = apphome_static_view.background as ColorDrawable
        //        //   background.color = ContextCompat.getColor(this, R.color.blue)

        apphome_devicecount.text = "${poslist!!.size} Devices"

    }
}
