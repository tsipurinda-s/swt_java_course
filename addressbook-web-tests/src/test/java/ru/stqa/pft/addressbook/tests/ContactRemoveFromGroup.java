package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.NoSuchElementException;

public class ContactRemoveFromGroup extends TestBase{

    @BeforeMethod
    public void ensurePreconditions() {

        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("name 1").withHeader("header 1").withFooter("footer 1"));
            app.goTo().homePage();
        }

        if (app.db().contacts().size() == 0) {
            app.goTo().contactPage();
            app.contact().create(new ContactData()
                    .withFirstname("Иван").withLastname("Петров").withMobilePhone("81234567890")
                    .withEmail("petrov.i@mail.com").withPhoto("src/test/resources/ava.png").inGroup(app.db().groups().iterator().next()), true);
        }
    }

    @Test
    public void testRemoveContactFromGroup() {
        ContactData before;

        try {
            before = app.db().contacts().stream().filter((c) -> c.getGroups().size() != 0).findFirst().get();
        } catch (NoSuchElementException e) {
            before = prepareContactForRemoveFromGroup(app.db().contacts().iterator().next());
        }

        GroupData groupForRemove = before.getGroups().iterator().next();
        app.contact().removeFromGroup(before, groupForRemove);
        ContactData finalBefore = before;
        ContactData after = app.db().contacts().stream().filter((c) -> c.getId() == finalBefore.getId()).findFirst().get();

        assert(!after.getGroups().contains(groupForRemove));
    }

    public ContactData prepareContactForRemoveFromGroup(ContactData contact) {
        app.contact().addContactToGroup(contact, app.db().groups().iterator().next());
        app.goTo().homePage();
        return app.db().contacts().stream().filter((c) -> c.getId() == contact.getId()).findFirst().get();
    }
}
