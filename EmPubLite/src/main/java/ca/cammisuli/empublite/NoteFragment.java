package ca.cammisuli.empublite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.actionbarsherlock.app.SherlockFragment;

/**
 * Created by Jon on 9/9/13.
 */
public class NoteFragment extends SherlockFragment {
    private static final String KEY_POSITION = "position";
    private EditText editor = null;

    static NoteFragment newInstance(int position)
    {
        NoteFragment frag = new NoteFragment();
        Bundle args = new Bundle();

        args.putInt(KEY_POSITION, position);

        return (frag);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View result = inflater.inflate(R.layout.editor, container, false);
        int position = getArguments().getInt(KEY_POSITION, -1);

        editor = (EditText)result.findViewById(R.id.editor);

        return(result);
    }
}
