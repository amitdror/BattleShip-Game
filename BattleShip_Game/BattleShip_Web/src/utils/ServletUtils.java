package utils;

import managers.GameManager;
import managers.UserManager;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class ServletUtils {
    private static final String USER_MANAGER_ATTRIBUTE_NAME = "userManager";
    private static final String GAME_MANAGER_ATTRIBUTE_NAME = "gameManager";
    public static final int INT_PARAMETER_ERROR = Integer.MIN_VALUE;


    public static UserManager getUserManager(ServletContext servletContext) {
        if (servletContext.getAttribute(USER_MANAGER_ATTRIBUTE_NAME) == null) {
            servletContext.setAttribute(USER_MANAGER_ATTRIBUTE_NAME, new UserManager());
        }
        return (UserManager) servletContext.getAttribute(USER_MANAGER_ATTRIBUTE_NAME);
    }

    public static GameManager getGameManager(ServletContext servletContext) {
        if (servletContext.getAttribute(GAME_MANAGER_ATTRIBUTE_NAME) == null) {
            servletContext.setAttribute(GAME_MANAGER_ATTRIBUTE_NAME, new GameManager());
        }
        return (GameManager) servletContext.getAttribute(GAME_MANAGER_ATTRIBUTE_NAME);
    }
/*
    public static ChatManager getChatManager(ServletContext servletContext) {
        if (servletContext.getAttribute(CHAT_MANAGER_ATTRIBUTE_NAME) == null) {
            servletContext.setAttribute(CHAT_MANAGER_ATTRIBUTE_NAME, new ChatManager());
        }
        return (ChatManager) servletContext.getAttribute(CHAT_MANAGER_ATTRIBUTE_NAME);
    }
*/
    public static int getIntParameter(HttpServletRequest request, String name) {
        String value = request.getParameter(name);
        if (value != null) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException numberFormatException) {
            }
        }
        return INT_PARAMETER_ERROR;
    }
}
