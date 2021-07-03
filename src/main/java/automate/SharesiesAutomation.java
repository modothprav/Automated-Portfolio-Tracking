package automate;

import base.BasePage;
import sharesies.SharesiesHome;

public class SharesiesAutomation {    
    public static void main(String[] args) {
        BasePage testBase = new BasePage();
        testBase.initialize();

        SharesiesHome homePage = new SharesiesHome();
        homePage.clickLoginButton();
    }
}
