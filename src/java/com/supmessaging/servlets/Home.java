package com.supmessaging.servlets;

import com.supmessaging.tools.ActionToolbar;
import com.supmessaging.tools.ComplexRequest;
import com.supmessaging.tools.SessionCreator;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Home extends HttpServlet {

    public static final String connectedView    = "/WEB-INF/dashboard.jsp";
    public static final String guestView        = "/WEB-INF/home.jsp";
    
    @Override
    public void doGet( HttpServletRequest request, HttpServletResponse response )	throws ServletException, IOException {            
        SessionCreator sessionCreator = new SessionCreator(request);
        ActionToolbar myBeautifulToolbar = new ActionToolbar();
        
        myBeautifulToolbar.getAdaptedToolbar(sessionCreator, request);
        ComplexRequest stats = new ComplexRequest();
        int statUsers = stats.getStatsUser();
        int statMessages = stats.getStatsUser();
        
        request.setAttribute("nbUsers", statUsers);
        request.setAttribute("nbMessages", statMessages);

        this.getServletContext().getRequestDispatcher(
                (sessionCreator.checkSessionExist() ? connectedView : guestView)
            ).forward(request, response);
        
        

    }

    
}
