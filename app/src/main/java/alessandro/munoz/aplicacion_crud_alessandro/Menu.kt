package alessandro.munoz.aplicacion_crud_alessandro

import RecyclerViewHelpers.Adaptador
import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.claseConexion
import modelo.dataClassTickets
import java.util.Calendar
import java.util.UUID

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

        val txtTitulo = findViewById<EditText>(R.id.txtTitulo)
        val txtDescripcion = findViewById<EditText>(R.id.txtDescripcion)
        val txtAutor = findViewById<EditText>(R.id.txtAutor)
        val txtTicketEmail = findViewById<EditText>(R.id.txtTicketEmail)
        val txtFechaCreacion = findViewById<EditText>(R.id.txtFechaCreacion)
        val txtFechaFinal = findViewById<EditText>(R.id.txtFechaFinal)
        val btnEnviar = findViewById<Button>(R.id.btnEnviar)
        val rcvTickets = findViewById<RecyclerView>(R.id.rcvTickets)

        txtFechaCreacion.setOnClickListener {
            val calendario = Calendar.getInstance()
            val anio = calendario.get(Calendar.YEAR)
            val mes = calendario.get(Calendar.MONTH)
            val dia = calendario.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                this,
                { view, anioSeleccionado, mesSeleccionado, diaSeleccionado ->
                    val fechaSeleccionada =
                        "$diaSeleccionado/${mesSeleccionado + 1}/$anioSeleccionado"
                    txtFechaCreacion.setText(fechaSeleccionada)
                },
                anio, mes, dia
            )
            datePickerDialog.show()
        }

        txtFechaFinal.setOnClickListener {
            val calendario = Calendar.getInstance()
            val anio = calendario.get(Calendar.YEAR)
            val mes = calendario.get(Calendar.MONTH)
            val dia = calendario.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                this,
                { view, anioSeleccionado, mesSeleccionado, diaSeleccionado ->
                    val fechaSeleccionada =
                        "$diaSeleccionado/${mesSeleccionado + 1}/$anioSeleccionado"
                    txtFechaFinal.setText(fechaSeleccionada)
                },
                anio, mes, dia
            )
            datePickerDialog.show()
        }


        btnEnviar.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                //obj de clase conexion
                val objConexion = claseConexion().cadenaConexion()

                //variable que contenga prepare statement
                val ticketNuevo = objConexion?.prepareStatement("insert into Tickets (numeroTicket, tituloTIcket, descripcionTicket, autor, emailAutor, fechaCreacion, estadoTicket, fechaFin) values (?, ?, ?, ?, ?, ?, ?, ?)")!!
                ticketNuevo.setString(1, UUID.randomUUID().toString())
                ticketNuevo.setString(2, txtTitulo.text.toString())
                ticketNuevo.setString(3, txtDescripcion.text.toString())
                ticketNuevo.setString(4, txtAutor.text.toString())
                ticketNuevo.setString(5, txtTicketEmail.text.toString())
                ticketNuevo.setString(6, txtFechaCreacion.text.toString())
                ticketNuevo.setString(7, "Activo")
                ticketNuevo.setString(8, txtFechaFinal.text.toString())
                ticketNuevo.executeUpdate()
            }

            rcvTickets.layoutManager = LinearLayoutManager(this)

            fun obtenerDatos(): List<dataClassTickets>{
                //obj de clase conexion
                val objConexion = claseConexion().cadenaConexion()

                val statement = objConexion?.createStatement()
                val resultSet = statement?.executeQuery("select * from Tickets")!!

                val tickets = mutableListOf<dataClassTickets>()

                while (resultSet.next()){
                    val numeroTicket = resultSet.getString("numeroTicket")
                    val tituloTicket = resultSet.getString("tituloTicket")
                    val descripcionTicket = resultSet.getString("descripcionTicket")
                    val autor = resultSet.getString("autor")
                    val emailAutor = resultSet.getString("emailAutor")
                    val fechaCreacion = resultSet.getString("fechaCreacion")
                    val estadoTicket = resultSet.getString("estadoTicket")
                    val fechaFin = resultSet.getString("fechaFin")

                    val ticket = dataClassTickets(numeroTicket, tituloTicket, descripcionTicket, autor, emailAutor, fechaCreacion, estadoTicket, fechaFin)
                    tickets.add(ticket)
                }
                return tickets
            }

            CoroutineScope(Dispatchers.IO).launch {
                val Tickets = obtenerDatos()
                withContext(Dispatchers.Main){
                    val adaptador = Adaptador(Tickets)
                    rcvTickets.adapter = adaptador
                }
            }
        }

    }
}