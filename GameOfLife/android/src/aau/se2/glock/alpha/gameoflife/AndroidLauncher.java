package aau.se2.glock.alpha.gameoflife;

import android.os.Bundle;
import android.view.WindowManager;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import aau.se2.glock.alpha.gameoflife.sensor.SensorListener;

public class AndroidLauncher extends AndroidApplication {

	private SensorListener sensorListener;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		sensorListener = new SensorListener(this, null);
		GameOfLife.proximitySensorInterface = sensorListener;
		initialize(GameOfLife.getInstance(), config);
	}

	@Override
	protected void onResume() {
		super.onResume();
		sensorListener.register();
	}

	@Override
	protected void onPause() {
		sensorListener.unregister();
		super.onPause();
	}
}
