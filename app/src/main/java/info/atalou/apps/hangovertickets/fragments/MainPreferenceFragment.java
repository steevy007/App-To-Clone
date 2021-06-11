package info.atalou.apps.hangovertickets.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;

import info.atalou.apps.hangovertickets.R;

import java.util.Locale;

public class MainPreferenceFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    public static final String KEY_PREF_DAY = "key_pref_day";
    public static final String KEY_PREF_GATE = "key_pref_gate";


    public String gatePref_ID, dayPref_ID;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.OnSharedPreferenceChangeListener listener =
                new SharedPreferences.OnSharedPreferenceChangeListener() {
                    @Override
                    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                        if (key.equals(KEY_PREF_DAY)) {
                            dayPref_ID = prefs.getString(KEY_PREF_DAY, "2");
                            switch (dayPref_ID) {
                                case "date_1":
                                    //Locale localeEN = new Locale("en");
                                   // setLocale(localeEN);
                                    break;
                                case "date_2":
                                   // Locale localeES = new Locale("es");
                                   // setLocale(localeES);
                                    break;
                            }
                        } else if (key.equals(KEY_PREF_GATE)) {
                            gatePref_ID = prefs.getString(KEY_PREF_DAY, "2");
                            switch (gatePref_ID) {
                                case "vip":
                                  //  Locale localeEN = new Locale("en");
                                  //  setLocale(localeEN);
                                    break;
                                case "others":
                                  //  Locale localeES = new Locale("es");
                                  //  setLocale(localeES);
                                    break;
                            }
                        }
                    }
                };
        sharedPref.registerOnSharedPreferenceChangeListener(listener);

        addPreferencesFromResource(R.xml.pref_main);

    }


    public void setLocale(Locale locale) {
        Locale.setDefault(locale);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = locale;
        res.updateConfiguration(conf, dm);
        //recreate();
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
        if (key.equals(KEY_PREF_DAY)) {
            dayPref_ID = prefs.getString(KEY_PREF_DAY, "2");
            switch (dayPref_ID) {
                case "date_1":
                   // Locale localeEN = new Locale("en");
                   // setLocale(localeEN);
                    break;
                case "date_2":
                   // Locale localeES = new Locale("es");
                   // setLocale(localeES);
                    break;
            }
        } else if (key.equals(KEY_PREF_GATE)) {
            gatePref_ID = prefs.getString(KEY_PREF_DAY, "2");
            switch (gatePref_ID) {
                case "vip":
                   // Locale localeEN = new Locale("en");
                  //  setLocale(localeEN);
                    break;
                case "others":
                  //  Locale localeES = new Locale("es");
                  //  setLocale(localeES);
                    break;
            }
        }
    }


}