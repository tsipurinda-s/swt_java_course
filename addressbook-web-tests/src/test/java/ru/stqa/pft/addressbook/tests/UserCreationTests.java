package ru.stqa.pft.addressbook.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.concurrent.TimeUnit;

public class UserCreationTests extends TestBase{

  @Test
  public void testUserCreation() {
    app.goToUserPage();
    app.fillContactForm(new ContactData("Иван", "Иванов", "81234567890", "ivanov.i@mail.com"));
    app.submitContactCreation();
    app.returnToHomePage();
  }

}
