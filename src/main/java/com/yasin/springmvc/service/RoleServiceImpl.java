package com.yasin.springmvc.service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.google.gson.reflect.TypeToken;
import com.yasin.model.*;
import com.google.gson.*;
import org.springframework.stereotype.Service;


@Service("roleService")
public class RoleServiceImpl implements RoleService{

    private static final AtomicLong counter = new AtomicLong();

    private static List<Roles> roles;

    static{
        //roles= populateDummyRoless();
    }

    public List<Roles> findAllRoles() {
        findAll();
        return roles;
    }

    public Roles findById(long id)  {
        findAll();
        for (Roles role: roles) {
            if(role.getId()==id)
                return role;
        }
        return null;
    }

    public Roles findByName(String name) {
        findAll();
        for(Roles role : roles){
            if(role.getName().equalsIgnoreCase(name)){
                return role;
            }
        }
        return null;
    }
    public void saveRoles(Roles role) {
        String url = "http://localhost:8080/rest/role";
        Gson gson = new Gson();
        String send = gson.toJson(role);
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
            System.out.println("\nSAVE USER___Sending 'POST' request to URL : " + url);
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

    public void updateRoles(Roles role) {
        String url = "http://localhost:8080/rest/role";
        Gson gson = new Gson();
        String send = gson.toJson(role);
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod("PUT");
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
            System.out.println("\nSAVE USER___Sending 'PUT' request to URL : " + url);
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

    public void deleteRolesById(long id) {
        String url = "http://localhost:8080/rest/role/id="+id;
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod("DELETE");

            //add request header
            con.setRequestProperty("User-Agent", "Mozilla/5.0");

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'DELETE' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);
        }catch (java.net.MalformedURLException e){
            e.printStackTrace();
        }catch (ProtocolException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public boolean isRolesExist(Roles role) {
        return findByName(role.getName())!=null;
    }

    public void deleteAllRoles(){
        roles.clear();
    }
    /*
        private static List<Roles> populateDummyRoless(){
            List<Roles> roles = new ArrayList<Roles>();
            roles.add(new Roles(counter.incrementAndGet(),"Sam", "NY", "sam@abc.com"));
            roles.add(new Roles(counter.incrementAndGet(),"Tomy", "ALBAMA", "tomy@abc.com"));
            roles.add(new Roles(counter.incrementAndGet(),"Kelly", "NEBRASKA", "kelly@abc.com"));
            return roles;
        }*/
    @Override
    public void findAll() {

        String url = "http://localhost:8080/rest/role";
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
            Gson gson = new Gson();
            System.out.println(response.toString());

            roles = gson.fromJson(response.toString(), new TypeToken<ArrayList<Roles>>() {}.getType());
            for (int i = 0; i < roles.size(); i++) {
                System.out.println(roles.get(i).toString());
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