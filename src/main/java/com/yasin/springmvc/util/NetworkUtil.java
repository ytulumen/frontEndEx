package com.yasin.springmvc.util;

import com.yasin.springmvc.service.RoleServiceView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
@Component
public class NetworkUtil {

    @Autowired
    NetworkUtil self;
    private String username;
    private String password;
    public String networkService(String halfUrl, String method, String send) {
        String lastUrl = "http://localhost:8080" + halfUrl;
        String value = "Basic bXktdHJ1c3RlZC1jbGllbnQ6c2VjcmV0";
        StringBuffer response = new StringBuffer("");
        try {
            boolean isOauth = lastUrl.contains("oauth/token");
            if(!isOauth) {

                lastUrl += "?access_token=" + self.getByName(username, password);
            }
            URL obj = new URL(lastUrl);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            // optional default is GET
            if(isOauth) {
                con.setRequestProperty("Authorization", value);
            }
            con.setRequestMethod(method);
            //add request header
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            if(method.equals("POST") || method.equals("PUT")) {
                con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
                con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                con.setRequestProperty("Accept", "application/json");
                con.setRequestProperty("Content-Length", "" + String.valueOf(send.getBytes().length));
                con.setDoOutput(true);

                DataOutputStream daos = new DataOutputStream(con.getOutputStream());
                daos.writeBytes(send);
                daos.flush();
                daos.close();
            }
            int responseCode = con.getResponseCode();
            System.out.println("\n" + halfUrl + "___Sending '"+ method + "' request to URL : " + lastUrl);
            System.out.println("Response Code : " + responseCode + "SEND " + send);


            if(isOauth || method.equals("GET")) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                System.out.println(response.toString());
                if(isOauth) {
                    return response.substring(response.indexOf("access_token")+15,
                            response.indexOf("\"", response.indexOf("access_token")+16));

                }
                in.close();
            }
        }catch (java.net.MalformedURLException e){
            e.printStackTrace();
        }catch (ProtocolException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return response.toString();
    }
    @Cacheable("models")
    public String getByName(String name, String password) {
        this.username = name;
        this.password = password;
        String url = "/oauth/token?grant_type=password&username="+
                name + "&password=" + password;
        StringBuffer response = new StringBuffer(self.networkService(url,"POST", ""));
        return response.toString();
    }
}
