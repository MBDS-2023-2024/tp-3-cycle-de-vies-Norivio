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
    private lateinit var btnPartager : Button
    private lateinit var btnItineraire : Button

    companion object {
        private const val REQUEST_CALL_PERMISSION_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnHomeCompute = findViewById(R.id.btn_home_compute)
        btnEmergencyCall = findViewById(R.id.btn_appel_urgence)
        btnRechercher = findViewById(R.id.btn_rechercher)
        btnPartager = findViewById(R.id.btn_partager)
        btnItineraire = findViewById(R.id.btn_itineraire)

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

        btnPartager.setOnClickListener {
            // Contenu à partager
            val shareContent = "Tu es l'un des meilleurs professeurs de la formation MBDS."

            // Intent de partage
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, shareContent)
                type = "text/plain"
            }

            // Afficher le sélecteur de partage
            val chooser = Intent.createChooser(shareIntent, "Partager via")
            if (shareIntent.resolveActivity(packageManager) != null) {
                startActivity(chooser)
            }
        }

        btnItineraire.setOnClickListener{
            // Coordonnées des Papeteries Gobelins
            val destination = "48.8335,2.3554" // Coordonnées des Papeteries Gobelins

            // URI pour Google Maps
            val uri = "google.navigation:q=$destination"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri)).apply {
                setPackage("com.google.android.apps.maps")
            }
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                // L'application Google Maps n'est pas installée
                val webUri = "http://maps.google.com/maps?daddr=$destination"
                val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(webUri))
                startActivity(webIntent)
            }
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