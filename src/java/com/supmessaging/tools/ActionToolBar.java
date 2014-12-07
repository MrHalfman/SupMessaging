package com.supmessaging.tools;

import javax.servlet.http.HttpServletRequest;

public class ActionToolBar {
    public void getAdaptedToolbar(SessionCreator sessionCreator, HttpServletRequest request) {
        String home = "/SupMessaging/home/dashboard";
        String contact = "<li role=\"presentation\"><a href=\"/SupMessaging/contact\">Contact</a></li>";
        String connect = "<li role=\"presentation\"><a href=\"/SupMessaging/connect\">Sign in</a></li>";
        String registration = "<li role=\"presentation\"><a href=\"/SupMessaging/registration\">Sign up</a></li>";
        String editProfile = "<li role=\"presentation\"><a href=\"/SupMessaging/home/myProfile\">My profile</a></li>";
        String logOut = "<li role=\"presentation\"><a href=\"/SupMessaging/goodbye\">Logout</a></li>";
        
        if (!sessionCreator.checkSessionExist()) {
            // Elements apparaissant si la personne n'est pas connecté
            home = "/SupMessaging";
            request.setAttribute("contact", contact);
            request.setAttribute("connect", connect);
            request.setAttribute("registration", registration);
        }
        else {
            // Elements apparaissant si la personne est connecté 
            request.setAttribute("editProfile", editProfile);
            request.setAttribute("logOut", logOut);
        }
        
        request.setAttribute("home", home);
    }
}
