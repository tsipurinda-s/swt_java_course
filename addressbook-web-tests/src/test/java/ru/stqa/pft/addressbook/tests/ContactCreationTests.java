package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase{

  @Test(enabled = false)
  public void testContactCreation() {
    List<ContactData> before = app.getContactHelper().getContactList();
    app.goTo().goToContactPage();
    ContactData contact = new ContactData("Иван", "Иванов", null, null,null);
    app.getContactHelper().createContact(contact, true);
    List<ContactData> after = app.getContactHelper().getContactList();

    before.add(contact);
    Comparator<? super ContactData> byLastname = (c1, c2) -> String.CASE_INSENSITIVE_ORDER.compare(c1.getLastname(), c2.getLastname());
    before.sort(byLastname);
    after.sort(byLastname);
    Assert.assertEquals(before, after);
  }

}
