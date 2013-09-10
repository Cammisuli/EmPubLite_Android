package ca.cammisuli.empublite;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.actionbarsherlock.app.SherlockFragmentActivity;

/**
 * Created by Jon on 9/9/13.
 */
public class NoteActivity extends SherlockFragmentActivity {
    public static final String EXTRA_POSITION = "position";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if(getSupportFragmentManager().findFragmentById(android.R.id.content)==null)
        {
            int position = getIntent().getIntExtra(EXTRA_POSITION, -1);

            if (position >= 0)
            {
                Fragment f = NoteFragment.newInstance(position);
                getSupportFragmentManager().beginTransaction().add(android.R.id.content, f).commit();
            }
        }
    }
}
