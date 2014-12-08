package com.supmessaging.servlets;

import com.supmessaging.tools.ActionToolBar;
import com.supmessaging.tools.CheckInput;
import com.supmessaging.tools.SessionCreator;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Profile extends HttpServlet {
    public static final String jspView = "/WEB-INF/profile.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SessionCreator sessionCreator = new SessionCreator(request);
        ActionToolBar myBeautifulToolbar = new ActionToolBar();
        
        myBeautifulToolbar.getAdaptedToolbar(sessionCreator, request);

        if(sessionCreator.checkSessionExist()) {
            
            // On set les données de l'utilisateur dans les champs
            request.setAttribute("firstName", "");
            request.setAttribute("lastName", "");
            request.setAttribute("email", "");
        
            this.getServletContext().getRequestDispatcher( jspView ).forward( request, response );
        }
        else {
            response.sendRedirect("/SupMessaging");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, String> errors = new HashMap<>();
        CheckInput checkInput = new CheckInput(request, errors);
        
        SessionCreator sessionCreator = new SessionCreator(request);
        
        
        // On récupère les modifications
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        
        errors.clear();
        request.setAttribute("firstName", firstName);
        request.setAttribute("lastName", lastName);
        request.setAttribute("email", email);
        
        checkInput.nonEmpty(firstName, "firstName", false);
        checkInput.nonEmpty(lastName, "lastName", false);
        checkInput.validateMail(email, "email", false);
        
        if(!errors.isEmpty()) {
            request.setAttribute("error", errors);
            this.getServletContext().getRequestDispatcher(jspView).forward( request, response );
            errors.clear();
        }
        
        /*
        String oldPassword = request.getParameter("oldPassword");
        String newPasswordOne = request.getParameter("newPasswordOne");
        String newPasswordTwo = request.getParameter("newPasswordTwo");
        */
    }
}
