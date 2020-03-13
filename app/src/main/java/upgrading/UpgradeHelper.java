package upgrading;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class UpgradeHelper
{
    public static String ReadNewVersion(String httpUrl)
    {
        try
        {
            URL url = new URL(httpUrl);
            HttpURLConnection uc = (HttpURLConnection) url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            String line;
            StringBuilder lin2 = new StringBuilder();
            while ((line = br.readLine()) != null) {
                lin2.append(line);
            }

            return lin2.toString();
        }
        catch ( IOException e)
        {

        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static boolean IsNewVersionAvailable(String serverVersion, String currentVersion)
    {
        ArrayList<Integer> serverIntVersion = new ArrayList<>();
        ArrayList<Integer> currentIntVersion = new ArrayList<>();

        if(serverVersion.isEmpty())
        {
            serverVersion = "0";
        }

        for(String number : serverVersion.split("\\."))
        {
            serverIntVersion.add(Integer.parseInt(number));
        }

        for(String number : currentVersion.split("\\."))
        {
            currentIntVersion.add(Integer.parseInt(number));
        }

        Integer arraySize = 0;
        if(serverIntVersion.size() > currentIntVersion.size() )
        {
            Integer diffInSize = serverIntVersion.size() - currentIntVersion.size();
            for( int i=0; i < diffInSize; i++)
            {
                currentIntVersion.add(0);
            }
            arraySize = serverIntVersion.size();
        }
        else
        {
            Integer diffInSize = currentIntVersion.size() - serverIntVersion.size();
            for(int i=0; i < diffInSize; i++)
            {
                serverIntVersion.add(0);
            }
            arraySize = currentIntVersion.size();
        };

        for(int i=0; i < arraySize; i++ )
        {
            if(serverIntVersion.get(i) > currentIntVersion.get(i))
            {
                return true;
            }
            else if(serverIntVersion.get(i) < currentIntVersion.get(i))
            {
                return false;
            }
        }

        return false;
    }

    public static void DownloadNewApk()
    {
        // TODO: implement
    }
}
