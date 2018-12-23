package servlets;

import managers.GameManager;
import utils.ServletUtils;
import utils.SessionUtils;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.util.Collection;

@WebServlet(name = "UploadFileServlet", urlPatterns = {"/uploadfile"})
@MultipartConfig
public class UploadFileServlet extends HttpServlet
{
    private static final String SAVE_DIR = "uploadFiles";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            final Part filePart = request.getPart("xmlFile");
            final String fileName = getFileName(filePart);

            if(fileName.equals("")){throw new Exception("Please choose a file");}

                File xmlFile = new File(fileName);
                String path = xmlFile.getAbsolutePath();
                System.out.println(path);
                filePart.write(path);

                GameManager gamesDataHolder = ServletUtils.getGameManager(getServletContext());

                String gameName = request.getParameter("userGameName");

                if(gameName.isEmpty())
                {
                    throw new Exception("Please choose a name for your game");
                }
                else if(gamesDataHolder.isGameExists(gameName))
                {
                    throw new Exception("Please choose other game name, this name is taken");
                }

                String userName = SessionUtils.getUsername(request);
                gamesDataHolder.addGame(gameName, path, userName);

                response.sendRedirect("MainPage.jsp");

        }
        catch(Exception ex)
        {
            request.setAttribute("errorMassage", ex.getMessage());
            getServletContext().getRequestDispatcher("/pages/MainPage/MainPage.jsp").forward(request, response);
        }

    }

    private String getFileName(final Part part)
    {
        final String partHeader = part.getHeader("content-disposition");
        for(String content : part.getHeader("content-disposition").split(";"))
        {
            if(content.trim().startsWith("filename"))
            {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        processRequest(request, response);
    }

}
