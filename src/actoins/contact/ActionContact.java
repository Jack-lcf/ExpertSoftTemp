package actoins.contact;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import constants.Attribute;
import constants.Parameter;
import constants.Uri;
import dao.DaoException;
import domain.Contact;
import domain.ContactComparator;
import service.AbstractServiceLocator;
import service.ServiceException;
import service.ServiceLocatorFactory;
import service.contact.ContactService;
import actoins.AbstractActoin;

public class ActionContact extends AbstractActoin {

    public ActionContact(AbstractServiceLocator serviceLocator) {
        super(serviceLocator);
    }

    private List<Contact> getContacts(List<Contact> list, Integer offset, Integer numRecords) {
        List<Contact> result = null;
        if (list != null && !list.isEmpty()) {
            result = new ArrayList<Contact>();
            int limit = list.size();
            if (limit >= (offset + numRecords)) {
                limit = offset + numRecords;
            }
            for (int i = offset; i < limit; i++) {
                result.add(list.get(i));
            }
        }
        return result;
    }

    @Override
    public String exec(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        ContactService service = ServiceLocatorFactory.getServiceLocator().getService(ContactService.class);
        List<Contact> allContacts = null;

        // Sorting list
        String method = request.getParameter(Parameter.METHOD_KEY);
        if (method == null) {
            method = "getId";
        }
        try {
            allContacts = service.getAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        ContactComparator comparator = new ContactComparator(method);
        Collections.sort(allContacts, comparator);

        // Paging
        int page = 1;
        int recordsPerPage = 5;
        int numOfPages = 0;
        List<Contact> selectedContacts = null;
        if (request.getParameter(Parameter.PAGE_KEY) != null) {
            page = Integer.parseInt(request.getParameter(Parameter.PAGE_KEY));
        }
        selectedContacts = getContacts(allContacts, (page - 1) * recordsPerPage, recordsPerPage);
        numOfPages = (int) Math.ceil(allContacts.size() * 1.0 / recordsPerPage);
        request.setAttribute(Attribute.NUMBER_OF_PAGES_KEY, numOfPages);
        request.setAttribute(Attribute.CURRENT_PAGE_KEY, page);
        request.setAttribute(Attribute.CURRENT_METHOD_KEY, method);
        request.setAttribute(Attribute.CONTACTS_KEY, selectedContacts);
        return Uri.JSP_PREFIX + Uri.CONTACT_MAIN_URI + Uri.JSP_SUFFIX;
    }

}
