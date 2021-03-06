package com.supmessaging.tools;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionCreator {

    private final HttpServletRequest request;

    public SessionCreator(HttpServletRequest request) {
        this.request = request;
    }

    public String getUsername() {
        HttpSession session = request.getSession();
        return (String) session.getAttribute("username");
    }

    public void createSession(int uid, String username, int permission) {
        HttpSession session = request.getSession();
        session.setAttribute("userid", uid);
        session.setAttribute("username", username);
        session.setAttribute("permission", permission);
        session.setAttribute("security", null);
    }

    public boolean checkSessionExist() {
        HttpSession session = request.getSession();
        Boolean sessionExist = true;

        if (session.getAttribute("username") == null) {
            sessionExist = false;
        }

        return sessionExist;
    }

    public void destroySession(HttpServletResponse response, String redirection) throws IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        response.sendRedirect(redirection);
    }
}
