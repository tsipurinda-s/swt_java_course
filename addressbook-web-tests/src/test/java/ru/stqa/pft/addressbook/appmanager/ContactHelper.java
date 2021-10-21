package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.List;

public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void submitContactCreation() {
        click(By.name("submit"));
    }

    public void fillContactForm(ContactData contactData, boolean creation) {
        type(By.name("firstname"), contactData.getFirstname());
        type(By.name("lastname"), contactData.getLastname());
        type(By.name("mobile"), contactData.getMobilePhone());
        type(By.name("email"), contactData.getEmail());

        if (creation) {
            if (contactData.getGroup() != null) {
                new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
            }
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }

    public void returnToHomePage() {
        wd.findElement(By.linkText("home page")).click();
    }

    public void initContactModificationById(int id) {
        wd.findElement(By.cssSelector("a[href='edit.php?id=" + id + "']")).click();
    }

    public void submitContactModification() {
        click(By.name("update"));
    }

    private void selectContactById(int id) {
        wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }


    public void create(ContactData contact, boolean creation) {
        fillContactForm(contact, creation);
        submitContactCreation();
        returnToHomePage();
    }

    public void modify(ContactData contact) {
        initContactModificationById(contact.getId());
        fillContactForm(contact, false);
        submitContactModification();
        returnToHomePage();
    }

    public void deleteSelectedContacts(ContactData contact) {
        selectContactById(contact.getId());
        click(By.cssSelector("input[value=Delete]"));

        wd.switchTo().alert().accept();
        wd.findElement(By.cssSelector("div.msgbox"));
    }

    public Contacts all() {
        Contacts contacts = new Contacts();
        List<WebElement> rows = wd.findElements(By.cssSelector("table tr[name='entry']"));
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            int id = Integer.parseInt(cells.get(0).findElement(By.tagName("input")).getAttribute("id"));
            String lastname = cells.get(1).getText();
            String firstname = cells.get(2).getText();
            String allPhones = cells.get(5).getText();
            contacts.add(new ContactData().withId(id).withFirstname(firstname).withLastname(lastname)
                    .withAllPhones(allPhones));
        }
        return contacts;
    }

    public ContactData getContactInfoFromEditForm(ContactData contact) {
        return infoFromEditForm(contact);
    }

    public ContactData infoFromEditForm(ContactData contact) {
        initContactModificationById(contact.getId());
        String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
        String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
        String home = wd.findElement(By.name("home")).getAttribute("value");
        String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
        String work = wd.findElement(By.name("work")).getAttribute("value");
        wd.navigate().back();
        return new ContactData().withId(contact.getId()).withFirstname(firstname).withLastname(lastname)
                .withHomePhone(home).withMobilePhone(mobile).withWorkPhone(work);
    }
}
