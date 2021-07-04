package automate;

import base.BasePage;
import sharesies.SharesiesApp;
import sharesies.SharesiesHome;
import sharesies.SharesiesLogIn;
import sharesies.SharesiesReports;
import sharesies.SharesiesSettings;

public class SharesiesAutomation {    
    public static void main(String[] args) {
        BasePage testBase = new BasePage();
        testBase.initialize();

        SharesiesHome homePage = new SharesiesHome();
        SharesiesLogIn loginPage = homePage.clickLoginButton();
        SharesiesApp app = loginPage.logIn();
        SharesiesSettings settingsPage = app.clickSettings();
        SharesiesReports reportsPage = settingsPage.clickReports();
    }
}
