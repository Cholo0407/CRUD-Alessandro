package alessandro.munoz.aplicacion_crud_alessandro

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import modelo.claseConexion

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtUser = findViewById<EditText>(R.id.txtUser)
        val txtContra = findViewById<EditText>(R.id.txtContra)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnRegistrarse = findViewById<Button>(R.id.btnRegistrarse)

        btnLogin.setOnClickListener {
            val Menu = Intent(this, Menu::class.java)
            GlobalScope.launch(Dispatchers.IO) {
                //obj de la clase conexion
                val objConexion = claseConexion().cadenaConexion()
                val comprobarUser = objConexion?.prepareStatement("Select * from Usuarios where usuario = ? and contrasena ?")!!
                comprobarUser.setString(1, txtUser.text.toString())
                comprobarUser.setString(2, txtContra.text.toString())
                val resultado = comprobarUser.executeQuery()
                if (resultado.next()){
                    startActivity(Menu)
                } else {
                    println("Usuario o contraseña incorrectas!")
                }
            }
        }

        btnRegistrarse.setOnClickListener {
            val Registro = Intent (this@MainActivity, activity_register::class.java)
            startActivity(Registro)
        }
    }
}