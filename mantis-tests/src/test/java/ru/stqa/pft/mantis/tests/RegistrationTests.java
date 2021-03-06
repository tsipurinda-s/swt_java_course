package ru.stqa.pft.mantis.tests;


import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.AssertJUnit.assertTrue;
import static ru.stqa.pft.mantis.appmanager.LinkHelper.findConfirmationLink;

public class RegistrationTests extends TestBase {

    //@BeforeMethod
    public void startMailServer() {
        app.mail().start();
    }

    @Test
    public void testRegistration() throws IOException, MessagingException {
        long now = System.currentTimeMillis();
        String user = String.format("user%s", now);
        String password = String.format("user%s", now);
        String email = String.format("user%s@localhost", now);
        app.james().createUser(user, password); //для внешнего почтового сервера(JamesHelper)
        app.registration().start(user, email);

        //List<MailMessage> mailMessages = app.mail().waitForMail(2, 10000); //для встроенного почтового сервера(MailHelper)

        List<MailMessage> mailMessages = app.james().waitForMail(user,password,60000); //для внешнего почтового сервера(JamesHelper)

        String confirmationLink = findConfirmationLink(mailMessages, email);
        app.registration().finish(confirmationLink, password);
        assertTrue(app.newSession().login(user, password));
    }



    //для встроенного почтового сервера(MailHelper)
    //@AfterMethod(alwaysRun = true)
    public void stopMailServer() {
        app.mail().stop();
    }
}
