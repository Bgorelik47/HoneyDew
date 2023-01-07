package com.pace.honeydew;
import java.io.*;
import java.net.*;

//Figure out what to do with this class
public class OurNewsApiHandler {
//    public static void main(String[] args) throws IOException {
//        String[][] requestParam = {{"q", "java"}, {"apiKey", "00a8b39145f54269a321b78fad3d22c1"}};
//
//        formatUrlString(requestParam);
//        PingUrl(formatUrlString(requestParam));
//    }
    public static String formatUrlString(String[][] a) throws IOException {
        //array of key/value pair arrays

        String urlText = "https://newsapi.org/v2/everything?";


        for(int i= 0; i<a.length; i++) {
            String key = a[i][0];
            String value = a[i][1];
            urlText += key + "=" + value;
            if(i < a.length - 1) {
                urlText+="&";
            }
        }
        return urlText;

    }


    public static void PingUrl(String formattedUrl) throws IOException {
        URL url = null;
        HttpURLConnection con = null;
        System.out.println("inside ping");
        System.out.println(formattedUrl);
        try {
            url = new URL(formattedUrl);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        int status = con.getResponseCode();
        InputStreamReader in = new InputStreamReader(con.getInputStream());
        BufferedReader bf = new BufferedReader(in);
        String bfOutput = "";
        StringBuffer content = new StringBuffer();

        while((bfOutput = bf.readLine()) != null) {
            content.append(bfOutput);
            System.out.println("inside while loop");

        }
        in.close();
        con.disconnect();
        System.out.println(content);
    }



}
