package com.example.oo_raiser.rfidbossapp.Helper;

import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.oo_raiser.rfidbossapp.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class ConnectHelper {

    private String TAG = Resources.getSystem().getString(R.string.app_TAG);

    private final ConnectivityManager connManger;
    private NetworkInfo info;
    private HttpURLConnection connection;

    /** Constructor */
    public ConnectHelper()
    {
        connManger = null;
    }

    public ConnectHelper(ConnectivityManager _connManger)
    {
        connManger = _connManger;
    }

    public ConnectHelper(Context _context)
    {
        connManger = (ConnectivityManager) _context.getSystemService(_context.CONNECTIVITY_SERVICE);
    }

    /** get/post function */

    //get GET Connection
    public HttpURLConnection getGetConnection(String urlStr)
    {
        connection = null;
        info = connManger.getActiveNetworkInfo();

        try{
            //create the POST Request
            URL url = new URL(urlStr);
            connection = (HttpURLConnection)url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type","application/json; UTF-8");

            //connect
            connection.connect();
         }catch (Exception e){
            Log.e(TAG,"[ConnectHelper_HttpURLConnection]: "+e.getMessage());
        }

        return  connection;
    }

    //get Post Connection
    public HttpURLConnection getPostConnection(String urlStr)
    {
        connection = null;
        info = connManger.getActiveNetworkInfo();

        try{
            //create the POST Request
            URL url = new URL(urlStr);
            connection = (HttpURLConnection)url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type","application/json; UTF-8");

            //connect
            connection.connect();


        }catch (Exception e){
            Log.e(TAG,"[ConnectHelper_HttpURLConnection]: "+e.getMessage());
        }

        return  connection;
    }


    //get string data from webApi with GET Request
    public String RequestWebApiGet(String urlStr)
    {
        String dataStr = null;
        info = connManger.getActiveNetworkInfo();

        if(info!=null && info.isConnected())
        {
            try{
                //get connect
                connection = getGetConnection(urlStr);

                if(connection!=null)
                {
                    //read data
                    BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
                    StringBuffer buffer = new StringBuffer();
                    String s = null;
                    while((s = bf.readLine()) != null)
                    {
                        buffer.append(s);
                    }
                    dataStr = buffer.toString();
                }else{
                    dataStr = Util.SERVER_CONNENTFAIL;
                }

            }catch (Exception e){
                dataStr = Util.SERVER_CONNENTFAIL;
                Log.e(TAG,"[ConnectHelper_RequestWebApiGet]: "+e.getMessage());
            }finally {
                connection.disconnect();
            }


        }else{
            dataStr = Util.WIFI_CONNECTFAIL;
        }

        return dataStr;
    }

    public String RequestWebApiPost(String urlStr)
    {
        String dataStr = null;
        info = connManger.getActiveNetworkInfo();

        // Test whether the network function is on
        if(info!=null && info.isConnected())
        {
            try{
                //get connect
                connection = getPostConnection(urlStr);

                //read data
                BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
                StringBuffer buffer = new StringBuffer();
                String s = null;
                while((s = bf.readLine()) != null)
                {
                    buffer.append(s);
                }
                dataStr = buffer.toString();

            }catch (Exception e){
                dataStr = Util.SERVER_CONNENTFAIL;
                Log.e(TAG,"[ConnectHelper_RequestWebApiGet]: "+e.getMessage());
            }finally {
                connection.disconnect();
            }


        }else{
            dataStr = Util.WIFI_CONNECTFAIL;
        }

        return dataStr;

    }


}
