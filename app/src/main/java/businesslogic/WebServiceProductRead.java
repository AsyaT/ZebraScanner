package businesslogic;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WebServiceProductRead extends AsyncTask<String, Void, List<ProductModel.ProductListModel>> {

    @Override
    protected List<ProductModel.ProductListModel> doInBackground(String... urlString) {

        List<ProductModel.ProductListModel> productListModel = new ArrayList<ProductModel.ProductListModel>();

        try {

            URL url= new URL(urlString[0] );
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(5000);

            String header = "Basic " + new String(android.util.Base64.encode(urlString[1].getBytes(), android.util.Base64.NO_WRAP));
            urlConnection.setRequestProperty ("Authorization", header);

            InputStream inputStream = urlConnection.getInputStream();
            Gson gson = new Gson();

            JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
            try {
                reader.beginObject();
                while (reader.hasNext())
                {
                    String name = reader.nextName();
                    reader.beginArray();

                    while (reader.hasNext()) {

                        ProductModel.ProductListModel product = gson.fromJson(reader, ProductModel.ProductListModel.class);
                        productListModel.add(product);
                    }

                    reader.endArray();
                }
                reader.endObject();
                urlConnection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }
        } catch (ProtocolException exception) {
            exception.printStackTrace();
        } catch (MalformedURLException exception) {
            exception.printStackTrace();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return productListModel;
    }

}
