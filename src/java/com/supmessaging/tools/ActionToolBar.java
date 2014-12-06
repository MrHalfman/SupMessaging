/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supmessaging.tools;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Martin
 */
public class ActionToolBar {
    public void getAdaptedToolbar(SessionCreator sessionCreator, HttpServletRequest request) {
        String home = "dashboard";
        String contact = "<li role=\"presentation\"><a href=\"contact\">Contact</a></li>";
        String connect = "<li role=\"presentation\"><a href=\"connect\">Sign in</a></li>";
        String registration = "<li role=\"presentation\"><a href=\"registration\">Sign up</a></li>";
        String logOut = "<li role=\"presentation\"><a href=\"/SupMessaging\">Logout</a></li>";
        
        if (!sessionCreator.checkSessionExist()) {
            home = "/SupMessaging";
            request.setAttribute("contact", contact);
            request.setAttribute("connect", connect);
            request.setAttribute("registration", registration);
        }
        else {
            request.setAttribute("logOut", logOut);
        }
        
        request.setAttribute("home", home);
    }
}
