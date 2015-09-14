package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import actoins.ActionManager;
import actoins.ActionManagerFactory;
import constants.Attribute;
import constants.Messages;
import constants.Parameter;
import constants.Uri;

/**
 * Servlet implementation class web_controller
 */
@WebServlet(name = "/DispatcherServlet", loadOnStartup = 1, urlPatterns = { "/index","/import","/contacts" })
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public static final String ERROR_DATA_BASE = "Database error";

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request
     *            servlet request
     * @param response
     *            servlet response
     * @throws ServletException
     *             if a servlet-specific error occurs
     * @throws IOException
     *             if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception e) {
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request
     *            servlet request
     * @param response
     *            servlet response
     * @throws ServletException
     *             if a servlet-specific error occurs
     * @throws IOException
     *             if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        try {
            processRequest(request, response);
        } catch (Exception e) {
        }
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request
     *            servlet request
     * @param response
     *            servlet response
     * @throws ServletException
     *             if a servlet-specific error occurs
     * @throws IOException
     *             if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {

        response.setContentType("text/html;charset=UTF-8");
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String actionName;

        // Getting command from request
        String action = request.getParameter(Parameter.COMMAND_KEY);

        if (action == null) {
            int beginActionName = contextPath.length();
            int endActionName = uri.lastIndexOf(Uri.URI_DELIMETER);

            if (endActionName >= 0) {
                // If getting uri contents point
                actionName = uri.substring(beginActionName, endActionName);
            } else {
                // If getting uri doesn't content point
                actionName = uri.substring(beginActionName);
            }
        } else {
            actionName = action;
        }

        ActionManager actionManager = ActionManagerFactory.getActionManager();
        try {
            String path = actionManager.execute(actionName, request, response);
            if (path != null) {
                request.getRequestDispatcher(path).forward(request, response);
            } else {
                if (Uri.MAIN_URI.equals(actionName)) {
                    request.getRequestDispatcher(actionName + Uri.JSP_SUFFIX).forward(request, response);
                } else {
                    request.getRequestDispatcher(Uri.JSP_PREFIX + actionName + Uri.JSP_SUFFIX).forward(request,
                            response);
                }
            }
        } catch (Exception e) {
            String jsp = Uri.JSP_PREFIX + Uri.ERROR_URI + Uri.JSP_SUFFIX;
            request.setAttribute(Attribute.ERROR_KEY, Messages.DATA_BASE_ERROR);
            getServletContext().getRequestDispatcher(jsp).forward(request, response);
        }
    }
}
