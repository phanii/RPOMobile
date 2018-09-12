package com.rpo.mobile.modal

import android.os.Parcel
import android.os.Parcelable

data class BarcodeBean(val barcode: String, val quantity: Int) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(barcode)
        writeInt(quantity)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<BarcodeBean> = object : Parcelable.Creator<BarcodeBean> {
            override fun createFromParcel(source: Parcel): BarcodeBean = BarcodeBean(source)
            override fun newArray(size: Int): Array<BarcodeBean?> = arrayOfNulls(size)
        }
    }
}