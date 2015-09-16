package actoins.contact;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import constants.Attribute;
import constants.Parameter;
import constants.Uri;
import dao.DaoException;
import domain.Contact;
import service.AbstractServiceLocator;
import service.ServiceException;
import service.ServiceLocatorFactory;
import service.contact.ContactService;
import actoins.AbstractActoin;

public class ActionContact extends AbstractActoin {

    public ActionContact(AbstractServiceLocator serviceLocator) {
        super(serviceLocator);
    }

    @Override
    public String exec(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        ContactService service = ServiceLocatorFactory.getServiceLocator().getService(ContactService.class);
        List<Contact> contacts = null;
        
        // ------------------ PAGING -------------------------------------------------------------------------
        int page = 1;
        int recordsPerPage = 5;
        int numOfRecords = 0;
        int numOfPages = 0;
        if(request.getParameter(Parameter.PAGE_KEY) != null){
            page = Integer.parseInt(request.getParameter(Parameter.PAGE_KEY));
            System.out.println("page = " + page);
        } else {
            System.out.println("page key is null and page = " + page);
        }
        try {
            contacts = service.getContacts((page-1)*recordsPerPage, recordsPerPage);
            numOfRecords = service.getNumberOfRecords();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        numOfPages = (int) Math.ceil(numOfRecords*1.0/recordsPerPage);
        request.setAttribute(Attribute.NUMBER_OF_PAGES, numOfPages);
        request.setAttribute(Attribute.CURRENT_PAGE, page);
        // ---------------------------------------------------------------------------------------------------
        request.setAttribute(Attribute.CONTACTS_KEY, contacts);
        return Uri.JSP_PREFIX + Uri.CONTACT_MAIN_URI + Uri.JSP_SUFFIX;
    }

}
