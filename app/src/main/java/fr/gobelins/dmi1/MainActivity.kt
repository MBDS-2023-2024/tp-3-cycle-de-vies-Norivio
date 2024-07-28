package fr.gobelins.dmi1

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.telephony.emergency.EmergencyNumber
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private lateinit var btnHomeCompute: Button
    private lateinit var btnEmergencyCall : Button
    private lateinit var btnRechercher : Button

    companion object {
        private const val REQUEST_CALL_PERMISSION_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnHomeCompute = findViewById(R.id.btn_home_compute)
        btnEmergencyCall = findViewById(R.id.btn_appel_urgence)
        btnRechercher = findViewById(R.id.btn_rechercher)

        btnHomeCompute.setOnClickListener {
            val intent = Intent(this, ComputeActivity::class.java)
            intent.extras?.putString("operation", "ADD")
            startActivity(intent)
        }

        btnEmergencyCall.setOnClickListener {
            //Initialize the phone number
            val phone_number = "tel:+50940433086"

            //Check if CALL_PHONE permission is granted
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                //Request CALL_PHONE permission
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), REQUEST_CALL_PERMISSION_CODE)
            } else {
                //Permission already granted, make the call
                makePhoneCall(phone_number)
            }
        }

        btnRechercher.setOnClickListener {
            // URL à ouvrir
            val url = "https://google.fr"
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(browserIntent)
        }
    }

    private fun makePhoneCall(phoneNumber: String){
        //Getting instance of Intent with action as ACTION_CALL
        val phoneIntent = Intent(Intent.ACTION_CALL).apply {
            data = Uri.parse(phoneNumber)
        }
        startActivity(phoneIntent)
    }
}