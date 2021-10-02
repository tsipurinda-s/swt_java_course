package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver wd) {
        super(wd);
    }
    public void submitContactCreation() {
        click(By.name("submit"));
    }

    public void fillContactForm(ContactData contactData) {
        type(By.name("firstname"),contactData.firstname());
        type(By.name("lastname"),contactData.lastname());
        type(By.name("mobile"),contactData.mobile());
        type(By.name("email"),contactData.email());
    }

    public void returnToHomePage() {
        wd.findElement(By.linkText("home page")).click();
    }

    public void initContactModification() {
        click(By.cssSelector("img[alt='Edit']"));
    }

    public void submitContactModification() {
        click(By.name("update"));
    }

    public void deleteSelectedContacts() {
        click(By.name("selected[]"));
        click(By.cssSelector("input[value=Delete]"));
        wd.switchTo().alert().accept();
    }
}
