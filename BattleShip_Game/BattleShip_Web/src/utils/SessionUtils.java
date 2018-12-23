package utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;

public class SessionUtils {

    public static final String USERNAME = "username";
    public static final String UPFILE = "fileName";

    public static String getUsername (HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Object sessionAttribute = session != null ? session.getAttribute(USERNAME) : null;
        return sessionAttribute != null ? sessionAttribute.toString() : null;
    }

    public static String getFileName (HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Object sessionAttribute = session != null ? session.getAttribute(UPFILE) : null;
        return sessionAttribute != null ? sessionAttribute.toString() : null;
    }

    public static void clearSession (HttpServletRequest request) {
        request.getSession().invalidate();
    }
}
