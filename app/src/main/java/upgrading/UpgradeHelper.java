package upgrading;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class UpgradeHelper
{
    public static String ReadNewVersion(String httpUrl) throws IOException
    {
        URL url = new URL(httpUrl);
        HttpURLConnection uc = (HttpURLConnection) url.openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader(uc.getInputStream()));
        String line;
        StringBuilder lin2 = new StringBuilder();
        while ((line = br.readLine()) != null)
        {
            lin2.append(line);
        }

        return lin2.toString();
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

        if(currentVersion.isEmpty())
        {
            currentVersion = "0";
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

    public static void DownloadNewApk(String fileURL, File existingFile)
    {
        try {

            FileOutputStream f = new FileOutputStream(existingFile);
            URL u = new URL(fileURL);
            HttpURLConnection c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            //c.setDoOutput(true);
            c.connect();
            InputStream in = c.getInputStream();
            byte[] buffer = new byte[1024];
            int len1 = 0;
            while ((len1 = in.read(buffer)) > 0) {
                f.write(buffer, 0, len1);
            }
            f.close();
        } catch (Exception e)
        {
            System.out.println("exception in DownloadFile: --------"+e.toString());
            e.printStackTrace();
        }
    }

    public static File CreateFile(String destinationPath, String folderName, String fileName)
    {
        String extStorageDirectory = destinationPath;
        File folder = new File(extStorageDirectory, folderName);
        folder.mkdir();
        File file = new File(folder, fileName);
        try {
            file.createNewFile();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return file;
    }

    public static void InstallApk(Context context, String filePath)
    {
        File file = new File(filePath);

        if(file.exists()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(
                    Uri.fromFile(file),
                    "application/vnd.android.package-archive"
            );
            context.startActivity(intent);
        }
    }

    public static void StartService(Context context)
    {
        Intent intent = new Intent(context, DownloadingService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
    }
}
