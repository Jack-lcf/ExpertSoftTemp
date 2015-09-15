package actoins.contact;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import constants.Attribute;
import constants.Messages;
import constants.Parameter;
import constants.Uri;
import csv.ContactCSVBuilder;
import dao.DaoException;
import domain.Contact;
import service.AbstractServiceLocator;
import service.ServiceException;
import service.ServiceLocatorFactory;
import service.contact.ContactService;
import actoins.AbstractActoin;

public class ActionContactImport extends AbstractActoin {

    public ActionContactImport(AbstractServiceLocator serviceLocator) {
        super(serviceLocator);
    }

    @Override
    public String exec(HttpServletRequest request, HttpServletResponse response) throws ServiceException, DaoException {
        //String filePath = request.getParameter(Parameter.FILE_KEY);
        
        /*List<Contact> contacts = null;
        ContactCSVBuilder builder = new ContactCSVBuilder();
        builder.buildListContacts(filePath);
        contacts = builder.getContacts();
        ContactService service = null;
        if (contacts != null && !contacts.isEmpty()) {
            service = ServiceLocatorFactory.getServiceLocator().getService(ContactService.class);
            for (Contact c : contacts) {
                Contact findContact = service.findByLogin(c.getLogin());
                if (findContact == null) {
                    service.create(c);
                } else {
                    c.setId(findContact.getId());
                    service.update(c);
                }
            }
            contacts = service.getAll();
            request.setAttribute(Attribute.CONTACTS_KEY, contacts);
            return Uri.JSP_PREFIX + Uri.CONTACT_MAIN_URI + Uri.JSP_SUFFIX;
        } else {
            request.setAttribute(Attribute.ERROR_KEY, Messages.DATA_INCORRECT_ERROR);
            return Uri.JSP_PREFIX + Uri.IMPORT_URI + Uri.JSP_SUFFIX;
        }*/
        return Uri.JSP_PREFIX + Uri.IMPORT_URI + Uri.JSP_SUFFIX;
    }

}
