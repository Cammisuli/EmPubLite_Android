package ca.cammisuli.empublite;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by Jon on 9/7/13.
 */
public class BookContents {
    JSONObject raw = null;
    JSONArray chapters;
    File updateDir = null;

    BookContents(JSONObject raw)
    {
        this(raw, null);
    }

    public BookContents(JSONObject raw, File updateDir) {
        this.raw = raw;
        this.updateDir = updateDir;
        chapters = raw.optJSONArray("chapters");
    }

    int getChapterCount()
    {
        return (chapters.length());
    }

    String getChapterFile(int position)
    {

        JSONObject chapter = chapters.optJSONObject(position);

        if (updateDir != null)
        {
            return (Uri.fromFile(new File(updateDir, chapter.optString("file"))).toString());
        }
        return ("file:///android_asset/book/"+chapter.optString("file"));
    }

    String getTitle()
    {
        return (raw.optString("title"));
    }
}
