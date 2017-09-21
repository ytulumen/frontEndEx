package com.yasin.springmvc.service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.google.gson.reflect.TypeToken;
import com.yasin.model.*;
import com.google.gson.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Service("userService")
@Component
public class UserServiceImpl implements UserService, UserDetailsService {

    private static final AtomicLong counter = new AtomicLong();

    private static List<User> users;

    static{
        //users= populateDummyUsers();
    }

    public List<User> findAllUsers() {
        findAll();
        return users;
    }

    public User findById(long id)  {
        findAll();
        for (User user: users) {
            if(user.getId()==id)
                return user;
        }
        return null;
    }

    public User findByName(String name) {
        findAll();
        for(User user : users){
            if(user.getName().equalsIgnoreCase(name)){
                return user;
            }
        }
        return null;
    }

    public void saveUser(User user) {
        String url = "http://localhost:8080/workerMng/user";
        Gson gson = new Gson();
        String send = gson.toJson(user);
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



    public void updateUser(User user) {
        String url = "http://localhost:8080/workerMng/user";
        Gson gson = new Gson();
        String send = gson.toJson(user);
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

    public void deleteUserById(long id) {
        String url = "http://localhost:8080/workerMng/user/id="+id;
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod("DELETE");

            //add request header
            con.setRequestProperty("User-Agent", "Mozilla/5.0");

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

    public boolean isUserExist(User user) {
        return findByName(user.getName())!=null;
    }

    public void deleteAllUsers(){
        users.clear();
    }
/*
    private static List<User> populateDummyUsers(){
        List<User> users = new ArrayList<User>();
        users.add(new User(counter.incrementAndGet(),"Sam", "NY", "sam@abc.com"));
        users.add(new User(counter.incrementAndGet(),"Tomy", "ALBAMA", "tomy@abc.com"));
        users.add(new User(counter.incrementAndGet(),"Kelly", "NEBRASKA", "kelly@abc.com"));
        return users;
    }*/
    @Override
    public void findAll() {

        String url = "http://localhost:8080/workerMng/user";
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod("GET");

            //add request header
            con.setRequestProperty("User-Agent", "Mozilla/5.0");

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

            users = gson.fromJson(response.toString(), new TypeToken<ArrayList<User>>() {}.getType());
            for (int i = 0; i < users.size(); i++) {
                System.out.println(users.get(i).toString());
            }
        }catch (java.net.MalformedURLException e){
            e.printStackTrace();
        }catch (ProtocolException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = findByName(s);
        GrantedAuthority authority = new SimpleGrantedAuthority("user");
        UserDetails userDetails = (UserDetails)new org.springframework.security.core.userdetails.User(user.getName(),
                user.getPassword(), Arrays.asList(authority));
        return userDetails;
    }
}