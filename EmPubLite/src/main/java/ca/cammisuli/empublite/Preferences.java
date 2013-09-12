package ca.cammisuli.empublite;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockPreferenceActivity;

import java.util.List;

/**
 * Created by jcammisuli on 09/09/13.
 *
 * This creates the settings activity. If it is on a tablet, two panes are shown, if it's on a phone, only one pane will show
 */
public class Preferences extends SherlockPreferenceActivity {

    private boolean needResource = false;

    @SuppressWarnings("deprecation")
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if(needResource || Build.VERSION.SDK_INT<Build.VERSION_CODES.HONEYCOMB)
        {
            addPreferencesFromResource(R.xml.pref_display);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onBuildHeaders(List<Header> target)
    {
        if (onIsHidingHeaders() || !onIsMultiPane())
        {
            needResource = true;
        }
        else
        {
            loadHeadersFromResource(R.xml.preference_headers, target);
        }

    }
}
