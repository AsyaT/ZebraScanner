package upgrading;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import ru.zferma.zebrascanner.BuildConfig;
import ru.zferma.zebrascanner.ScannerApplication;

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
        mTimer.scheduleAtFixedRate(new VersionComparesTask(), 0, NOTIFY_INTERVAL);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent,flags,startId);
    }

    class VersionComparesTask extends TimerTask
    {

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void run()
        {
            try {
                ScannerApplication appState = ((ScannerApplication) getApplication());
                String webServerURL = "http://"+appState.versionUpdateServerConnection.GetServerAddress()+"/" ;
                String globalFolder = Environment.getExternalStorageDirectory().toString();;
                String deviceLocationFolderName = "APPS";
                String apkFileName = "zebraapp.apk";

                String currentVersionName = BuildConfig.VERSION_NAME;
                String serverVersion = UpgradeHelper.ReadNewVersion(webServerURL );

                if (serverVersion!=null && UpgradeHelper.IsNewVersionAvailable(serverVersion, currentVersionName))
                {
                    File file = UpgradeHelper.CreateFile(globalFolder, deviceLocationFolderName, apkFileName);

                    UpgradeHelper.DownloadNewApk(webServerURL + apkFileName, file);

                    UpgradeHelper.InstallApk(getBaseContext(), globalFolder + "/"+deviceLocationFolderName+"/" + apkFileName);
                }
            }
            catch (IOException ioException)
            {

            }
            catch(Exception ex)
            {

            }
        }
    }
}
