package com.supmessaging.servlets;

import com.supmessaging.persistence.Users;
import com.supmessaging.tools.ActionToolbar;
import com.supmessaging.tools.ComplexRequest;
import com.supmessaging.tools.SessionCreator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyFriends extends HttpServlet {

    private final ActionToolbar myBeautifulToolbar = new ActionToolbar();
    public static final String jspView = "/WEB-INF/friends.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SessionCreator sessionCreator = new SessionCreator(request);
        myBeautifulToolbar.getAdaptedToolbar(sessionCreator, request);
        List<Users> users = new ArrayList<>();

        if (sessionCreator.checkSessionExist()) {
            ComplexRequest queryFriends = new ComplexRequest();
            users = queryFriends.getFriends(sessionCreator.getUsername());
            request.setAttribute("users", users);
            this.getServletContext().getRequestDispatcher(jspView).forward(request, response);
        } else {
            response.sendRedirect("/SupMessaging");
        }
    }
}
