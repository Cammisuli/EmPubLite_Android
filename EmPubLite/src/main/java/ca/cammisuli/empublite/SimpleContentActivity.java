package ca.cammisuli.empublite;

import android.os.Bundle;
import android.view.Menu;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public class SimpleContentActivity extends SherlockFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_content_activity);
    }

}
