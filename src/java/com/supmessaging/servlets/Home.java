package com.supmessaging.servlets;

import com.supmessaging.persistence.Messages;
import com.supmessaging.tools.ActionToolbar;
import com.supmessaging.tools.ComplexRequest;
import com.supmessaging.tools.SessionCreator;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Home extends HttpServlet {

    public static final String connectedView = "/WEB-INF/dashboard.jsp";
    public static final String guestView = "/WEB-INF/home.jsp";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SessionCreator sessionCreator = new SessionCreator(request);
        HttpSession session = request.getSession();
        ActionToolbar myBeautifulToolbar = new ActionToolbar();

        myBeautifulToolbar.getAdaptedToolbar(sessionCreator, request);
        ComplexRequest stats = new ComplexRequest();
        int statUsers = stats.getStatsUser();
        int statMessages = stats.getStatsMessages();

        request.setAttribute("nbUsers", statUsers);
        request.setAttribute("nbMessages", statMessages);

        if (sessionCreator.checkSessionExist()) {
            List<Messages> messages = stats.getTenLastMessages((int) session.getAttribute("userid"));
            request.setAttribute("lastMessages", messages);
            this.getServletContext().getRequestDispatcher(connectedView).forward(request, response);
        } else {
            this.getServletContext().getRequestDispatcher(guestView).forward(request, response);
        }
    }

}
