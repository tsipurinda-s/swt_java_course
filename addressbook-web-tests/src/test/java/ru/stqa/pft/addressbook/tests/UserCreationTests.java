package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class UserCreationTests extends TestBase{

  @Test
  public void testUserCreation() {
    app.getNavigationHelper().goToUserPage();
    app.getContactHelper().fillContactForm(new ContactData("Иван", "Иванов", "81234567890", "ivanov.i@mail.com"));
    app.getContactHelper().submitContactCreation();
    app.getContactHelper().returnToHomePage();
  }

}
