package com.yasin.webdriver;

import com.google.gson.Gson;
import com.yasin.model.Roles;
import com.yasin.model.User;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class RoleTest {
    WebDriver driver;

    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "chromedriver");
        driver = new ChromeDriver();
        driver.navigate().to("http://localhost:8081/rolepage.html");
        if(driver.getCurrentUrl().contains("login")){
            driver.findElement(By.id("username")).sendKeys("yasin");
            driver.findElement(By.id("password")).sendKeys("tlmn");
            driver.findElement(By.id("submit")).click();
        }

    }

    @Test
    public void addUser(){
        if(driver.getCurrentUrl().contains("rolepage")){
            driver.findElement(By.id("name")).sendKeys("testRole");
            driver.findElement(By.id("addButton")).click();
        }
    }

    @Test
    public void editUser() {
        createRole();
        if (driver.getPageSource().contains("editRole")) {
            driver.findElement(By.xpath("/html/body/div/div[2]/div[2]/table/tbody/tr[5]/td[5]/button[1]")).click();
            driver.findElement(By.id("name")).clear();
            driver.findElement(By.id("name")).sendKeys("newTestEditRole");
            driver.findElement(By.id("addButton")).click();
        }
    }
    @Test
    public void deleteUser(){
        createRole();
        if (driver.getPageSource().contains("removeRole")) {
            driver.findElement(By.xpath("/html/body/div/div[2]/div[2]/table/tbody/tr[5]/td[5]/button[2]")).click();
        }
    }
    private void createRole(){
        String oauthUrl = "http://localhost:8080/oauth/token?grant_type=password&username=yasin&password=tlmn";
        String lastUrl = "http://localhost:8080/rest/role";
        String value = "Basic bXktdHJ1c3RlZC1jbGllbnQ6c2VjcmV0";
        StringBuffer response;
        Roles roles = new Roles();
        roles.setName("testRoleName");
        String send = new Gson().toJson(roles);
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
