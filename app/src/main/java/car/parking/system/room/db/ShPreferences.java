package car.parking.system.room.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.carparkingsystem.R;

import car.parking.system.room.util.Util;

public class ShPreferences<bundle> {

    protected void onCreate(bundle savedInstanceState){

        setContentView(R.layout.activity_sh_main);

        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.), Context.MODE_PRIVATE);
        float latitude = sharedPref.getFloat(Util.LATITUDE, null);
        float longitude =  sharedPref.getFloat(Util.LONGITUDE, null);
        Toast.makeText(ShPreferences.this,
                "; latitude: " + String.valueOf(latitude) +
                        "; longitude: " + String.valueOf(longitude).show();

    }

    private void setContentView(int activity_sh_main) {
    }
}
