package com.yasin.webdriver;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class UserRoleTest {
    private WebDriver driver;


    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "chromedriver");
        driver = new ChromeDriver();
        driver.navigate().to("http://localhost:8081/userrolepage.html");
        if(driver.getCurrentUrl().contains("login")){
            driver.findElement(By.id("username")).sendKeys("yasin");
            driver.findElement(By.id("password")).sendKeys("tlmn");
            driver.findElement(By.id("submit")).click();
        }

    }

    @Test
    public void addUserRole(){
        if(driver.getCurrentUrl().contains("userrolepage")){
            driver.findElement(By.id("name")).sendKeys("testUserrole");
            driver.findElement(By.id("username")).sendKeys("yasin");
            driver.findElement(By.id("rolename")).sendKeys("sabuk");
            driver.findElement(By.id("addButton")).click();
        }


    }
    @Test
    public void deleteUser(){
        if (driver.getPageSource().contains("removeUser")) {
            driver.findElement(By.xpath("/html/body/div/div[2]/div[2]/table/tbody/tr[1]/td[7]/button")).click();

        }
    }
}
