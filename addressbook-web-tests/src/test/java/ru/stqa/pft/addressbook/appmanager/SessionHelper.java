package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SessionHelper {
    private WebDriver wd;

    public SessionHelper(WebDriver wd) {
        this.wd = wd;
    }

    public void login(String user, String password) {
        wd.findElement(By.name("user")).click();
        wd.findElement(By.name("user")).sendKeys(user);
        wd.findElement(By.name("pass")).click();
        wd.findElement(By.name("pass")).sendKeys(password);
        wd.findElement(By.xpath("//input[@value='Login']")).click();
    }
}
