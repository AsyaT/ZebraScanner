package upgrading;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class Autostart extends BroadcastReceiver
{
    public void onReceive(Context context, Intent arg1)
    {
        UpgradeHelper.StartService(context);
        Log.i("Autostart", "started");
    }
}
