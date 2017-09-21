package com.yasin.springmvc.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yasin.model.UserRoles;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service("userroleService")
public class UserRolesServiceImpl implements UserRolesService{

    private static List<UserRoles> userRoles;

    @Override
    public List<UserRoles> findAllUserRoles() {
        findAll();
        return userRoles;
    }

    @Override
    public UserRoles findById(long id) {
        findAll();
        for (UserRoles userRoles1: userRoles) {
            if(userRoles1.getId()==id)
                return userRoles1;
        }
        return null;

    }

    @Override
    public UserRoles findByName(String name) {
        findAll();
        for (UserRoles userRoles1: userRoles){
            if(userRoles1.getName().equalsIgnoreCase(name)){
                return userRoles1;
            }
        }
        return null;
    }

    @Override
    public void saveUserRoles(UserRoles userRoles) {
        String url = "http://localhost:8080/workerMng/userrole";
        Gson gson = new Gson();
        String send = gson.toJson(userRoles);
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod("POST");
            //add request header
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Content-Length", "" + String.valueOf(send.getBytes().length));
            con.setDoOutput(true);

            DataOutputStream daos = new DataOutputStream(con.getOutputStream());
            daos.writeBytes(send);
            daos.flush();
            daos.close();
            int responseCode = con.getResponseCode();
            System.out.println("\nSAVE USER___Sending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode + "SEND " + send);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            in.close();
        }catch (java.net.MalformedURLException e){
            e.printStackTrace();
        }catch (ProtocolException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUserRolesById(long id) {
        String url = "http://localhost:8080/workerMng/userrole/id="+id;
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod("DELETE");

            //add request header
            con.setRequestProperty("Roles-Agent", "Mozilla/5.0");

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);
        }catch (java.net.MalformedURLException e){
            e.printStackTrace();
        }catch (ProtocolException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAllUserRoles() {

    }

    @Override
    public boolean isUserRolesExist(UserRoles userRoles) {
        return false;
    }

    @Override
    public void findAll() {
        String url = "http://localhost:8080/workerMng/userrole";
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod("GET");

            //add request header
            con.setRequestProperty("Roles-Agent", "Mozilla/5.0");

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();
            System.out.println("USERROLE FIND ALL" + response);
            Gson gson = new Gson();
            System.out.println(response.toString());

            userRoles = gson.fromJson(response.toString(), new TypeToken<ArrayList<UserRoles>>() {}.getType());
            for (int i = 0; i < userRoles.size(); i++) {
                System.out.println(userRoles.get(i).toString());
            }
        }catch (java.net.MalformedURLException e){
            e.printStackTrace();
        }catch (ProtocolException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
