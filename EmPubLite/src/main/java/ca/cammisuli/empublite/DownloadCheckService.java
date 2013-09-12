package ca.cammisuli.empublite;

import android.app.DownloadManager;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jcammisuli on 11/09/13.
 */
public class DownloadCheckService extends IntentService {

    private static final String UPDATE_URL = "http://misc.commonsware.com/empublite-update.json";
    private static final String UPDATE_BASEDIR = "updates";
    public static final String UPDATE_FILENAME = "book.zip";
    public static final String PREF_PENDING_UPDATE = "pendingUpdateDir";

    public DownloadCheckService() {
        super("DownloadCheckService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        BufferedReader reader = null;

        try {
            URL url = new URL(UPDATE_URL);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();

            c.setRequestMethod("GET");
            c.setReadTimeout(15000);
            c.connect();

            reader = new BufferedReader(new InputStreamReader(c.getInputStream()));
            StringBuilder buf = new StringBuilder();
            String line = null;

            while ((line = reader.readLine()) != null) {
                buf.append(line + "\n");
            }

            checkDownloadInfo(buf.toString());

        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "Exception retrieving update info", e);
        } finally {
            if (reader != null)
            {
                try{
                    reader.close();
                }
                catch (IOException e){
                    Log.e(getClass().getSimpleName(), "Exception closing HUC reader", e);
                }
            }
        }
    }

    static File getUpdateBaseDir(Context context)
    {
        return (new File(context.getFilesDir(), UPDATE_BASEDIR));
    }

    private void checkDownloadInfo(String s) throws JSONException{
        JSONObject json = new JSONObject(s);
        String version = json.names().getString(0);
        File localCopy = new File(getUpdateBaseDir(this), version);

        if (!localCopy.exists())
        {
            PreferenceManager.getDefaultSharedPreferences(this)
                    .edit()
                    .putString(PREF_PENDING_UPDATE, localCopy.getAbsolutePath())
                    .commit();

            String url = json.getString(version);
            DownloadManager mgr = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
            DownloadManager.Request req = new DownloadManager.Request(Uri.parse(url));
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).mkdirs();

            req.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE|DownloadManager.Request.NETWORK_WIFI)
                    .setAllowedOverRoaming(false)
                    .setTitle(getString(R.string.update_title))
                    .setDescription(getString(R.string.update_description))
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, UPDATE_FILENAME);

            mgr.enqueue(req);
        }
    }
}
