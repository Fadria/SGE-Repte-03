package com.jovian.mispedidos.utils

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.jovian.mispedidos.R
import com.jovian.mispedidos.databinding.ActivityQractivityBinding
import com.jovian.mispedidos.model.Pedido
import java.io.IOException
import kotlin.properties.Delegates

private const val CAMERA_REQUEST_CODE = 110

/**
 * @author anonymous
 * @description: Activity la lectura de codigos qr
 * el codigo no es mio, simplemente he modificado codigo para almacenamiento de datos
 * y comportamiento de la activity en caso de lectura correcta o no del codigo
 * como dato, en el MacBook M1 pro no he sido capaz de arrancar camaras externas y virtual scene(delicadito el nene)
 * en windows 11, sin problema
 **/
class QRActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQractivityBinding
    private val requestCodeCameraPermission = 1001
    private lateinit var cameraSource: CameraSource
    private lateinit var barcodeDetector: BarcodeDetector
    private var scannedValue = ""
    private var id: Long = 0
    private var pos: Int = 0
    private var idString = ""
    private var posPedido = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQractivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //alamcenamos en variables distintos datos, como el id del producto,
        //la posicion del producto en el array
        //la posicion del pedido en el listado de pedidos
        //de este modo, podemos localizar el objeto y marcar el check si el codigo qr es correcto
        if(intent.hasExtra("idProducto")) {
            id = intent.getLongExtra("idProducto", 0)
            idString = id.toString()
        }
        if(intent.hasExtra("posicion")) {
            pos = intent.getIntExtra("posicion", 0)
        }
        if(intent.hasExtra("posPedido")){
            posPedido = intent.getIntExtra("posPedido",0)
        }

        //para pruebas
        //Pedido.listaPedidos[posPedido].productos.get(pos).checked = true


        if (ContextCompat.checkSelfPermission(
                this@QRActivity, android.Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            askForCameraPermission()
        } else {
            setupControls()
        }

        val aniSlide: Animation =
            AnimationUtils.loadAnimation(this@QRActivity, R.anim.scanner_animation)
        binding.barcodeLine.startAnimation(aniSlide)
    }


    private fun setupControls() {
        barcodeDetector =
            BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.ALL_FORMATS).build()

        cameraSource = CameraSource.Builder(this, barcodeDetector)
            .setRequestedPreviewSize(1920, 1080)
            .setAutoFocusEnabled(true) //you should add this feature
            .build()

        binding.cameraSurfaceView.getHolder().addCallback(object : SurfaceHolder.Callback {
            @SuppressLint("MissingPermission")
            override fun surfaceCreated(holder: SurfaceHolder) {
                try {
                    //Start preview after 1s delay
                    cameraSource.start(holder)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            @SuppressLint("MissingPermission")
            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
                try {
                    cameraSource.start(holder)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                cameraSource.stop()
            }
        })


        barcodeDetector.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {
                Toast.makeText(applicationContext, "Scanner has been closed", Toast.LENGTH_SHORT)
                    .show()
            }

            //aqui esta la crema de toda la clase
            //cogemos la lectura del codigo qr y comparamos con el id del producto
            //si es correcto, marcamos el check a true y lo indicamos
            //en caso contrario, indicamos el error
            //y cada vez que finaliza la lectura, finaliza la activity
            override fun receiveDetections(detections: Detector.Detections<Barcode>) {
                val barcodes = detections.detectedItems
                if (barcodes.size() == 1) {
                    scannedValue = barcodes.valueAt(0).rawValue


                    //Don't forget to add this line printing value or finishing activity must run on main thread
                    runOnUiThread {
                        cameraSource.stop()
                        if(scannedValue.equals(idString)){
                            Toast.makeText(this@QRActivity, "Producto registrado", Toast.LENGTH_SHORT).show()
                            Pedido.listaPedidos[posPedido].productos.get(pos).checked = true
                        }
                        else Toast.makeText(this@QRActivity, "El codigo del producto no coincide", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }else
                {
                    Toast.makeText(this@QRActivity, "value- else", Toast.LENGTH_SHORT).show()

                }
            }
        })
    }

    private fun askForCameraPermission() {
        ActivityCompat.requestPermissions(
            this@QRActivity,
            arrayOf(android.Manifest.permission.CAMERA),
            requestCodeCameraPermission
        )
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == requestCodeCameraPermission && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setupControls()
            } else {
                Toast.makeText(applicationContext, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraSource.stop()
    }
}