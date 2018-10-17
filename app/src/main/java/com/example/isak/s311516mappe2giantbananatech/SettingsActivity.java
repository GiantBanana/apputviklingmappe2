package com.example.isak.s311516mappe2giantbananatech;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.view.View;


public class SettingsActivity extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getFragmentManager().beginTransaction().replace(R.id.frag_content, new PrefFrag()).commit();
    }

    public static class PrefFrag extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
            final SwitchPreference on_off = (SwitchPreference) findPreference("on_off");
            if(on_off.isChecked()){
                findPreference("set_time").setEnabled(true);
                findPreference("set_message").setEnabled(true);
            }else{
                findPreference("set_time").setEnabled(false);
                findPreference("set_message").setEnabled(false);
            }
            on_off.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    if (on_off.isChecked()){
                        findPreference("set_time").setEnabled(true);
                        findPreference("set_message").setEnabled(true);
                    }else{
                        findPreference("set_time").setEnabled(false);
                        findPreference("set_message").setEnabled(false);
                    }
                    return false;
                }
            });
        }

        public void enablePreference(View view){
            getPreferenceScreen().findPreference("set_message").setEnabled(true);
            getPreferenceScreen().findPreference("set_time").setEnabled(true);
        }
    }
}
