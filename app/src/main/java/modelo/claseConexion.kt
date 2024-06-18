package modelo

import java.sql.Connection
import java.sql.DriverManager

class claseConexion {
    fun cadenaConexion(): Connection? {

        try {
            //ip propia
            val ip = "jdbc:oracle:thin:@192.168.1.16:1521:xe"
            val usuario = "system"
            //contraseña propia
            val contrasena = "LACHOLA"

            val connection = DriverManager.getConnection(ip,usuario,contrasena)
            return connection
        }
        catch (e: Exception){
            println("Este es el error $e")
            return null
        }

    }
}