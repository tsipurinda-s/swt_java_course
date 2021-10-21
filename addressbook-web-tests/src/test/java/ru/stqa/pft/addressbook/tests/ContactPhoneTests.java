package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactPhoneTests extends TestBase{

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.contact().all().size() == 0) {
            app.goTo().contactPage();
            app.contact().create(new ContactData()
                    .withFirstname("Иван").withLastname("Петров").withMobilePhone("81234567890").withEmail("petrov.i@mail.com").withGroup("test1")
                    .withHomePhone("111").withMobilePhone("222").withWorkPhone("333"), true);
        }
    }

    @Test
    public void testContactPhones() {
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().getContactInfoFromEditForm(contact);
        Assert.assertEquals(contact.getHomePhone(), cleaned(contactInfoFromEditForm.getHomePhone()));
        Assert.assertEquals(contact.getMobilePhone(), cleaned(contactInfoFromEditForm.getMobilePhone()));
        Assert.assertEquals(contact.getWorkPhone(), cleaned(contactInfoFromEditForm.getWorkPhone()));
    }

    public String cleaned(String phone) {
        return phone.replaceAll("\\s","").replaceAll("[-()]","");
    }

}
