package ca.cammisuli.empublite;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;

import java.io.File;

/**
 * Created by jcammisuli on 11/09/13.
 */
public class DownloadCompleteReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        File update = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), DownloadCheckService.UPDATE_FILENAME);

        if (update.exists())
        {
            context.startService(new Intent(context, DownloadInstallService.class));
        }
    }
}
