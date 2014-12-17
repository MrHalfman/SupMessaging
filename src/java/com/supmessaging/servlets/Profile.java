package com.supmessaging.servlets;

import com.supmessaging.tools.ActionToolbar;
import com.supmessaging.tools.CheckForm;
import com.supmessaging.tools.SessionCreator;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.supmessaging.persistence.Users;
import com.supmessaging.tools.Encryption;
import com.supmessaging.tools.ComplexRequest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;

public class Profile extends HttpServlet {

    public static final String jspView = "/WEB-INF/profile.jsp";
    private final ComplexRequest complexRequest = new ComplexRequest();
    private final ActionToolbar myBeautifulToolbar = new ActionToolbar();

    private String firstname = null;
    private String lastname = null;
    private String email = null;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SessionCreator sessionCreator = new SessionCreator(request);
        ComplexRequest complexRequest = new ComplexRequest();
        myBeautifulToolbar.getAdaptedToolbar(sessionCreator, request);
        HttpSession session = request.getSession();

        if (sessionCreator.checkSessionExist()) {

            Users me = complexRequest.getInformationsUser(sessionCreator.getUsername());

            request.setAttribute("firstName", me.getFirstname());
            request.setAttribute("lastName", me.getName());
            request.setAttribute("email", me.getMail());

            this.firstname = me.getFirstname();
            this.lastname = me.getName();
            this.email = me.getMail();

            if (session.getAttribute("popup") == "popup!") {
                session.removeAttribute("popup");
                request.setAttribute("popup", true);
            }
            this.getServletContext().getRequestDispatcher(jspView).forward(request, response);
        } else {
            response.sendRedirect("/SupMessaging");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> errorsData = new HashMap<>();
        Map<String, String> errorsPassword = new HashMap<>();
        HttpSession session = request.getSession();
        SessionCreator sessionCreator = new SessionCreator(request);
        myBeautifulToolbar.getAdaptedToolbar(sessionCreator, request);

        CheckForm checkData = new CheckForm(request, errorsData);
        CheckForm checkPassword = new CheckForm(request, errorsPassword);
        Encryption encryption = new Encryption();
        checkPassword.queryUser((String) sessionCreator.getUsername());

        // On récupère les modifications
        String firstName = checkData.formatName(request.getParameter("firstName"));
        String lastName = checkData.formatName(request.getParameter("lastName"));
        String mail = request.getParameter("email");
        String oldPassword = request.getParameter("oldPassword");
        String newPasswordOne = request.getParameter("newPasswordOne");
        String newPasswordTwo = request.getParameter("newPasswordTwo");

        request.setAttribute("firstName", firstName);
        request.setAttribute("lastName", lastName);
        request.setAttribute("email", mail);

        checkData.nonEmpty(firstName, "firstName", false);
        checkData.nonEmpty(lastName, "lastName", false);
        checkData.validateMail(mail, "email", false);

        if (!errorsData.isEmpty()) {

            request.setAttribute("error", errorsData);

            this.getServletContext().getRequestDispatcher(jspView).forward(request, response);
            errorsData.clear();
            errorsPassword.clear();
        } else {
            if (!oldPassword.isEmpty() || !newPasswordOne.isEmpty() || !newPasswordTwo.isEmpty()) {
                checkPassword.equalizationPassword(newPasswordOne, newPasswordTwo, "newPassword");

                try {
                    checkPassword.validatePassword(oldPassword, "oldPassword", true);
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(Profile.class.getName()).log(Level.SEVERE, null, ex);
                }

                if (!errorsPassword.isEmpty()) {
                    request.setAttribute("errorPassword", errorsPassword);
                } else {
                    String ecryptedPassword = null;
                    try {
                        ecryptedPassword = encryption.encryptionPassword(newPasswordTwo);
                    } catch (NoSuchAlgorithmException ex) {
                        Logger.getLogger(Profile.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    complexRequest.alterPassword(ecryptedPassword, request);
                    session.setAttribute("popup", "popup!");
                }
            }

            // On regarde si les informations d'origines sont différentes pour faire la requête
            if (!firstName.equals(this.firstname) || !lastName.equals(this.lastname)
                    || !mail.equals(this.email)) {

                complexRequest.alterInformations(lastName, firstName, mail, request);

                // On assigne en cas de changement immédiat (du mot de passe par exemple)
                this.firstname = firstName;
                this.lastname = lastName;
                this.email = mail;
                session.setAttribute("popup", "popup!");
            }
            response.sendRedirect("/SupMessaging/profile");
        }
    }
}
