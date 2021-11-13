package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

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
        ContactData before = app.db().contacts().iterator().next();
        GroupData groupForAdding = app.db().groups().iterator().next();

        app.contact().addContactToGroup(before, groupForAdding);

        ContactData after = app.db().contacts().iterator().next();

        assertThat(before.inGroup(groupForAdding).getGroups(), equalTo(after.getGroups()));
    }


}
