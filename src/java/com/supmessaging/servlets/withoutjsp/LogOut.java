package com.supmessaging.servlets.withoutjsp;

import com.supmessaging.tools.SessionCreator;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogOut extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SessionCreator sessionCreator = new SessionCreator(request);
        
        if(sessionCreator.checkSessionExist()) {
            sessionCreator.destroySession(response, "/SupMessaging");
        }
        else {
            response.sendRedirect("/SupMessaging");
        }
    }
}
