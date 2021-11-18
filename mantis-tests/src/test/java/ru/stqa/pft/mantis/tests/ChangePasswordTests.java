package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.appmanager.LinkHelper;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserData;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.AssertJUnit.assertTrue;

public class ChangePasswordTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        String adminLogin = app.getProperty("web.adminLogin");
        String adminPassword = app.getProperty("web.adminPassword");
        app.authorization().login(adminLogin, adminPassword);
        app.james().initTelnetSession();
    }

    @Test
    public void testChangePassword() throws MessagingException, IOException {
        app.goTo().manageUserPage();

        String email = "test@localhost";
        String mailUser = "test";
        String mailPass = "test";
        UserData user = app.db().users().stream().filter((u) -> u.getEmail().equals(email)).findFirst().get();

        app.user().openUser(user).resetPassword();

        List<MailMessage> mailMessages = app.james().waitForMail(mailUser, mailPass,60000);

        String setLink = LinkHelper.findConfirmationLink(mailMessages, email);

        long now = System.currentTimeMillis();
        String newPass = String.format("test%s", now);

        app.setPassword().openPage(setLink).set(newPass);
        assertTrue(app.newSession().login(user.getUsername(), newPass));
    }

    @AfterMethod
    public void stopJames() {
        app.james().closeTelnetSession();
    }

}
