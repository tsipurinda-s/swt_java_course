package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.stqa.pft.addressbook.model.GroupData;

public class GroupsHelper extends HelperBase {

    public GroupsHelper(FirefoxDriver wd) {
        super(wd);
    }

    public void initGroupCreation() {
        click("new");
    }

    public void fillGroupForm(GroupData groupData) {
        type("group_name", groupData.name());
        type("group_header", groupData.header());
        type("group_footer", groupData.footer());
    }

    public void submitGroupCreation() {
        click("submit");
    }

    public void returnToGroupPage() {
        wd.findElement(By.linkText("group page")).click();
    }

    public void deleteSelectedGroups() {
      click("delete");
    }

    public void selectGroup() {
      click("selected[]");
    }
}
