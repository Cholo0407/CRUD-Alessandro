package alessandro.munoz.aplicacion_crud_alessandro

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.claseConexion
import java.util.UUID

class activity_register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val txtUserRegistro = findViewById<EditText>(R.id.txtUserRegistro)
        val txtContraRegistro = findViewById<EditText>(R.id.txtContraRegistro)
        val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)

        btnRegistrar.setOnClickListener{
            val login = Intent (this, MainActivity::class.java)
            GlobalScope.launch(Dispatchers.IO) {
                //obj de la clase Conexion
                val objConexion = claseConexion().cadenaConexion()

                val nuevoUsuario = objConexion?.prepareStatement("Insert into Usuarios (uuidUsuario, usuario, contrasena) Values (?, ?, ?)")!!
                nuevoUsuario.setString(1, UUID.randomUUID().toString())
                nuevoUsuario.setString(2, txtUserRegistro.text.toString())
                nuevoUsuario.setString(3, txtContraRegistro.text.toString())
                nuevoUsuario.executeUpdate()
                withContext(Dispatchers.Main){
                    Toast.makeText(this@activity_register, "Usuario Creado", Toast.LENGTH_SHORT).show()
                    startActivity(login)
                }
            }
        }



    }
}