package servlets;

import managers.UserManager;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    public static final String USERNAME = "username";
    public static final String USER_NAME_ERROR = "username_error";
    private final String MAIN_ROOM = "pages/MainPage/MainPage.jsp";
    private final String SIGN_UP_URL = "/index.jsp";
    private final String LOGIN_ERROR_URL = "/index.jsp";


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        String usernameFromSession = SessionUtils.getUsername(request);
        UserManager userManager = ServletUtils.getUserManager(getServletContext());

        if (usernameFromSession == null) {
            //user is not logged in yet
            String usernameFromParameter = request.getParameter(USERNAME);
            if (usernameFromParameter == "") {
                //no username in session and no username in parameter -
                //redirect back to the index page
                //this return an HTTP code back to the browser telling it to load
                response.sendRedirect(SIGN_UP_URL);
            } else {
                //normalize the username value
                usernameFromParameter = usernameFromParameter.trim();
                if(usernameFromParameter == "")
                {
                    String errorMessage = "You have to put a your name in the text area";
                    request.setAttribute(USER_NAME_ERROR, errorMessage);
                    getServletContext().getRequestDispatcher(LOGIN_ERROR_URL).forward(request, response);
                }
                else if (userManager.isUserExists(usernameFromParameter)) {
                    String errorMessage = "Username " + usernameFromParameter + " already exists. Please enter a different username.";
                    // username already exists, forward the request back to index.jsp
                    // with a parameter that indicates that an error should be displayed
                    // the request dispatcher obtained from the servlet context is one that MUST get an absolute path (starting with'/')
                    // and is relative to the web app root
                    // see this link for more details:
                    // http://timjansen.github.io/jarfiller/guide/servlet25/requestdispatcher.xhtml
                    request.setAttribute(USER_NAME_ERROR, errorMessage);
                    getServletContext().getRequestDispatcher(LOGIN_ERROR_URL).forward(request, response);
                } else {
                    //add the new user to the users list
                    userManager.addUser(usernameFromParameter);
                    //set the username in a session so it will be available on each request
                    //the true parameter means that if a session object does not exists yet
                    //create a new one
                    request.getSession(true).setAttribute(USERNAME, usernameFromParameter);

                    //redirect the request to the chat room - in order to actually change the URL
                    System.out.println("On login, request URI is: " + request.getRequestURI());
                    response.sendRedirect(MAIN_ROOM);
                }
            }
        } else {
            //user is already logged in
            response.sendRedirect(MAIN_ROOM);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Login Servlet";
    }// </editor-fold>

}
