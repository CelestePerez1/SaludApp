package pa.ac.utp.miprimeraapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class Menu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val cardPeso = findViewById<CardView>(R.id.cardPeso)
        val cardPresi = findViewById<CardView>(R.id.cardPresion)
        val cardGlu = findViewById<CardView>(R.id.cardGlucosa)
        val cardAct = findViewById<CardView>(R.id.cardActividad)
        val cardHidrat = findViewById<CardView>(R.id.cardHidra)
        val cardMet = findViewById<CardView>(R.id.cardMedica)

        cardPeso.setOnClickListener {
            Toast.makeText(this, "Accediendo a Peso e IMC", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, ControlPeso::class.java)
            startActivity(intent)
        }

        cardPresi.setOnClickListener {
            Toast.makeText(this, "Accediendo a Presión Aterial e historial", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ControlPresion::class.java)
            startActivity(intent)
        }

        cardGlu.setOnClickListener {
            Toast.makeText(this, "Accediendo a Control de Glucosa", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ControlGlucosa::class.java)
            startActivity(intent)
        }

        cardAct.setOnClickListener {
            Toast.makeText(this, "Accediendo a Control Fisico", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ControlActiv::class.java)
            startActivity(intent)
        }

        cardHidrat.setOnClickListener {
            Toast.makeText(this, "Accediendo a Control de hidratacion", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ControlHidra::class.java)
            startActivity(intent)
        }

        cardMet.setOnClickListener {
            Toast.makeText(this, "Accediendo a Control Medicamento", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ControlMedi::class.java)
            startActivity(intent)
        }


    }
}