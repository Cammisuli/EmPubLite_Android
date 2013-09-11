package ca.cammisuli.empublite;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class EmPubLiteActivity extends SherlockFragmentActivity {

    private ViewPager pager = null;
    private ContentsAdapter adapter = null;
    private static final String MODEL = "model";
    private SharedPreferences prefs = null;
    private static final String PREF_LAST_POSITION = "lastPosition";
    private static final String PREF_SAVE_LAST_POSITION = "saveLastPosition";
    private static final String PREF_KEEP_SCREEN_ON = "keepScreenOn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pager = (ViewPager)findViewById(R.id.pager);

        if(getSupportFragmentManager().findFragmentByTag(MODEL)==null)
        {
            getSupportFragmentManager().beginTransaction().add(new ModelFragment(), MODEL).commit();
        }

        setContentView(R.layout.main);

        pager = (ViewPager)findViewById(R.id.pager);

        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public void onPause()
    {
        if (prefs!=null)
        {
            int position=pager.getCurrentItem();
            prefs.edit().putInt(PREF_LAST_POSITION, position).apply();
            Toast.makeText(getApplicationContext(), "" + prefs.getInt(PREF_LAST_POSITION, 0), Toast.LENGTH_LONG).show();
        }
        super.onPause();
    }

    @Override
    public void onResume()
    {
        if (prefs != null)
        {
            pager.setKeepScreenOn(prefs.getBoolean(PREF_KEEP_SCREEN_ON, false));
        }

        super.onResume();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.options, menu);

        return (super.onCreateOptionsMenu(menu));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                pager.setCurrentItem(0, false);
                return (true);
            case R.id.about:
                Intent i = new Intent(this, SimpleContentActivity.class);
                i.putExtra(SimpleContentActivity.EXTRA_FILE, "file:///android_asset/misc/about.html");
                startActivity(i);

                return (true);
            case R.id.help:
                i = new Intent(this, SimpleContentActivity.class);
                i.putExtra(SimpleContentActivity.EXTRA_FILE, "file:///android_asset/misc/help.html");
                startActivity(i);

                return (true);
            case R.id.settings:
                startActivity(new Intent(this, Preferences.class));
                return (true);
            case R.id.notes:
                i = new Intent(this, NoteActivity.class);
                i.putExtra(NoteActivity.EXTRA_POSITION, pager.getCurrentItem());
                startActivity(i);
                return (true);
            case R.id.update:
                startService(new Intent(this, DownloadCheckService.class));
                return (true);
        }
        return (super.onOptionsItemSelected(item));
    }

    void setupPager(SharedPreferences prefs, BookContents contents)
    {
        this.prefs = prefs;
//        Toast.makeText(getApplicationContext(), "" + prefs.getBoolean(PREF_SAVE_LAST_POSITION, false), Toast.LENGTH_LONG).show();
//        Toast.makeText(getApplicationContext(), "" + prefs.getInt(PREF_LAST_POSITION, 0), Toast.LENGTH_LONG).show();
        if (prefs.getBoolean(PREF_SAVE_LAST_POSITION, false))
        {
            pager.setCurrentItem(prefs.getInt(PREF_LAST_POSITION, 0));
        }


        adapter = new ContentsAdapter(this, contents);
        pager.setAdapter(adapter);

        findViewById(R.id.progressBar).setVisibility(View.GONE);
        findViewById(R.id.pager).setVisibility(View.VISIBLE);
    }
}
