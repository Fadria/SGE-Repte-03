package com.jovian.repte.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.jovian.repte.interfaces.ApiService
import com.jovian.repte.model.Pedido
import com.jovian.repte.model.PedidoResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PedidosViewModel (application: Application): AndroidViewModel(application){
    // Variables LiveData que observaremos en el MainActivity
    val pedidosListLD: MutableLiveData<List<Pedido>> = MutableLiveData()
    val errorLD: MutableLiveData<String> = MutableLiveData()

    // Función usada para obtener el listado de productos
    fun getPedidos(){
        //llamada a internet
        // coroutine para realizar llamadas asincronas y no usar el hilo principal
        CoroutineScope(Dispatchers.IO).launch {
            //pasamos la parte variable de la URL, que usaremos en la interfaz
            val call = getRetrofit().create(ApiService::class.java).getPedidoById("obtenerPedidos")
            val pedidosResponse: PedidoResponse? = call.body()
            if (call.isSuccessful) {
                //Show recyclerview
                val misPedidos: ArrayList<Pedido> = (pedidosResponse?.pedidos ?: emptyList()) as ArrayList<Pedido>

                pedidosListLD.postValue(misPedidos)
            } else {
                errorLD.postValue("No se han podido recuperar los pedidos")
            }
        }
    }

    /**
     * instancia del objeto retrofit
     * contiene la parte fija de la url de la api
     * y el conversor de datos JSON
     * @return un retrofit.Builder
     */
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://192.168.1.139:8069/almacen/apirest/") //la url debe acabar siempre con barra
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}