package app.itakura.reirei.database
import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_g_p_s.*
import kotlinx.android.synthetic.main.activity_main.*


class GPS : AppCompatActivity(), LocationListener,OnMapReadyCallback {

    // lateinit: late initialize to avoid checking null
    private lateinit var locationManager: LocationManager

    private var map: GoogleMap? = null

    private var mylocation: Location? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_g_p_s)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1000
            )
        } else {
            locationStart()

            if (::locationManager.isInitialized) {
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    1000,
                    50f,
                    this
                )
            }

            button.setOnClickListener {
                val mainPage = Intent(this, MainActivity::class.java)
                startActivity(mainPage)
            }

        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        map = googleMap

        val place =LatLng(mylocation?.getLatitude()?:,mylocation?.getLongitude()?:)

        //val tokyo = LatLng(35.689521, 139.691704)
        map?.addMarker(MarkerOptions().position(place).title("東京"))

        val cameraPosition = CameraPosition.Builder()
            .zoom(12f)
            .target(place)
            .build()
        map?.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    private fun locationStart() {
        Log.d("debug", "locationStart()")

        // Instances of LocationManager class must be obtained using Context.getSystemService(Class)
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Log.d("debug", "location manager Enabled")
        } else {
            // to prompt setting up GPS
            val settingsIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(settingsIntent)
            Log.d("debug", "not gpsEnable, startActivity")
        }

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1000
            )

            Log.d("debug", "checkSelfPermission false")
            return
        }

        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            1000, 50f, this
        )

    }

    /**
     * Android Quickstart:
     * https://developers.google.com/sheets/api/quickstart/android
     *
     * Respond to requests for permissions at runtime for API 23 and above.
     * @param requestCode The request code passed in
     * requestPermissions(android.app.Activity, String, int, String[])
     * @param permissions The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     * which is either PERMISSION_GRANTED or PERMISSION_DENIED. Never null.
     */
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        if (requestCode == 1000) {
            // 使用が許可された
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("debug", "checkSelfPermission true")

                locationStart()

            } else {
                // それでも拒否された時の対応
                val toast = Toast.makeText(
                    this,
                    "これ以上なにもできません", Toast.LENGTH_SHORT
                )
                toast.show()
            }
        }
    }

    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
        /* API 29以降非推奨
        when (status) {
            LocationProvider.AVAILABLE ->
                Log.d("debug", "LocationProvider.AVAILABLE")
            LocationProvider.OUT_OF_SERVICE ->
                Log.d("debug", "LocationProvider.OUT_OF_SERVICE")
            LocationProvider.TEMPORARILY_UNAVAILABLE ->
                Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE")
        }
         */
    }

    override fun onProviderEnabled(provider: String?) {
        TODO("Not yet implemented")
    }

    override fun onProviderDisabled(provider: String?) {
        TODO("Not yet implemented")
    }

    override fun onLocationChanged(location: Location) {

        mylocation = location


        button.setOnClickListener {

            val yourspot = "Latitude" + location.getLatitude()
            val yourspot1 = "Longitude" + location.getLongitude()

            if (yourspot.isNotEmpty()) {
                val MainActivity = Intent(this, MainActivity::class.java)

                MainActivity.putExtra("Latitude", yourspot)
                MainActivity.putExtra("Longitude", yourspot1)

                LatLng(
                    location.getLatitude(),location.getLongitude()
                )

                startActivity(MainActivity)


            }
        }







    }


}

