package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

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
        type(By.name("home"), contactData.getHomePhone());
        type(By.name("mobile"), contactData.getMobilePhone());
        type(By.name("work"), contactData.getWorkPhone());
        type(By.name("email"), contactData.getEmail());
        type(By.name("email2"), contactData.getEmail2());
        type(By.name("email3"), contactData.getEmail3());
        attach(By.name("photo"), contactData.getPhoto());

        if (creation) {
                if (contactData.getGroups().size() > 0) {
                    Assert.assertTrue(contactData.getGroups().size() == 1);
                    new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroups().iterator().next().getName());
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

    public void selectContactById(int id) {
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
            String allEmails = cells.get(4).getText();
            contacts.add(new ContactData().withId(id).withFirstname(firstname).withLastname(lastname)
                    .withAllPhones(allPhones).withAllEmails(allEmails));
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
        String homePhone = wd.findElement(By.name("home")).getAttribute("value");
        String mobilePhone = wd.findElement(By.name("mobile")).getAttribute("value");
        String workPhone = wd.findElement(By.name("work")).getAttribute("value");
        String email = wd.findElement(By.name("email")).getAttribute("value");
        String email2 = wd.findElement(By.name("email2")).getAttribute("value");
        String email3 = wd.findElement(By.name("email3")).getAttribute("value");
        wd.navigate().back();
        return new ContactData().withId(contact.getId()).withFirstname(firstname).withLastname(lastname)
                .withHomePhone(homePhone).withMobilePhone(mobilePhone).withWorkPhone(workPhone)
                .withEmail(email).withEmail2(email2).withEmail3(email3);
    }


    public void addContactToGroup(ContactData before, GroupData groupForAdding) {
        selectContactById(before.getId());
        selectGroupForAdding(groupForAdding.getId());
        submitAddingContactsToGroup();
        returnToGroup(groupForAdding);
    }

    private void returnToGroup(GroupData groupForAdding) {
        wd.findElement(By.linkText("group page \"" + groupForAdding.getName() + "\"")).click();
    }

    private void submitAddingContactsToGroup() {
        wd.findElement(By.name("add")).click();
    }

    private void selectGroupForAdding(int id) {
        new Select(wd.findElement(By.name("to_group"))).selectByValue(String.valueOf(id));
    }


    public void removeFromGroup(ContactData contact, GroupData groupForRemove) {
        selectGroupForRemove(groupForRemove.getId());
        selectContactById(contact.getId());
        submitRemovalContactsToGroup();
        returnToGroup(groupForRemove);
    }

    private void submitRemovalContactsToGroup() {
        wd.findElement(By.name("remove")).click();
    }

    private void selectGroupForRemove(int id) {
        new Select(wd.findElement(By.name("group"))).selectByValue(String.valueOf(id));
    }
}
