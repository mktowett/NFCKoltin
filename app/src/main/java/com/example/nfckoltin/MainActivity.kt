package com.example.nfckoltin

import android.app.PendingIntent
import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import android.os.Parcelable
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
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        //If no NfcAdapter, display that the device has no NFC
        if (nfcAdapter == null) {
            Log.d("noNFCCap","NO NFC Capabilities")
            Toast.makeText(
                this, "NO NFC Capabilities",
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }
        //Create a PendingIntent object so the Android system can
        //populate it with the details of the tag when it is scanned.
        pendingIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, this.javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
            PendingIntent.FLAG_IMMUTABLE
        )
    }

    override fun onResume() {
        super.onResume()
        assert(nfcAdapter != null)
        nfcAdapter!!.enableForegroundDispatch(this, pendingIntent, null, null)
    }

    override fun onPause() {
        super.onPause()
        if (nfcAdapter != null) {
            nfcAdapter!!.disableForegroundDispatch(this);
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        if (intent != null) {
            resolveIntent(intent)
        }
    }

    private fun resolveIntent(intent: Intent) {
        val action = intent.action

        if (NfcAdapter.ACTION_TAG_DISCOVERED == action
            || NfcAdapter.ACTION_TECH_DISCOVERED == action
            || NfcAdapter.ACTION_NDEF_DISCOVERED == action
        ) {
            val tag: Tag = (intent.getParcelableExtra<Parcelable>(NfcAdapter.EXTRA_TAG) as Tag?)!!
            Log.d("onTAG","onTag $tag")
        }

    }

    private fun readFromIntent(intent: Intent) {
        val action = intent.action
        if (NfcAdapter.ACTION_TAG_DISCOVERED == action || NfcAdapter.ACTION_TECH_DISCOVERED == action || NfcAdapter.ACTION_NDEF_DISCOVERED == action) {
            val myTag = intent.getParcelableExtra<Parcelable>(NfcAdapter.EXTRA_TAG) as Tag?
            val rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
            var msgs = mutableListOf<NdefMessage>()
            if (rawMsgs != null) {
                for (i in rawMsgs.indices) {
                    msgs.add(i, rawMsgs[i] as NdefMessage)
                }
               Log.d("onReadTag",msgs.toTypedArray().toString())
            }
        }
    }
}