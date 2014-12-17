package com.supmessaging.servlets.withoutjsp;

import com.supmessaging.tools.ComplexRequest;
import com.supmessaging.tools.SessionCreator;
import java.io.IOException;
import javax.servlet.ServletException;
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
        String username = (String) session.getAttribute("username");
        int userId = -1;
        String security = null;
        
        if(sessionCreator.checkSessionExist() && request.getParameter("userId") != null && request.getParameter("security") != null) {
            userId = Integer.parseInt(request.getParameter("userId"));
            security = request.getParameter("security");
            
            if (userId > -1 && security.equals(session.getAttribute("security"))) {
                addFriend.createRelationship(username, userId);
            }
            session.setAttribute("security", null);
            session.setAttribute("popup", "popup!");
            response.sendRedirect("/SupMessaging/search");
        }
        else {
            response.sendRedirect("/SupMessaging");
        }
    }
}
