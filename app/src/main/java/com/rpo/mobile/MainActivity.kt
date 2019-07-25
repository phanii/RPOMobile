package com.rpo.mobile

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import com.pixplicity.easyprefs.library.Prefs
import com.rpo.mobile.adapter.BarcodeAdapter
import com.rpo.mobile.barcodepreview.BarCodeReaderActivity
import com.rpo.mobile.modal.BarcodeBean
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    companion object {
        val TAG: String = javaClass.simpleName
    }


    var count: Int? = 0
    var count_: Int? = 0
    val afterUniqueset = ArrayList<BarcodeBean>()
    private var barcodeBeanList: ArrayList<BarcodeBean>? = null
    private var adapter: BarcodeAdapter? = null
    private var uniqueSet: Set<BarcodeBean>? = null
    private lateinit var barcodehashmap: HashMap<String, BarcodeBean>
    var total_ticketvalue: MutableLiveData<Int>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        barcodeBeanList = ArrayList()
        barcodeBeanList!!.clear()
        barcodehashmap = hashMapOf()
        uniqueSet = HashSet(barcodeBeanList)
        total_ticketvalue = MutableLiveData()
        loadData()
        addButton.setOnClickListener {


            if (isNullOrEmpty(barcodeEntry.text.toString())) {
                addtoList(barcodeEntry.text.toString())
            } else {
                Toast.makeText(this, "Enter Barcode number", Toast.LENGTH_SHORT).show()
            }
        }
        nextbutton.setOnClickListener {
            if (afterUniqueset.size > 0) {
                gotoNextActivity()
            } else {
                Toast.makeText(this, "List not Empaty", Toast.LENGTH_SHORT).show()
            }
        }
        barcodebutton.setOnClickListener { openScanner() }
        total_ticketvalue!!.observe(this, android.arch.lifecycle.Observer {

            total_value.text = it.toString()

        })

        barcodeEntry.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (isNullOrEmpty(barcodeEntry.text.toString())) {
                        addtoList(barcodeEntry.text.toString())

                    } else {
                        Toast.makeText(this@MainActivity, "Enter Barcode number", Toast.LENGTH_SHORT).show()
                    }
                    closeKeyboard()
                    return true
                }
                return false
            }

        })

    }

    private fun openScanner() {
        startActivity(Intent(this, BarCodeReaderActivity::class.java))
/*
        val intent = Intent(this@MainActivity, BarCodeReaderActivity::class.java)
        startActivityForResult(intent, 2)*/

    }

    private fun gotoNextActivity() {
        count = Prefs.getInt("prefsCount", 0) + 1
        Prefs.putInt("prefsCount", count!!)
        val filename: String = "Count ${Prefs.getInt("prefsCount", 1)}"
        var totalQuantity: Int? = 0
        val barcodetotal: Int = afterUniqueset.size

        Log.d(TAG, "NextActitiy : ${afterUniqueset.size}")
        for (temp in afterUniqueset) {
            if (totalQuantity != null) {
                totalQuantity += temp.quantity
            } else {
                totalQuantity = temp.quantity
            }
        }
        Log.d(TAG, "NextActitiy Total  : $totalQuantity")
        val intent = Intent(this, NextActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelableArrayList("mylist", afterUniqueset)
        bundle.putInt("totalbarcodes", barcodetotal)
        bundle.putInt("totalscannedquantity", totalQuantity!!)

        bundle.putString("filename", filename)

        intent.putExtras(bundle)
        this.startActivity(intent)

    }

    private fun addtoList(barcodenumber: String) {
//        fabProgressCircle.show()
        afterUniqueset.clear()
        val price = Random.nextInt(100)

        barcodeBeanList?.add(BarcodeBean(barcodenumber, price))

        count_ = count_!!.plus(price)
        total_ticketvalue?.value = count_


        val uniqueSet = HashSet<BarcodeBean>(barcodeBeanList)
        adapter?.notifyDataSetChanged()
        barcodeEntry.text.clear()

        for (temp in uniqueSet) {
            //  Log.d(TAG, "$temp: ---> ${Collections.frequency(barcodeBeanList, temp)}")
            afterUniqueset.add(BarcodeBean(temp.barcode, temp.quantity))
        }
        //fabProgressCircle.hide()
        //have to sort the list and remove the value from main list
        // uniqueSet.clear()
    }


    private fun loadData() {
        recyView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyView.hasFixedSize()
//        barcodeBeanList?.add(BarcodeBean(barcodeEntry.text.toString().toInt(),1))
        adapter = BarcodeAdapter(afterUniqueset)
        recyView.scrollToPosition(afterUniqueset.size)
        recyView.adapter = adapter

    }

    fun isNullOrEmpty(str: String?): Boolean {
        if (str != null && !str.isEmpty())
            return true
        return false
    }

    override fun onStart() {
        super.onStart()
        //FirebaseApp.initializeApp(this)
        try {
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this)
            } else {
                EventBus.getDefault().register(this)
            }
        } catch (e: Exception) {
            Log.d(TAG, "Already Registered: ${e.localizedMessage}")
        }
    }

    /*   override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
           super.onActivityResult(requestCode, resultCode, data)
           if (requestCode == 2) {
               val message = data?.getStringExtra("MESSAGE")
               Log.d(TAG, "message : $message");
              // addtoList(message!!)
               //textView1.setText(message)
           }

       }*/

    override fun onDestroy() {
        super.onDestroy()

        EventBus.getDefault().unregister(this)

    }
//subscriber get the value from adatpter and process the things

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onReceiveEventNotifyPosition(eventNotifyPosition: EventNotifyPosition) {

        removeFromList(eventNotifyPosition.bb)
        Log.d(TAG, "eventNotifyPosition : ${Gson().toJson(eventNotifyPosition.bb)}")

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onReceiveScannedBarcode(eventJustbarcode: EventJustbarcode) {
        Log.d(TAG, "Scanned Barcode : ${eventJustbarcode.barcodenumber}")
        addtoList(eventJustbarcode.barcodenumber)
    }

    //remove the item from existing list
    private fun removeFromList(bb: BarcodeBean) {
        val posToRemove = bb.barcode
        val posPrice: Int = bb.quantity


        val it = barcodeBeanList?.iterator()
        if (it != null) {
            while (it.hasNext()) {
                val bbean: BarcodeBean = it.next()
                if (bbean.barcode == posToRemove) {
                    it.remove()

                }


            }

        }

        count_ = count_!!.minus(posPrice)
        total_ticketvalue?.value = count_
        println("updated price ${total_ticketvalue?.value.toString()}")


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onReceiveEventClose(eventCloseData: EventCloseData) {
        if (eventCloseData.information.equals("closedata", ignoreCase = true)) {
            try {
                barcodeBeanList?.clear()
                afterUniqueset.clear()
            } catch (e: Exception) {
                Log.d(TAG, ": Unable to clear the list")
            }
            Toast.makeText(this, "File was saved", Toast.LENGTH_LONG).show()
            finish()
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    fun closeKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

}
