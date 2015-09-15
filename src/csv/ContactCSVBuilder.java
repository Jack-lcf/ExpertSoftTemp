package csv;

import java.io.File;
import java.util.List;

import domain.Contact;

public class ContactCSVBuilder {
    private List<Contact> contacts;
    private CSVReader reader;

    public ContactCSVBuilder() {
        reader = CSVReaderFactory.createCSVReader();
    }

    /**
     * @return the contacts
     */
    public List<Contact> getContacts(File file) {
        contacts = reader.parse(file.getPath());
        return contacts;
    }
}
