package fr.gobelins.dmi1

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ComputeActivity : AppCompatActivity() {
    private  lateinit var firstOperand : EditText
    private  lateinit var secondOperand : EditText
    private  lateinit var result: TextView
    private  lateinit var sumOperand: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.compute_activity)

        // Initialiser les vues
        firstOperand = findViewById(R.id.first_operand)
        secondOperand = findViewById(R.id.second_operand)
        result = findViewById(R.id.txtResult)
        sumOperand = findViewById(R.id.btn_compute)

        sumOperand.setOnClickListener { Add() }

        val operation = intent.getStringExtra("operation") ?: "ADD"
    }

    @SuppressLint("SetTextI18n")
    private fun Add(){
        // Récupérer les valeurs des EditText au moment du clic
        val firstValue = firstOperand.text.toString()
        val secondValue = secondOperand.text.toString()

        // Convertir les valeurs en Int
        val firstNumber = firstValue.toDoubleOrNull()?:return run { result.text = "Erreur" }
        val secondNumber = secondValue.toDoubleOrNull()?: return run{result.text = "Erreur"}

        // Effectuer l'addition
        val resultOperand = firstNumber.plus(secondNumber)

        // Afficher le résultat
        result.text = "Résultat: $resultOperand"
    }
}