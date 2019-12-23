package serverDatabaseInteraction;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class WebService extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... urlString)
    {
        try {
            URL url= new URL(urlString[0] );
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(5000);

            String header = "Basic " + new String(android.util.Base64.encode(urlString[1].getBytes(), android.util.Base64.NO_WRAP));
            urlConnection.setRequestProperty ("Authorization", header);
            InputStream inputStream = urlConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line="", result="";

            while ((line = bufferedReader.readLine()) != null) {
                if (line.isEmpty()) {
                    break;
                }
                result += line;
            }
            urlConnection.disconnect();
            return result;

            } catch (ProtocolException exception) {
            exception.printStackTrace();
        } catch (MalformedURLException exception) {
            exception.printStackTrace();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        catch (Exception ex)
        {
            throw ex;
        }

        return null;
    }

}
