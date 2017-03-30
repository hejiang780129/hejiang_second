package com.hbjr.washcheapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MyTestActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_test);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_test, menu);
		return true;
	}

}
