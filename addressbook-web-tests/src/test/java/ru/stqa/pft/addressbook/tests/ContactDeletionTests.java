package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactDeletionTests extends TestBase{
    @Test(enabled = false)
    public void testContactDeletion(){
        List<ContactData> before = app.getContactHelper().getContactList();
        if (! app.getContactHelper().isThereAContact()) {
            app.goTo().goToContactPage();
            app.getContactHelper().createContact(new ContactData("Иван", "Иванов", "81234567890", "ivanov.i@mail.com", "test1"), true);
        }
        app.getContactHelper().deleteSelectedContacts();
        List<ContactData> after =app.getContactHelper().getContactList();

        before.remove(0);
        Assert.assertEquals(before, after);

    }
}
