package com.example.nfckoltin

import android.app.PendingIntent
import android.content.Intent
import android.nfc.NfcAdapter
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    //Intialize attributes
    var nfcAdapter: NfcAdapter? = null
    var pendingIntent: PendingIntent? = null
    val TAG = "nfc_test"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Initialise NfcAdapter
        //Initialise NfcAdapter
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        //If no NfcAdapter, display that the device has no NFC
        //If no NfcAdapter, display that the device has no NFC
        if (nfcAdapter == null) {
            Log.d("noNFCCap","NO NFC Capabilities")
            Toast.makeText(
                this, "NO NFC Capabilities",
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }else{
            Log.d("noNFCCap","We are in business")
        }
        //Create a PendingIntent object so the Android system can
        //populate it with the details of the tag when it is scanned.
        pendingIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, this.javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
            0
        )
    }
}