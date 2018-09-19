package com.rpo.mobile.barcodepreview


import android.graphics.Bitmap
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.gson.Gson
import com.rpo.mobile.EventJustbarcode
import com.rpo.mobile.R
import kotlinx.android.synthetic.main.activity_main_fab.*
import kotlinx.android.synthetic.main.layout_qr_code_reader.*
import org.greenrobot.eventbus.EventBus


class BarCodeReaderActivity : BaseCameraActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBottomSheet(R.layout.layout_qr_code_reader)

    }

    override fun onClick(v: View) {
        fabProgressCircle.show()
        cameraView.captureImage { cameraKitImage ->
            // Get the Bitmap from the captured shot
            getQRCodeDetails(cameraKitImage.bitmap)
            runOnUiThread {
                showPreview()
                imagePreview.setImageBitmap(cameraKitImage.bitmap)
            }
        }
    }

    private fun getQRCodeDetails(bitmap: Bitmap) {
        val options = FirebaseVisionBarcodeDetectorOptions.Builder()
                .setBarcodeFormats(
                        FirebaseVisionBarcode.FORMAT_EAN_13)// here we are going to configure the barcode
                .build()
        val detector = FirebaseVision.getInstance().getVisionBarcodeDetector(options)
        val image = FirebaseVisionImage.fromBitmap(bitmap)
        detector.detectInImage(image)
                .addOnSuccessListener {
                    Log.d("detector", ": success ${Gson().toJson(it)}")
                    for (firebaseBarcode in it) {

                        codeData.text = firebaseBarcode.displayValue //Display contents inside the barcode
                        Log.d("codeData", ":${codeData.text} ")
                        // if (codeData.text.length == 25) {
                        EventBus.getDefault().post(EventJustbarcode(codeData.text.toString()))
                        // }


                        when (firebaseBarcode.valueType) {
                            //Handle the URL here
                            FirebaseVisionBarcode.TYPE_URL -> firebaseBarcode.url
                            // Handle the contact info here, i.e. address, name, phone, etc.
                            FirebaseVisionBarcode.TYPE_CONTACT_INFO -> firebaseBarcode.contactInfo
                            // Handle the wifi here, i.e. firebaseBarcode.wifi.ssid, etc.
                            FirebaseVisionBarcode.TYPE_WIFI -> firebaseBarcode.wifi
                            //Handle more type of Barcodes
                        }

                    }

                }
                .addOnFailureListener {
                    Log.d("detector", ": fail")
                    it.printStackTrace()
                    Log.d("BCR", ":error ")
                    Toast.makeText(baseContext, "Sorry, something went wrong!", Toast.LENGTH_SHORT).show()
                }
                .addOnCompleteListener {
                    fabProgressCircle.hide()
                    sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                }


        finish()
    }

}
