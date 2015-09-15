package controller;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import actoins.ActionManager;
import actoins.ActionManagerFactory;
import constants.Attribute;
import constants.Messages;
import constants.Parameter;
import constants.Uri;

/**
 * Servlet implementation class web_controller
 */
@WebServlet(name = "/DispatcherServlet", loadOnStartup = 1, urlPatterns = { "/index", "/import", "/contacts" })
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
    @SuppressWarnings("unchecked")
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        response.setContentType("text/html;charset=UTF-8");
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String actionName;

        boolean isMultipartContent = ServletFileUpload.isMultipartContent(request);
        if (!isMultipartContent) {

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
        } else {            
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            List<FileItem> fields = null;
            try {
                fields = upload.parseRequest(request);
            } catch (FileUploadException e) {
                System.out.println("Upload error: " + e);
            }
            Iterator<FileItem> it = fields.iterator();
            if (!it.hasNext()) {
                // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                System.out.println("No fields found");
            } else {
                while (it.hasNext()) {
                    FileItem fileItem = it.next();
                    boolean isFormField = fileItem.isFormField();
                    if (isFormField) {
                        String action = fileItem.getString();
                        System.out.println(fileItem.getFieldName() + ": " + action);
                    } else {
                        File file = new File(contextPath);
                        try {
                            fileItem.write(file);
                        } catch (Exception e) {
                            System.out.println("Write file error: " + e);
                        }
                        if(file.exists()){
                            System.out.println("File name: " + file.getName());
                            System.out.println("File path: " + file.getPath());
                            System.out.println("File size: " + file.length());
                        } else {
                            System.out.println("File is null");
                        }
                        String fileName = fileItem.getString();
                        System.out.println(fileName);
                    }
                }
            }
        }
    }
}
