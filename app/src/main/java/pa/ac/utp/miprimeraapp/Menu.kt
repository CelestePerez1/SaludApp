package pa.ac.utp.miprimeraapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.card.MaterialCardView

class Menu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_menu)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

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