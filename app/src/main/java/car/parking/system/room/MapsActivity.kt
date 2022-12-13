package car.parking.system.room;

import android.annotation.SuppressLint
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import car.parking.system.room.db.Contrato
import car.parking.system.room.db.DB
import com.example.carparkingsystem.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions



internal class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    var mDbHelper: DB? = null
    var db: SQLiteDatabase? = null
    var c_coordenadas: Cursor? = null
    private var mMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        mDbHelper = DB(this)
        db = mDbHelper!!.getWritableDatabase()
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @SuppressLint("Range")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val viana = LatLng(41.6945464, -8.847001)
        mMap!!.addMarker(MarkerOptions().position(viana).title("ESTG"))
        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(viana))
        mMap!!.setMinZoomPreference(11.0f)
        //        mMap.zoomTo(float)
//        mMap.zoomTo(float)

        var c_coordenadas = db?.query(
            Contrato.Coordenada.TABLE_NAME,
            Contrato.Coordenada.PROJECTION,
            null,
            null,
            null,
            null,
            "_ID DESC",
            null
        )

        if (c_coordenadas != null) {
            c_coordenadas.moveToFirst()
        }
        var i = 1
        if (c_coordenadas != null) {
            while (!c_coordenadas.isAfterLast()) {
                val latitude: Float? = c_coordenadas?.getFloat(c_coordenadas.getColumnIndex("latitude"))
                val longitude: Float? = c_coordenadas?.getFloat(c_coordenadas.getColumnIndex("longitude"))
                val pos = longitude?.let { latitude?.let { it1 -> LatLng(it1.toDouble(), it.toDouble()) } }
                pos?.let { MarkerOptions().position(it).title(Integer.toString(i)) }
                    ?.let { mMap!!.addMarker(it) }
                c_coordenadas.moveToNext()
                i++
            }
        }
        // make sure to close the cursor
        if (c_coordenadas != null) {
            c_coordenadas.close()
        }
    }


}
