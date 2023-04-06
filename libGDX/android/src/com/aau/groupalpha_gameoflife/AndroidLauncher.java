package com.aau.groupalpha_gameoflife;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.aau.groupalpha_gameoflife.GameOfLife;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		//We use GameOfLife.getInstance() instead of a Constructor because it is a Singleton
		initialize(GameOfLife.getInstance(), config);
	}
}
