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
        String security = null;
        
        ComplexRequest toto = new ComplexRequest();
        toto.checkRelation("adrienxp3", "adrienxp3");
        
        System.out.println(sessionCreator.checkSessionExist());
        System.out.println(request.getParameter("userId") != null);
        System.out.println(sessionCreator.checkSessionExist() && request.getParameter("userId") != null);

        
        if(sessionCreator.checkSessionExist() && request.getParameter("userId") != null && request.getParameter("security") != null) {
            userId = Integer.parseInt(request.getParameter("userId"));
            security = request.getParameter("security");
            
            System.out.println(userId);
            System.out.println(security);
            System.out.println(session.getAttribute("security"));
            System.out.println(userId > -1 && security == session.getAttribute("security"));
            if (userId > -1 && security.equals(session.getAttribute("security"))) {
                addFriend.createRelationship((String) session.getAttribute("username"), userId);
                System.out.println("GO LOOK THE TABLE");
            }
            session.setAttribute("security", null);
            response.sendRedirect("/SupMessaging/search");
        }
        else {
            response.sendRedirect("/SupMessaging");
        }
    }
}
