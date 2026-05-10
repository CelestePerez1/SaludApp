package pa.ac.utp.miprimeraapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CardPeso : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_peso)

        val titulo = findViewById<TextView>(R.id.titulo)
        val valor1 = findViewById<EditText>(R.id.valor1)
        val valor2 = findViewById<EditText>(R.id.valor2)
        val boton = findViewById<Button>(R.id.btSumar)
        val result = findViewById<TextView>(R.id.TxtResultado)

        titulo.text = "Ejercicio de Prueba"

        boton.setOnClickListener {
            //Convertir edittext a string
            val valor1txt = valor1.text.toString()
            val valor2txt = valor2.text.toString()

            if(valor1txt.isNotEmpty() && valor2txt.isNotEmpty()){
                val valor1num = valor1txt.toInt()
                val valor2num = valor2txt.toInt()
                val suma = valor1num+valor2num
                result.text = suma.toString()
            }
        }




    }
}