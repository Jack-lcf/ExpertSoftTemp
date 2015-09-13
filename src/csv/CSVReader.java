package csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import domain.Contact;

public class CSVReader {
    private final String DELIMITER = ";";
    private BufferedReader br = null;
    private String[] headers = null;

    public CSVReader() {

    }

    /**
     * @return the headers
     */
    public String[] getHeaders() {
        return headers;
    }

    public List<Contact> parse(String filePath) {
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
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Read file error");
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println("BufferedReader close error:" + e);
                }
            }
        }
        return result;
    }

}
