package actoins.contact;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logger.Log;
import constants.Attribute;
import constants.Command;
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
import actoins.ActionManagerFactory;

public class ActionContactImport extends AbstractActoin {

    public ActionContactImport(AbstractServiceLocator serviceLocator) {
        super(serviceLocator);
    }

    @Override
    public String exec(HttpServletRequest request, HttpServletResponse response) throws ServiceException, DaoException {
        File file = (File) request.getAttribute(Parameter.FILE_KEY);
        List<Contact> contacts = null;
        ContactCSVBuilder builder = new ContactCSVBuilder();
        contacts = builder.getContacts(file);
        file.delete();
        ContactService service = null;
        if (contacts != null && !contacts.isEmpty()) {
            service = ServiceLocatorFactory.getServiceLocator().getService(ContactService.class);
            for (Contact c : contacts) {
                Contact findContact = service.findByLogin(c.getLogin());
                if (findContact == null) {
                    service.create(c);
                    Log.info("New contact was added to database");
                } else {
                    c.setId(findContact.getId());
                    service.update(c);
                }
            }
            return ActionManagerFactory.getActionManager().execute(Command.CONTACT_COM, request, response);
        } else {
            request.setAttribute(Attribute.ERROR_KEY, Messages.DATA_INCORRECT_ERROR);
            return Uri.JSP_PREFIX + Uri.IMPORT_URI + Uri.JSP_SUFFIX;
        }
    }

}
