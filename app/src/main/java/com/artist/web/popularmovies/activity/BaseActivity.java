package com.artist.web.popularmovies.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.artist.web.popularmovies.R;

public class BaseActivity extends AppCompatActivity
        implements SharedPreferences.OnSharedPreferenceChangeListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getMoviePreferences();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        String themeName = sharedPreferences.getString(getString(R.string.preference_theme_key),
                getString(R.string.pref_theme_red_value));
        if(themeName.equals("ShroomHaze")) {
            setTheme(R.style.ShroomHaze);
        }else if(themeName.equals("RainbowBlue")){
            setTheme(R.style.RainbowBlue);
        }
        recreate();
    }

    private void getMoviePreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String themeName = sharedPreferences.getString(getString(R.string.preference_theme_key)
                ,getString(R.string.pref_theme_red_value));
        if(themeName.equals("ShroomHaze")) {
            setTheme(R.style.ShroomHaze);
        }else if(themeName.equals("RainbowBlue")){
            setTheme(R.style.RainbowBlue);
        }
        //register the listener
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregister VisualizerActivity as an OnPreferenceChangedListener to avoid any memory leaks.
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }
}
