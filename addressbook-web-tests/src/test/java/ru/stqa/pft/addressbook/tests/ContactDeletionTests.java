package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactDeletionTests extends TestBase{
    @Test
    public void testContactDeletion(){
        if (! app.getContactHelper().isThereAContact()) {
            app.getNavigationHelper().goToContactPage();
            app.getContactHelper().createContact(new ContactData("Иван", "Иванов", "81234567890", "ivanov.i@mail.com", "test1"), true);
        }
        app.getContactHelper().deleteSelectedContacts();
    }
}
