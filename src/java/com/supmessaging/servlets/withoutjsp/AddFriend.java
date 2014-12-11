package com.supmessaging.servlets.withoutjsp;

import com.supmessaging.tools.ComplexRequest;
import com.supmessaging.tools.SessionCreator;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddFriend extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SessionCreator sessionCreator = new SessionCreator(request);
        ComplexRequest addFriend = new ComplexRequest();
        HttpSession session = request.getSession();
        int userId = -1;
        
        System.out.println(sessionCreator.checkSessionExist());
        System.out.println(request.getParameter("userId") != null);
        System.out.println(sessionCreator.checkSessionExist() && request.getParameter("userId") != null);
        
        
        if(sessionCreator.checkSessionExist() && request.getParameter("userId") != null) {
            userId = Integer.parseInt(request.getParameter("userId"));
            System.out.println(userId > -1);
            if (userId > -1) {
                addFriend.createRelationship((String) session.getAttribute("username"), userId);
                System.out.println("GO LOOK THE TABLE");
            }
            response.sendRedirect("/SupMessaging/search");
        }
        else {
            response.sendRedirect("/SupMessaging");
        }
    }
}
