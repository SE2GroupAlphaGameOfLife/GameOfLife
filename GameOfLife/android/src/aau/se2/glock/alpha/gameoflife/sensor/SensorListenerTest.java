package aau.se2.glock.alpha.gameoflife.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;

import aau.se2.glock.alpha.gameoflife.core.utilities.ProximityListener;

/*
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;*/

public class SensorListenerTest {
/*
    @Mock
    private Context mockContext;

    @Mock
    private ProximityListener mockProximityListener;

    @Mock
    private SensorManager mockSensorManager;

    @Mock
    private Sensor mockProximitySensor;

    private SensorListener sensorListener;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(mockContext.getSystemService(Context.SENSOR_SERVICE)).thenReturn(mockSensorManager);
        when(mockSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)).thenReturn(mockProximitySensor);
        sensorListener = new SensorListener(mockContext, mockProximityListener);
    }

    @Test
    public void registerSensor_registersListener() {
        sensorListener.registerSensor();
        verify(mockSensorManager).registerListener(sensorListener, mockProximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Test
    public void unregisterSensor_unregistersListener() {
        sensorListener.unregisterSensor();
        verify(mockSensorManager).unregisterListener(sensorListener, mockProximitySensor);
    }

    @Test
    public void setProximityListener_setsListener() {
        ProximityListener newListener = proximityListener -> {};
        sensorListener.setProximityListener(newListener);
        // You can't directly verify a setter method, but you can test its effect
        // For example, if the onProximity method is called on the sensorListener,
        // you can verify that it's also called on the newListener
        sensorListener.onSensorChanged(null);
        verify(newListener).onProximity();
    }*/
}

