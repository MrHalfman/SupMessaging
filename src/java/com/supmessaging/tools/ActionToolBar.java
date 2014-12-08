package com.supmessaging.tools;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class ActionToolBar {
    public void getAdaptedToolbar(SessionCreator sessionCreator, HttpServletRequest request) {
        Map<String, List<String> > links = new HashMap<>();

        links.put("home", Arrays.asList("", "/SupMessaging", " <span class=\"glyphicon glyphicon-home\" aria-hidden=\"true\"></span>"));
        
        if (!sessionCreator.checkSessionExist()) {
            // Elements apparaissant si la personne n'est pas connecté
            links.put("contact", Arrays.asList("", "/SupMessaging/contact", "Contact"));
            links.put("connect", Arrays.asList("", "/SupMessaging/connect", "Sign in"));
            links.put("registration", Arrays.asList("", "/SupMessaging/registration" ,"Sign up"));
        }
        else {
            // Elements apparaissant si la personne est connecté 
            links.put("editProfile", Arrays.asList("", "/SupMessaging/profile", "My profile"));
            links.put("logOut", Arrays.asList("", "/SupMessaging/goodbye", "Logout"));
        }
        
        request.setAttribute("links", links);
    }
}
