package com.supmessaging.servlets;

import com.supmessaging.persistence.Users;
import com.supmessaging.tools.ActionToolbar;
import com.supmessaging.tools.CheckForm;
import com.supmessaging.tools.ComplexRequest;
import com.supmessaging.tools.SessionCreator;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserSearch extends HttpServlet {

    private final ActionToolbar myBeautifulToolbar = new ActionToolbar();
    public static final String jspView = "/WEB-INF/userSearch.jsp";
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SessionCreator sessionCreator = new SessionCreator(request);
        myBeautifulToolbar.getAdaptedToolbar(sessionCreator, request);
        
        if(sessionCreator.checkSessionExist()) {
            this.getServletContext().getRequestDispatcher( jspView ).forward( request, response );
        }
        else {
            response.sendRedirect("/SupMessaging");
        }
           
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SessionCreator sessionCreator = new SessionCreator(request);
        myBeautifulToolbar.getAdaptedToolbar(sessionCreator, request);
        Map<String, String> errors = new HashMap<>();
        CheckForm checkData = new CheckForm(request, errors);
        
        String friend = request.getParameter("friend");
        request.setAttribute("friend", friend);
        checkData.nonEmpty(friend, "friend", false);
        
        if(!errors.isEmpty()) {
            request.setAttribute("error", errors);
            this.getServletContext().getRequestDispatcher(jspView).forward(request, response);
            errors.clear();
        }
        else {           
            ComplexRequest searchUser = new ComplexRequest();
            ComplexRequest addFriend = new ComplexRequest();
            List<Users> users = searchUser.contactSearch(friend);
            int idOfFriend = 0;
            
            
            Users toto = new Users(); //remplacer toto par l'user de session
            toto.setId(3); //remplacer toto par l'user de session
            
               for (Users user : users){               
                    System.out.println(user.getFirstname());
                    System.out.println(user.getName());  
                    idOfFriend = user.getId();
                }
               
               // on envoi la requete d'ajout d'amis, à place avec le ONCLIK du bouton ajout amis
               addFriend.createRelationship(toto.getId(), idOfFriend); //remplacer toto par l'user de session
           
               
                      
            
            
            this.getServletContext().getRequestDispatcher(jspView).forward(request, response);
        }
        
    }
}
