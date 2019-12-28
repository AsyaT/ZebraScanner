package serverDatabaseInteraction;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class WebServiceResponse extends AsyncTask<String, Void, String>
{
    @Override
    protected String doInBackground(String... urlString) {
        try{
            URL url= new URL(urlString[0] );
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setConnectTimeout(5000);

            String header = "Basic " + new String(android.util.Base64.encode(urlString[1].getBytes(), android.util.Base64.NO_WRAP));
            urlConnection.setRequestProperty ("Authorization", header);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setDoOutput(true);

            try (OutputStream outputStream = urlConnection.getOutputStream()) {
                outputStream.write(urlString[2].getBytes());
                outputStream.flush();
            }
            return String.valueOf(urlConnection.getResponseCode());

        } catch (
            ProtocolException exception) {
            exception.printStackTrace();
        } catch (
            MalformedURLException exception) {
            exception.printStackTrace();
        } catch (
            IOException exception) {
            exception.printStackTrace();
        }
        catch (Exception ex)
            {
                throw ex;
            }

        return null;
    }
}
