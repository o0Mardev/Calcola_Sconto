package com.marco.calcola_sconto.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.marco.calcola_sconto.MainActivity;
import com.marco.calcola_sconto.R;

public class SettingsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        LoadSettings(SettingsActivity.this);

//        SharedPreferences sharedPreferences3 =
//                PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this);
//        boolean black_mode = sharedPreferences3.getBoolean("check_box_preference_black_mode",false);
//
//        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
//
//        if (black_mode==true && currentNightMode == Configuration.UI_MODE_NIGHT_YES){
//            setTheme(R.style.Theme);
//        }


//        setTheme(R.style.Theme);

        super.onCreate(savedInstanceState);


        setContentView(R.layout.settings_activity);

        ImageView imageView = findViewById(R.id.imageView);

        FloatingActionButton button = findViewById(R.id.apply_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
//
//                if (black_mode==true && currentNightMode == Configuration.UI_MODE_NIGHT_YES){
//                    setTheme(R.style.Theme);
//                }


                Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(
                        getBaseContext().getPackageName() );
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i); finish();

            }
        });


        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (toolbar != null) {
            ActionBar ab = getSupportActionBar();
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setTitle(Html.fromHtml("<font color=\"#ffffff\">" + getString(R.string.action_settings)));


        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }

    }


    public void LoadSettings(Context context) {

        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context/* Activity context */);
        String theme = sharedPreferences.getString("theme", "follow_system");
        switch (theme) {
            case "light":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case "dark":
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case "follow_system":
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
            case "battery_saver":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
        }

    }


}