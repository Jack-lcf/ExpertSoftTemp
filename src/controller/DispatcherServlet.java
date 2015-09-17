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

import logger.Log;

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
import csv.CSVFilter;

/**
 * Servlet implementation class web_controller
 */
@WebServlet(name = "/DispatcherServlet", loadOnStartup = 1, urlPatterns = { "/index", "/import", "/contacts" })
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

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
        String actionName = null;

        boolean isMultipartContent = ServletFileUpload.isMultipartContent(request);
        if (!isMultipartContent) {

            // Getting command from request
            String action = request.getParameter(Parameter.COMMAND_KEY);
            System.out.println("comm -" + action);
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

        } else {

            // File uploading
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            List<FileItem> fields = null;
            try {
                fields = upload.parseRequest(request);
            } catch (FileUploadException e) {
                Log.error(Messages.UPLOAD_FILE_ERROR + e);
                String jsp = Uri.JSP_PREFIX + Uri.ERROR_URI + Uri.JSP_SUFFIX;
                request.setAttribute(Attribute.ERROR_KEY, Messages.UPLOAD_FILE_ERROR);
                getServletContext().getRequestDispatcher(jsp).forward(request, response);
            }

            Iterator<FileItem> it = fields.iterator();

            while (it.hasNext()) {
                FileItem fileItem = it.next();
                boolean isFormField = fileItem.isFormField();
                if (isFormField) {
                    actionName = fileItem.getString();
                } else {
                    if (!CSVFilter.accept(fileItem.getName())) {
                        request.setAttribute(Attribute.ERROR_KEY, Messages.TYPE_INCORRECT_ERROR);
                    } else {
                        File file = new File(Attribute.FILE_TEMP_KEY);
                        try {
                            fileItem.write(file);
                            request.setAttribute(Attribute.FILE_KEY, file);
                        } catch (Exception e) {
                            Log.error(Messages.FILEITEM_WRITE_ERROR + e);
                            String jsp = Uri.JSP_PREFIX + Uri.ERROR_URI + Uri.JSP_SUFFIX;
                            request.setAttribute(Attribute.ERROR_KEY, Messages.UPLOAD_FILE_ERROR);
                            getServletContext().getRequestDispatcher(jsp).forward(request, response);
                        }
                    }
                }
            }

        }

        ActionManager actionManager = ActionManagerFactory.getActionManager();
        System.out.println("act-name - " + actionName);
        try {
            String path = actionManager.execute(actionName, request, response);
            System.out.println("path - " + path);
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
            Log.error(Messages.DATA_BASE_ERROR);
            String jsp = Uri.JSP_PREFIX + Uri.ERROR_URI + Uri.JSP_SUFFIX;
            request.setAttribute(Attribute.ERROR_KEY, Messages.DATA_BASE_ERROR);
            getServletContext().getRequestDispatcher(jsp).forward(request, response);
        }
    }
}
