package com.supmessaging.servlets.withoutjsp;

import com.supmessaging.tools.ComplexRequest;
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
        
        ComplexRequest addFriend = new ComplexRequest();
        HttpSession session = request.getSession();
        int userId = -1;
        
        if (request.getParameter("userId") != null) {
            userId = Integer.parseInt(request.getParameter("userId"));
        }
        
        if (userId > -1) {
            addFriend.createRelationship((String) session.getAttribute("username"), userId);
        }
        
    }
}
