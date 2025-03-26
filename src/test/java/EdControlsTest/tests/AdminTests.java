package EdControlsTest.tests;

import EdControlsMain.EdPageObjects.AdminRole;
import org.testng.annotations.Test;

public class AdminTests extends AdminRole {

    static AdminRole AdminRole = new AdminRole(driver);
    // Test case 1 (Admin should be able to create the project)
    @Test(priority = 1)
    public void createProject() throws Exception {
        AdminRole.createProject();
    }

    // Test Case 2 (Admin should be able to edit the project details)
    @Test(priority = 2)
    public void checkProjectEditable() throws Exception {
        AdminRole.checkProjectEditable();
    }

    // Test Case 3 (He should be able to edit the project name, reference name, invoice name and reference name)
    @Test(priority = 3)
    public void editProject() throws Exception {
        AdminRole.editProject();
    }
    // Test case 4 (Admin should be able to add/edit the project and company logo)
    @Test (priority = 4)
    public void addEditLogo() throws Exception {
        AdminRole.addEditLogo();
    }

    // Test case 5 (Admin should be able to add/edit accountable, support, consulted and informed user)
    @Test(priority = 5)
    public void addUsersOnNewProject() throws Exception {
        AdminRole.addUsersOnNewProject();
    }

    // Test case 6 (He should be able to add/edit the end date of the project)
    @Test(priority = 6)
    public void addDueDate() throws Exception {
        AdminRole.addDueDate();
    }

    // Test case 7 (Admin should be able to archive the project)
    @Test(priority = 7)
    public void archiveProject() throws Exception {
        AdminRole.archiveProject();
    }

    // Test case 8 (Admin should be able to de-archive the project)
    @Test(priority = 8)
    public void deArchiveProject() throws Exception {
        AdminRole.deArchiveProject();
    }

    // Test Case 9 (Should be able to create a library group)
    @Test (priority = 9)
    public void createLibraryGroup() throws Exception {
        AdminRole.createLibraryGroup();
    }


    // Test Case 10 (Admin should be able to create a template group)
    @Test (priority = 10)
    public void createTemplateGroup() throws Exception {
        AdminRole.createTemplateGroup();
    }

    // Test Case 11 (Should be able to create a template)
    @Test(priority = 11)
    public void createAreaTemplate() throws Exception {
        AdminRole.createAreaTemplate();
    }

    // Create Object Template
    @Test(priority = 12)
    public void createObjectTemplate() throws Exception {
        AdminRole.createObjectTemplate();
    }

    // Test Case 13 (Should be able to edit a template)
    @Test
    public void editTemplate(){

    }

    //Test case 13 (Admin should be able to replace user from User Management)
    @Test (priority = 13)
    public void replaceUserInUserManagement() throws Exception {
        AdminRole.replaceUserInUserManagement();
    }

    //Test Case 14 (Admin should be able to delete user from User Management)
    @Test(priority = 14)
    public void removeUserInUserManagement() throws Exception {
        AdminRole.removeUserInUserManagement();
    }

    //Test Case 15 (Should be able to edit/add the information in the contract information screen)
    @Test(priority = 15)
    public void editContractInfo() throws InterruptedException {
        AdminRole.editContractInfo();
    }












}
