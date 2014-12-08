package com.supmessaging.tools;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class ActionToolbar {
    public void getAdaptedToolbar(SessionCreator sessionCreator, HttpServletRequest request) {
        Map<String, List<String> > links = new HashMap<>();

        links.put("home", Arrays.asList("", "/SupMessaging", " <span class=\"glyphicon glyphicon-home\" aria-hidden=\"true\"></span>"));
        
        if (sessionCreator.checkSessionExist()) {
            // Elements apparaissants si la personne est connectée
            links.put("inbox", Arrays.asList("", "/SupMessaging/inbox", "Inbox"));
            links.put("editProfile", Arrays.asList("", "/SupMessaging/profile", "My profile"));
            links.put("logOut", Arrays.asList("", "/SupMessaging/goodbye", "Logout"));
        }
        else {
            // Elements apparaissants si la personne n'est pas connectée
            links.put("contact", Arrays.asList("", "/SupMessaging/contact", "Contact"));
            links.put("connect", Arrays.asList("", "/SupMessaging/connect", "Sign in"));
            links.put("registration", Arrays.asList("", "/SupMessaging/registration" ,"Sign up"));
        }
        
        switch(request.getRequestURI()) {
            default:
                try {
                    links.get("home").set(0, "active");
                } catch (NullPointerException ex) {};
                break;
            case "/SupMessaging/contact":
                try {
                    links.get("contact").set(0, "active");
                } catch (NullPointerException ex) {};
                break;
            case "/SupMessaging/connect":
                try {
                    links.get("connect").set(0, "active");
                } catch (NullPointerException ex) {};
                break;
            case "/SupMessaging/registration":
                try {
                    links.get("registration").set(0, "active");
                } catch (NullPointerException ex) {};
                break;
            case "/SupMessaging/profile":
                try {
                    links.get("editProfile").set(0, "active");
                } catch (NullPointerException ex) {};
                break;
            case "/SupMessaging/inbox":
                try {
                    links.get("inbox").set(0, "active");
                } catch (NullPointerException ex) {};
                break;
        }
        
        request.setAttribute("links", links);
    }
}
