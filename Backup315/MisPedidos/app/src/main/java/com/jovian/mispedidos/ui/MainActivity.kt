package com.jovian.mispedidos.ui

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.jovian.mispedidos.adapter.PedidoAdapter
import com.jovian.mispedidos.databinding.ActivityMainBinding
import com.jovian.mispedidos.model.Pedido
import com.jovian.mispedidos.utils.CHANNEL_ID
import com.jovian.mispedidos.utils.CHANNEL_NAME
import com.jovian.mispedidos.utils.NOTIFICATION_ID
import com.jovian.mispedidos.utils.PENDING_REQUEST
import kotlinx.coroutines.*

/**
 * @author Cassandra Sowa, Federico Adria, Esther Talavera, Javier Tamarit, Jorge Victoria
 * @since 10Feb2022
 * @version 1.0
 * @description: Activity pricipal de la app
 * desde aqui invocamos distintas funciones para la lectura de datos de api y carga en recyclerview
 **/
class MainActivity : AppCompatActivity() {
    //variable para el binding
    private lateinit var binding: ActivityMainBinding
    //variable para gestion de notificaciones
    val pendingIntent: PendingIntent by lazy { makePendingIntent() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //coroutine para llamada a la funcion
        GlobalScope.launch {
            leerDatos()
        }

    }

    //funcion para realizar llamadas al servidor cada cierto tiempo
    //hemos incluido un bucle para pruebas
    //TODO investigar sobre JOB
    //por cada paso del bucles llamamos a la funcion getPedidos para leer datos y cargar el recycler
    //esto nos permite capturar pedidos nuevos en tiempo real
    //y no cargar el recycler view hasta que se complete la lectura de datos
    //tambien saltará una notificacion cuando entre un pedido nuevo
    private suspend fun leerDatos() {
        for(num in 1..1000) {
            Pedido.getPedidos(this, {
                InitRecycler()
            }, {
                sendNotification("")
            })
            delay(10000)
            //para test del desarrollador
            Log.i("loading", "loading")
        }
    }

    //funcion para cargar del recycler view
    fun InitRecycler(){
        binding.rvPedidos.layoutManager = LinearLayoutManager(this)
        val adapter = PedidoAdapter(this)
        binding.rvPedidos.adapter = adapter
    }

    //funcion para el envio de notificaciones
    fun sendNotification(s: String?) {
        CoroutineScope(Dispatchers.Default).launch {
            delay(600L)//short  delay
            makeNotificationChannel()//Create the channel
            makeNotificaton(s)//Start to do notification
        }
    }


    //funcion para generar el channel de las notificicaciones
     fun makeNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

            notificationChannel.setShowBadge(true)

            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    //funcion para crear notificaciones
    private fun makeNotificaton(s: String?) {
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(
                this,
                CHANNEL_ID
            )

        with(builder) {
            setSmallIcon(android.R.drawable.ic_popup_reminder)
            setContentTitle("ha llegado un pedido").color = Color.RED
            var xd = s?.substring(s.indexOf('\n') + 1);

            setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(xd)
            )

            setAutoCancel(true)
            color = Color.RED//color
            priority = NotificationCompat.PRIORITY_DEFAULT

            setLights(Color.MAGENTA, 1000, 1000)

            setVibrate(longArrayOf(1000, 1000, 1000, 1000))

            setDefaults(Notification.DEFAULT_SOUND)

            setContentIntent(pendingIntent)

            setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

            setFullScreenIntent(pendingIntent, true)

        }

        val notificationManagerCompat = NotificationManagerCompat.from(this)

        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build())
    }

    private fun makePendingIntent(): PendingIntent {
        val intent = Intent(this, MainActivity::class.java)

        return PendingIntent.getActivity(this, PENDING_REQUEST, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }


}