package csv;

import java.io.File;
import java.util.List;

import service.ServiceException;
import domain.Contact;

public class ContactCSVBuilder {
    private List<Contact> contacts;
    private CSVReader reader;

    public ContactCSVBuilder() {
        reader = CSVReaderFactory.createCSVReader();
    }

    /**
     * @return the contacts
     * @throws ServiceException 
     */
    public List<Contact> getContacts(File file) throws ServiceException {
        contacts = reader.parse(file.getPath());
        return contacts;
    }
}
