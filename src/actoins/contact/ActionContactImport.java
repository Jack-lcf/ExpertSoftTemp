package actoins.contact;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import constants.Parameter;
import constants.Uri;
import service.AbstractServiceLocator;
import service.ServiceException;
import actoins.AbstractActoin;

public class ActionContactImport extends AbstractActoin{

    public ActionContactImport(AbstractServiceLocator serviceLocator) {
        super(serviceLocator);
    }

    @Override
    public String exec(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String filePath = request.getParameter(Parameter.FILE_PATH_KEY);
        if(filePath != null && !filePath.isEmpty()){
            System.out.println("Import file: " + filePath);
        }
        return Uri.MAIN_URI + Uri.JSP_SUFFIX;
    }

}
