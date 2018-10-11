package com.rpo.mobile

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.gson.Gson
import com.rpo.mobile.modal.Output
import com.sembozdemir.permissionskt.askPermissions
import com.sembozdemir.permissionskt.handlePermissionsResult
import kotlinx.android.synthetic.main.activity_next.*
import org.greenrobot.eventbus.EventBus
import java.io.FileOutputStream


class NextActivity : AppCompatActivity() {
    private var compoTitle: String? = null
    private var output: Output? = null

    companion object {
        val TAG: String = NextActivity::class.java.simpleName
    }

    private val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission
            .ACCESS_FINE_LOCATION, Manifest.permission.READ_CONTACTS)

    var count: Int? = 0
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next)
        val bundle = intent.extras
        val arraylist = bundle!!.getParcelableArrayList<Parcelable>("mylist")
        totallines.text = getString(R.string.totalitems) + bundle.getInt("totalbarcodes").toString()
        totalquantity.text = getString(R.string.totalquantity) + bundle.getInt("totalscannedquantity").toString()
        compoTitle = bundle.getString("filename")



        slidebutton.setOnClickListener {
            //            count = count!! + 1
            output = Output(desc.text.toString(), arraylist)

            requestNewPermissions()
        }


    }

    private fun requestNewPermissions() {
        askPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
        {
            onGranted { savefile() }
            onDenied {
                it.forEach {
                    when (it) {
                        Manifest.permission.WRITE_EXTERNAL_STORAGE -> toast("Write denied")
                        Manifest.permission.READ_EXTERNAL_STORAGE -> toast("Read denied")
                    }
                }
            }
            onShowRationale { request ->
                var permissions = ""
                request.permissions.forEach {
                    permissions += when (it) {
                        Manifest.permission.READ_EXTERNAL_STORAGE -> " Read Data"
                        Manifest.permission.WRITE_EXTERNAL_STORAGE -> "Write Data"
                        else -> {
                        }
                    }
                }
                request.retry()

            }


        }

    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        handlePermissionsResult(requestCode, permissions, grantResults)
    }

    private fun savefile() {

        val content = Gson().toJson(output)

        count = count!! + 1

        var outputStream: FileOutputStream?
        try {
            outputStream = openFileOutput(compoTitle, Context.MODE_PRIVATE)
            outputStream!!.write(content.toByteArray(Charsets.UTF_8))

            outputStream.close()
            EventBus.getDefault().post(EventCloseData("closedata"))
            finish()
            // closeTheApp()
        } catch (e: Exception) {
            toast("File not saved. Try again")
            e.printStackTrace()
        }

    }

    private fun closeTheApp() {

        toast("File was saved")
        finish()
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun toast(messsage: String) {
        Toast.makeText(this, messsage, Toast.LENGTH_LONG).show()

    }


}
