package upgrading;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import ru.zferma.zebrascanner.BuildConfig;

public class DownloadingService extends Service
{
    private Timer mTimer = null;
    public static final long NOTIFY_INTERVAL = 60 * 1000; // 60 seconds


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        // cancel if already existed
        if(mTimer != null) {
            mTimer.cancel();
        } else {
            // recreate new
            mTimer = new Timer();
        }
        // schedule task
        mTimer.scheduleAtFixedRate(new VersionComparerTask(), 0, NOTIFY_INTERVAL);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //Toast.makeText(this, "My Service Started", Toast.LENGTH_LONG).show();
        //Log.d(TAG, "onStart");

        return super.onStartCommand(intent,flags,startId);
    }

    class VersionComparerTask extends TimerTask
    {

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void run()
        {
            String currentVersionName = BuildConfig.VERSION_NAME;

            String serverVersion = UpgradeHelper.ReadNewVersion("http://192.168.88.217/");

            if(UpgradeHelper.IsNewVersionAvailable(serverVersion, currentVersionName))
            {
                String extStorageDirectory = "/storage/emulated/0/Download";
                File folder = new File(extStorageDirectory, "APPS");
                folder.mkdir();
                File file = new File(folder, "zebraapp."+"apk");
                try {
                    file.createNewFile();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                UpgradeHelper.DownloadNewApk("http://192.168.88.217/zebraapp.apk", file);


                //  install new apk
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(
                        Uri.fromFile( new File(extStorageDirectory + "/APPS/" + "zebraapp.apk")),
                        "application/vnd.android.package-archive"
                );
                startActivity(intent);
            }

        }
    }
}
