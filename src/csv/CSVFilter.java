package csv;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CSVFilter {
    private final static String CSV_EXTENSION = "csv";
    
    public static boolean accept(String fileName){
        Pattern p = Pattern.compile(".+\\." + CSV_EXTENSION);
        Matcher m = p.matcher(fileName);
        return m.matches();
    }

}
