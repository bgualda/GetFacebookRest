package br.com.gualda.soa.spring.facebook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;


public class FacebookImp implements IFacebook {
    public FacebookImp() {
        super();
    }

    public String[] InterFace(String accessToken) {
        return extractFacebookFromJSON(facebookConnection(accessToken));
    }
    
    private static String facebookService = "https://graph.facebook.com/me?access_token=";
    
    private String[] extractFacebookFromJSON(String response) {
        
        String facebook[] = new String[2];
        
        if (response != null) {
            final JSONObject jsonObj = (JSONObject)JSONValue.parse(response);
        
            final String id = (String)jsonObj.get("id");
            facebook[0] = id;
            
            final String first_name = (String)jsonObj.get("first_name");                        
            facebook[1] = first_name;
       }
        
        return facebook;
    }
    
    private static String facebookConnection(String token) {
        HttpURLConnection connection = null;
        OutputStream wr = null;
        BufferedReader rd = null;
        StringBuilder sb = null;
        String line = null;
        URL serverAddress = null;

        try {
            serverAddress = new URL(facebookService + token);
            
            connection = null;
            
            //Set conexão inicial
            connection = (HttpURLConnection)serverAddress.openConnection();

            connection.setRequestMethod("GET");
            
            connection.setDoOutput(true);
            connection.setReadTimeout(10000);
            
            connection.connect();
            
            //Efetua a leitura para o servidor.
            rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            sb = new StringBuilder();
            
            while ((line = rd.readLine()) != null) {
                sb.append(line + '\n');
            }
            
            return (sb.toString());       
        } catch (MalformedURLException e) {
            e.printStackTrace();
            } catch (IOException e) {
                    e.printStackTrace();
                    } finally {
                        //close the connection, set all objects to null
                        connection.disconnect();
                        rd = null;
                        sb = null;
                        wr = null;
                        connection = null;
                    }
            return null;
    }                                             
}
