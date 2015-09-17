package csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import service.ServiceException;

public class CSVContactValidator {
    private final static String DELIMITER = ",";
    private final static String REGEX_NAME = "[a-zA-Zа-яА-я\\-\\']*";
    private final static String REGEX_EMAIL = "([\\w-]+)@(\\w+\\.)([a-z]{2,4})+";
    private final static String REGEX_PHONE = "(\\+)?([\\d\\(\\)\\-\\s]{6,20})*";
    private final static String[] HEADERS = { "Name", "Surname", "Login", "Email", "Phone" };

    private static BufferedReader br = null;
    private static String[] fileHeaders = null;

    public static boolean isValid(File file) throws ServiceException {
        try {
            br = new BufferedReader(new FileReader(file));
            String str = new String();
            if ((str = br.readLine()) != null) {
                fileHeaders = str.split(DELIMITER);
                if (Arrays.equals(fileHeaders, HEADERS)) {
                    String regexRecord = REGEX_NAME + DELIMITER 
                                        + REGEX_NAME + DELIMITER
                                        + ".+" + DELIMITER
                                        + REGEX_EMAIL + DELIMITER 
                                        + REGEX_PHONE;
                    while ((str = br.readLine()) != null) {
                        if (!isCorrect(regexRecord, str)) {
                            return false;
                        }
                    }
                } else {
                    return false;
                }
            } else {
                return false;
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
        return true;
    }

    private static boolean isCorrect(String regex, String str) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str.trim());
        return m.matches();
    }

}
