package aau.se2.glock.alpha.gameoflife.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.util.Log;

import aau.se2.glock.alpha.gameoflife.core.utilities.ProximityListener;
import aau.se2.glock.alpha.gameoflife.core.utilities.ProximitySensorInterface;

public class SensorListener implements SensorEventListener, ProximitySensorInterface {

    // Sensor proximity distance
    private static final float PROXIMITY_THRESHOLD = 1f;
    // Minimum time the hand should be close to the sensor (2 seconds)
    private static final int MINIMUM_TIME = 2000;
    private SensorManager sensorManager;
    private ProximityListener proximityListener;
    private Sensor proximitySensor;
    private Handler handler = new Handler();
    private Runnable runnable;

    public SensorListener(Context context, ProximityListener proximityListener) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        this.proximityListener = proximityListener;
    }

    @Override
    public void registerSensor() {
        register();
    }

    @Override
    public void unregisterSensor() {
        unregister();
    }

    @Override
    public void setProximityListener(ProximityListener proximityListener) {
        this.proximityListener = proximityListener;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            Log.d("Sensor", "Proximity sensor distance :" + event.values[0]);
            if (event.values[0] < PROXIMITY_THRESHOLD) {
                Log.i("Sensor", "Under PROXIMITY_THRESHOLD");
                // Start the timer
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        // User's hand is close to the sensor for at least 2 seconds
                        triggerAction();
                    }
                };
                handler.postDelayed(runnable, MINIMUM_TIME);
            } else {
                // User's hand is not close enough, cancel the timer
                handler.removeCallbacks(runnable);
            }
        }
    }

    public void register() {
        sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // You might want to override this for more precise control over sensor accuracy changes
    }

    // Unregister the sensor listener in a suitable lifecycle method
    public void unregister() {
        if (sensorManager != null) {
            sensorManager.unregisterListener(this, proximitySensor);
        }
    }

    // The action to trigger when the user's hand is close to the sensor for at least 2 seconds
    private void triggerAction() {
        Log.i("Sensor", "Triggered Action method");
        if (proximityListener != null) {
            proximityListener.onProximity();
        }
    }
}