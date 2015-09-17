package csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import service.ServiceException;
import domain.Contact;

public class CSVReader {
    private final String DELIMITER = ",";
    private BufferedReader br = null;
    private String[] headers = null;

    public CSVReader() {
        super();
    }

    /**
     * @return the headers
     */
    public String[] getHeaders() {
        return headers;
    }

    public List<Contact> parse(String filePath) throws ServiceException {
        List<Contact> result = null;
        File file = new File(filePath);
        try {
            br = new BufferedReader(new FileReader(file));
            String str = new String();
            if ((str = br.readLine()) != null) {
                headers = str.split(DELIMITER);
                result = new ArrayList<Contact>();
                while ((str = br.readLine()) != null) {
                    String[] args = str.split(DELIMITER);
                    Contact contact = new Contact();
                    contact.setName(args[0]);
                    contact.setSurname(args[1]);
                    contact.setLogin(args[2]);
                    contact.setEmail(args[3]);
                    contact.setPhone(args[4]);
                    result.add(contact);
                }
            }
        } catch (FileNotFoundException e) {
            throw new ServiceException("Validated file not found: " + e);
        } catch (IOException e) {
            throw new ServiceException("Validated file read error: " + e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    throw new ServiceException("BufferedReader close error:" + e);
                }
            }
        }
        return result;
    }

}
