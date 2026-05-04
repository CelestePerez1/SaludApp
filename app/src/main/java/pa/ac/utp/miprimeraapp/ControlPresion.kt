package pa.ac.utp.miprimeraapp

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Calendar

class ControlPresion : AppCompatActivity() {

    private lateinit var btnFecha: Button
    private lateinit var txtFecha: TextView
    private lateinit var timePicker: TimePicker
    private lateinit var npSistolica: NumberPicker
    private lateinit var npDiastolica: NumberPicker
    private lateinit var npPulso: NumberPicker
    private lateinit var rgBrazo: RadioGroup
    private lateinit var btnAnalizar: Button
    private lateinit var txtResultado: TextView
    private lateinit var layoutClasificacion: android.view.View
    private lateinit var txtClasificacion: TextView
    private lateinit var layoutConsejo: android.view.View
    private lateinit var txtConsejo: TextView

    private var fechaSeleccionada: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_control_presion)

        btnFecha           = findViewById(R.id.btnFecha)
        txtFecha           = findViewById(R.id.txtFecha)
        timePicker         = findViewById(R.id.timePicker)
        npSistolica        = findViewById(R.id.npSistolica)
        npDiastolica       = findViewById(R.id.npDiastolica)
        npPulso            = findViewById(R.id.npPulso)
        rgBrazo            = findViewById(R.id.rgBrazo)
        btnAnalizar        = findViewById(R.id.btnAnalizar)
        txtResultado       = findViewById(R.id.txtResultado)
        layoutClasificacion = findViewById(R.id.layoutClasificacion)
        txtClasificacion   = findViewById(R.id.txtClasificacion)
        layoutConsejo      = findViewById(R.id.layoutConsejo)
        txtConsejo         = findViewById(R.id.txtConsejo)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configurar TimePicker en 24h
        timePicker.post {
            timePicker.setIs24HourView(true)
            colorearNumberPicker(timePicker)
            deshabilitarEdicion(timePicker)
        }

        // Configurar rangos de NumberPickers
        npSistolica.minValue = 80
        npSistolica.maxValue = 200
        npSistolica.value    = 115
        npSistolica.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS

        npDiastolica.minValue = 40
        npDiastolica.maxValue = 130
        npDiastolica.value    = 76
        npDiastolica.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS

        npPulso.minValue = 40
        npPulso.maxValue = 180
        npPulso.value    = 68
        npPulso.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS

        npSistolica.post  { colorearNumberPicker(npSistolica)  }
        npDiastolica.post { colorearNumberPicker(npDiastolica) }
        npPulso.post      { colorearNumberPicker(npPulso)      }

        // Eventos
        btnFecha.setOnClickListener   { mostrarDatePicker() }
        btnAnalizar.setOnClickListener { analizarMedicion() }
    }

    private fun mostrarDatePicker() {
        val calendario = Calendar.getInstance()
        DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                fechaSeleccionada = "$dayOfMonth/${month + 1}/$year"
                txtFecha.text = "Fecha: $fechaSeleccionada"
            },
            calendario.get(Calendar.YEAR),
            calendario.get(Calendar.MONTH),
            calendario.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun analizarMedicion() {
        if (fechaSeleccionada == null) {
            Toast.makeText(this, "Debe seleccionar una fecha", Toast.LENGTH_SHORT).show()
            return
        }
        if (rgBrazo.checkedRadioButtonId == -1) {
            Toast.makeText(this, "Debe seleccionar el brazo de medición", Toast.LENGTH_SHORT).show()
            return
        }

        val sistolica  = npSistolica.value
        val diastolica = npDiastolica.value
        val pulso      = npPulso.value
        val hora       = String.format("%02d:%02d", timePicker.hour, timePicker.minute)
        val brazo      = findViewById<RadioButton>(rgBrazo.checkedRadioButtonId).text.toString()
        val clasificacion = clasificarPresion(sistolica, diastolica)

        txtResultado.text =
            "Fecha: $fechaSeleccionada\n" +
            "Hora: $hora\n" +
            "Presión sistólica: $sistolica mmHg\n" +
            "Presión diastólica: $diastolica mmHg\n" +
            "Pulso cardíaco: $pulso BPM\n" +
            "Brazo utilizado: $brazo"

        txtClasificacion.text = clasificacion

        val (color, consejo) = when (clasificacion) {
            "Presión baja"    -> Pair(Color.parseColor("#2196F3"),
                "Sus valores están por debajo de lo normal. Consulte a su médico si presenta mareos o fatiga.")
            "Presión normal"  -> Pair(Color.parseColor("#4CAF50"),
                "Su presión está en un rango saludable. Mantenga un estilo de vida activo.")
            "Presión elevada" -> Pair(Color.parseColor("#FF9800"),
                "Sus valores indican presión elevada. Reduzca el consumo de sal y controle el estrés.")
            else              -> Pair(Color.parseColor("#F44336"),
                "Sus valores indican hipertensión. Consulte a su médico lo antes posible.")
        }

        val badge = GradientDrawable().apply {
            setColor(color)
            cornerRadius = 20f
        }
        txtClasificacion.background = badge

        txtConsejo.text = consejo
        layoutClasificacion.visibility = android.view.View.VISIBLE
        layoutConsejo.visibility       = android.view.View.VISIBLE
    }

    private fun clasificarPresion(sistolica: Int, diastolica: Int): String {
        return when {
            sistolica < 90 || diastolica < 60                    -> "Presión baja"
            sistolica in 90..119 && diastolica in 60..79         -> "Presión normal"
            sistolica in 120..129 && diastolica < 80             -> "Presión elevada"
            else                                                  -> "Hipertensión"
        }
    }

    @SuppressLint("SoonBlockedPrivateApi")
    private fun colorearNumberPicker(view: android.view.View) {
        if (view is NumberPicker) {
            try {
                val fieldInput = NumberPicker::class.java.getDeclaredField("mInputText")
                fieldInput.isAccessible = true
                val input = fieldInput.get(view) as EditText
                input.setTextColor(Color.BLACK)
                view.invalidate()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        if (view is android.view.ViewGroup) {
            for (i in 0 until view.childCount) {
                colorearNumberPicker(view.getChildAt(i))
            }
        }
    }

    private fun deshabilitarEdicion(view: android.view.View) {
        if (view is NumberPicker) {
            view.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        }
        if (view is android.view.ViewGroup) {
            for (i in 0 until view.childCount) {
                deshabilitarEdicion(view.getChildAt(i))
            }
        }
    }
}
