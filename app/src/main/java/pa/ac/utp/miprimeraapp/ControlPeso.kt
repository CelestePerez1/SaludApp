package pa.ac.utp.miprimeraapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.Toast
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
class ControlPeso : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_control_peso)

        val etEdad = findViewById<EditText>(R.id.etEdad)
        val etPeso = findViewById<EditText>(R.id.etPeso)
        val etEstatura = findViewById<EditText>(R.id.etEstatura)
        val swPeso = findViewById<SwitchCompat>(R.id.swPesoUnit)
        val swEstatura = findViewById<SwitchCompat>(R.id.swEstaturaUnit)
        val btnCalcular = findViewById<Button>(R.id.btnCalcular)
        val tvIMC = findViewById<TextView>(R.id.tvIMC)
        val tvPesoIdeal = findViewById<TextView>(R.id.tvPesoIdeal)
        val tvGrasa = findViewById<TextView>(R.id.tvGrasa)
        val tvClasificacion = findViewById<TextView>(R.id.tvClasificacion)

        // Listeners para cambiar los Hints dinámicamente
        swPeso.setOnCheckedChangeListener { _, isChecked ->
            etPeso.hint = if (isChecked) "Peso (Lb)" else "Peso (Kg)"
            etPeso.text.clear()
        }
        swEstatura.setOnCheckedChangeListener { _, isChecked ->
            etEstatura.hint = if (isChecked) "Estatura (in)" else "Estatura (cm)"
            etEstatura.text.clear()
        }

        val btnVerHistorial = findViewById<Button>(R.id.btnVerHistorial)
        btnVerHistorial.setOnClickListener {
            startActivity(Intent(this, HistorialPesoActivity::class.java))
        }

        btnCalcular.setOnClickListener {
            val sEdad = etEdad.text.toString()
            val sPeso = etPeso.text.toString()
            val sEstatura = etEstatura.text.toString()

            if (sEdad.isEmpty() || sPeso.isEmpty() || sEstatura.isEmpty()) {
                Toast.makeText(
                    this, "Completa todos los campos",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val edad = sEdad.toInt()
            var peso = sPeso.toDouble()
            var estatura = sEstatura.toDouble()

            // Normalización de Peso a KG si está en Libras
            if (swPeso.isChecked) {
                peso *= 0.453592
            }

            // Normalización de Estatura a CM si está en Pulgadas
            if (swEstatura.isChecked) {
                estatura *= 2.54
            }

            val estaturaMetros = estatura / 100
            val imc = peso / (estaturaMetros * estaturaMetros)
            val pesoIdeal = 22 * (estaturaMetros * estaturaMetros)
            val grasa = (1.20 * imc) + (0.23 * edad) - 16.2

            tvIMC.text = String.format("%.1f", imc)
            tvPesoIdeal.text = String.format("%.1f kg", pesoIdeal)
            tvGrasa.text = String.format("%.1f%%", grasa)

            tvClasificacion.text = categorizarIMC(imc)
        }
    }

    private fun categorizarIMC(imc: Double): String {
        return when {
            imc < 18.5 -> "Bajo Peso"
            imc < 25 -> "Normal"
            imc < 30 -> "Sobrepeso"
            imc < 35 -> "Obesidad I"
            imc < 40 -> "Obesidad II"
            else -> "Obesidad III"
        }
    }
}