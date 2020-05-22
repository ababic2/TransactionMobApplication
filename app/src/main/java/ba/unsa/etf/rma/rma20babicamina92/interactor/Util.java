package ba.unsa.etf.rma.rma20babicamina92.interactor;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import ba.unsa.etf.rma.rma20babicamina92.MainActivity;
import ba.unsa.etf.rma.rma20babicamina92.R;

public class Util {
    public static String readStream(InputStream in) {
        Scanner scanner = new Scanner(in);
        String result = "";
        while(scanner.hasNextLine()) {
            result += scanner.nextLine();
        }
        return result;
    }

    public static String getResultFromWeb(String query, MainActivity context) {
        String result = "";
        try {
            String api = context.getResources().getString(R.string.api_url);
            URL url = new URL(api + query);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();
            result = readStream(in);
        } catch (MalformedURLException e) {
            System.out.println("Malformed URL!");
        } catch (IOException e) {
            System.out.println("IOException: "+e.getMessage());
        }
        return result;
    }
}
