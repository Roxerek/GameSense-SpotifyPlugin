package pl.roxerek.splugin;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ActionsHandler {

    private final CorePropsManager corePropsManager;

    public ActionsHandler(CorePropsManager corePropsManager){
        this.corePropsManager = corePropsManager;
    }

    public void sendGameEvent(String json){
        try {
            HttpURLConnection httpURLConnection = initConnection("http://localhost:" + corePropsManager.getPort() + "/game_event");
            if(httpURLConnection != null){
                OutputStream outputStream = httpURLConnection.getOutputStream();
                byte[] out = json.getBytes(StandardCharsets.UTF_8);
                outputStream.write(out);
                System.out.println("[INFO] Site responded with code " + httpURLConnection.getResponseCode());
                httpURLConnection.disconnect();
                System.out.println("[INFO] Bytes are send");

            }
        }catch(Exception e){
            e.printStackTrace();
            System.err.println("[ERROR] Connection is not opened!");
        }
    }

    public boolean registerGameEvent(){
        String json = "{\"game\": \"SPOTYPLUG\",\"event\": \"UPDATE_SONG\",\"value_optional\": true,\"handlers\": [{\"device-type\": \"screened\",\"mode\": \"screen\",\"zone\": \"one\",\"datas\": [{\"lines\": [{\"has-text\": true,\"context-frame-key\": \"first-line\"},{\"has-text\": true,\"context-frame-key\": \"second-line\"}],\"length-millis\": 0}]}]}";
        try {
            HttpURLConnection httpURLConnection = initConnection("http://localhost:" + corePropsManager.getPort() + "/bind_game_event");
            if(httpURLConnection != null){
                OutputStream outputStream = httpURLConnection.getOutputStream();
                byte[] out = json.getBytes(StandardCharsets.UTF_8);
                outputStream.write(out);
                System.out.println("[INFO] Site responded with code " + httpURLConnection.getResponseCode());
                httpURLConnection.disconnect();
                System.out.println("[INFO] Bytes are send (Event has been registered)");
            }
        }catch(Exception e){
            e.printStackTrace();
            System.err.println("[ERROR] Connection is not opened!");
        }
        return true;
    };

    private HttpURLConnection initConnection(String targetUrl){
        try {
            URL url = new URL(targetUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);

            httpURLConnection.setRequestProperty("Content-Type","application/json");
            httpURLConnection.setRequestProperty("Accept", "application/json");

            return httpURLConnection;

        }catch (Exception e){
            e.printStackTrace();
            System.err.println("[ERROR] Can't connect with URL");
        }
        return null;
    }

}
