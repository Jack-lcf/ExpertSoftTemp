package actoins.contact;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import constants.Attribute;
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
        try {
            contacts = service.getAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        request.setAttribute(Attribute.CONTACTS_KEY, contacts);
        return Uri.JSP_PREFIX + Uri.CONTACT_MAIN_URI + Uri.JSP_SUFFIX;
    }

}
