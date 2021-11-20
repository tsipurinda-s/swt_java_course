package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.NoSuchElementException;

public class ContactAddToGroup extends TestBase{

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().contacts().size() == 0) {
            app.goTo().contactPage();
            app.contact().create(new ContactData()
                    .withFirstname("Иван").withLastname("Петров").withMobilePhone("81234567890")
                    .withEmail("petrov.i@mail.com").withPhoto("src/test/resources/ava.png"), true);
        }

        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("name 1").withHeader("header 1").withFooter("footer 1"));
            app.goTo().homePage();
        }

    }

    @Test
    public void testAddContactToGroup() {
        GroupData groupForAdding = app.db().groups().iterator().next();
        ContactData before;
        try {
            before = app.db().contacts().stream().filter((c) -> !c.getGroups().contains(groupForAdding)).findFirst().get();
        }
        catch (NoSuchElementException e) {
            app.goTo().contactPage();
            before = prepareContact().iterator().next();
        }

        app.contact().addContactToGroup(before, groupForAdding);

        ContactData finalBefore = before;
        ContactData after = app.db().contacts().stream().filter((c) -> c.getId() == finalBefore.getId()).findFirst().get();

        assert(after.getGroups().contains(groupForAdding));
    }

    public Contacts prepareContact() {
        Contacts before = app.db().contacts();
        app.goTo().contactPage();
        app.contact().create(new ContactData()
                .withFirstname("Иван").withLastname("Петров").withMobilePhone("81234567890")
                .withEmail("petrov.i@mail.com").withPhoto("src/test/resources/ava.png"), true);
        Contacts after = app.db().contacts();
        after.removeAll(before);
        return after;
    }



}
