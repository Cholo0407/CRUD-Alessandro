package RecyclerViewHelpers

import alessandro.munoz.aplicacion_crud_alessandro.R
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
    val textView: TextView = view.findViewById(R.id.txtTicketCard)
    val imgEditar: ImageView = view.findViewById(R.id.imgEditar)
    val imgDelete: ImageView = view.findViewById(R.id.imgDelete)

}