package com.example.employeemanagement.Controller;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by watanabehiroaki on 2018/03/26.
 */

//＜doInBackgroundの引数,onProgressUpdateの引数,onPostExecuteの引数＞
public class AsyncAPIRequest extends AsyncTask<String, Void, String> {

    private String app_version = "1.0.0";
    private String parameter,readSt;
    private HttpURLConnection httpURLConnection;


    @Override
    protected String doInBackground(String... strings) {

        setParameter("blue_test@blue-corporation.jp", "blue1234", "0");

        URL url = null;
        try {
            url = new URL(strings[0]);

            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Accept", "application/json");
            httpURLConnection.setRequestProperty("App-Version", app_version);
            httpURLConnection.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpURLConnection.setInstanceFollowRedirects(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            httpURLConnection.connect();

            OutputStream out = httpURLConnection.getOutputStream();
            PrintStream printStream = new PrintStream(out);
            printStream.print(parameter);
            printStream.close();

            out.close();

            int responseCode = httpURLConnection.getResponseCode();
            readSt = convertToString(httpURLConnection.getInputStream());
            Log.d("execute", "URL:" + strings[0]);
            Log.d("execute", "HttpStatusCode:" + responseCode);
            Log.d("execute", "ResponseData:" + readSt);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException io_e) {
            io_e.printStackTrace();
        }
        return null;
    }

    public void onPostExecute(String string) {
        httpURLConnection.disconnect();
        Log.d("AsyncAPIRequest", "onPostExecute:"+string);
    }

    public void setParameter(String mail, String pass, String confirm) {
        ArrayList<String> params = new ArrayList<>();
        params.add("mail" + "=" + mail);
        params.add("pass" + "=" + pass);
        params.add("confirm" + "=" + confirm);

        parameter = "";
        for(String param : params){
            if(!parameter.equals("")) {
                parameter += "&";
            }
            parameter += param;
        }
    }

    public String convertToString(InputStream stream) throws IOException {
        StringBuffer sb = new StringBuffer();
        String line = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        try {
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
