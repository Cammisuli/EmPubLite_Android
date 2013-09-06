package ca.cammisuli.empublite;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.actionbarsherlock.app.SherlockFragmentActivity;

/**
 * Created by jcammisuli on 06/09/13.
 */
public class ContentsAdapter extends FragmentStatePagerAdapter {

    public ContentsAdapter(SherlockFragmentActivity ctxt) {
        super(ctxt.getSupportFragmentManager());
    }

    @Override
    public Fragment getItem(int i) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
