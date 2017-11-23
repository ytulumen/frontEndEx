package com.yasin.webdriver;

import com.google.gson.Gson;
import com.yasin.model.User;
import com.yasin.springmvc.service.UserServiceView;
import com.yasin.springmvc.service.impl.UserServiceViewImpl;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class UserTest {

    private WebDriver driver;

    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "chromedriver");
        driver = new ChromeDriver();
        driver.navigate().to("http://localhost:8081/userpage.html");
        if(driver.getCurrentUrl().contains("login")){
            driver.findElement(By.id("username")).sendKeys("yasin");
            driver.findElement(By.id("password")).sendKeys("tlmn");
            driver.findElement(By.id("submit")).click();
        }

    }

    @Test
    public void addUser(){
        if(driver.getCurrentUrl().contains("userpage")){
            driver.findElement(By.id("name")).sendKeys("testAddUserName");
            driver.findElement(By.id("password")).sendKeys("testAddUserPass");
            driver.findElement(By.id("addButton")).click();
        }


    }
    @Test
    public void editUser() {

        createUser();
        if (driver.getPageSource().contains("editUser")) {
            driver.findElement(By.xpath("/html/body/div/div[2]/div[2]/table/tbody/tr[5]/td[6]/button[1]")).click();
            driver.findElement(By.id("name")).clear();
            driver.findElement(By.id("name")).sendKeys("newTestEditUser");
            driver.findElement(By.id("password")).clear();
            driver.findElement(By.id("password")).sendKeys("newTestEditPass");
            driver.findElement(By.id("addButton")).click();
        }
    }
    @Test
    public void deleteUser(){

        createUser();
        if (driver.getPageSource().contains("removeUser")) {
            driver.findElement(By.xpath("/html/body/div/div[2]/div[2]/table/tbody/tr[5]/td[6]/button[2]")).click();
        }
    }

    private void createUser(){
        String oauthUrl = "http://localhost:8080/oauth/token?grant_type=password&username=yasin&password=tlmn";
        String lastUrl = "http://localhost:8080/rest/user";
        String value = "Basic bXktdHJ1c3RlZC1jbGllbnQ6c2VjcmV0";
        StringBuffer response;
        User user = new User();
        user.setName("testUserName");
        user.setPassword("testUserPass");
        String send = new Gson().toJson(user);
        try {
            URL obj = new URL(oauthUrl);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestProperty("Authorization", value);
            con.setRequestMethod("POST");
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
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            System.out.println(response.toString());
            String token = response.substring(response.indexOf("access_token")+15,
                    response.indexOf("\"", response.indexOf("access_token")+16));
            in.close();

            lastUrl += "?access_token=" + token;
            obj = new URL(lastUrl);
            con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            //add request header
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Content-Length", "" + String.valueOf(send.getBytes().length));
            con.setDoOutput(true);

            daos = new DataOutputStream(con.getOutputStream());
            daos.writeBytes(send);
            daos.flush();
            daos.close();
            responseCode = con.getResponseCode();

        }catch (java.net.MalformedURLException e){
            e.printStackTrace();
        }catch (ProtocolException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
