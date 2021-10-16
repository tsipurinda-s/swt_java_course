package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase {
    @Test
    public void testContactModification(){
        if (! app.getContactHelper().isThereAContact()) {
            app.getNavigationHelper().goToContactPage();
            app.getContactHelper().createContact(new ContactData("Иван", "Петров", "81234567890", "petrov.i@mail.com", "test1"), true);
        }
        List<ContactData> before = app.getContactHelper().getContactList();
        app.getContactHelper().initContactModification();
        ContactData contact = new ContactData("Иван", "Отредактированный", null, null, null);
        app.getContactHelper().fillContactForm(contact, false);
        app.getContactHelper().submitContactModification();
        app.getContactHelper().returnToHomePage();
        List<ContactData> after = app.getContactHelper().getContactList();

        before.remove(0);
        before.add(contact);
        Comparator<? super ContactData> byLastname = (c1, c2) -> String.CASE_INSENSITIVE_ORDER.compare(c1.getLastname(), c2.getLastname());
        before.sort(byLastname);
        after.sort(byLastname);
        Assert.assertEquals(before, after);
    }

}
