package com.example.puroguramingujissenii;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

public class MainActivity extends Activity implements SensorEventListener, LocationListener {

    // センサマネージャ
    private SensorManager sensorManager;
    // 各センサ
    private Sensor lightSensor;
    private Sensor magneticSensor;

    // 位置情報マネージャ
    private LocationManager locationManager;

    // 各表示用
    private TextView lightTextView;
    private TextView magneticTextView;
    private TextView locationTextView;

    private static final int REQUEST_LOCATION = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // activity_main.xml に3つのTextViewを用意しておく

        // TextViewの取得
        lightTextView = findViewById(R.id.lightSensorText);
        magneticTextView = findViewById(R.id.magneticSensorText);
        locationTextView = findViewById(R.id.locationSensorText);

        // センサの準備
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        // 位置情報の準備
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // パーミッション確認
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // センサのリスナー登録
        if (lightSensor != null) {
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (magneticSensor != null) {
            sensorManager.registerListener(this, magneticSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

        // 位置情報のリスナー登録
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
        locationManager.removeUpdates(this);
    }

    // センサの値が変化したとき
    @SuppressLint("DefaultLocale")
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            float light = event.values[0];
            lightTextView.setText(String.format("明るさセンサ: %.2f ルクス", light));
        }
        else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            magneticTextView.setText(String.format("地磁気センサ: X=%.2f, Y=%.2f, Z=%.2f", x, y, z));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    // GPSの位置が変わったとき
    @SuppressLint("DefaultLocale")
    @Override
    public void onLocationChanged(@NonNull Location location) {
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        locationTextView.setText(String.format("位置情報: 緯度=%.3f 経度=%.3f", lat, lng));
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {}
    @Override
    public void onProviderDisabled(@NonNull String provider) {
        Toast.makeText(this, "GPSがオフになっています", Toast.LENGTH_LONG).show();
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}
}
