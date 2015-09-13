package csv;

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
    public List<Contact> getContacts() {
        return contacts;
    }
    
    public void buildListContacts(String fileName){
        contacts = reader.parse(fileName);
    }

}
