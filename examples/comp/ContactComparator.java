import java.lang.reflect.Field;
import java.util.Comparator;

public class ContactComparator implements Comparator<Contact> {

    private String fieldName;

    public ContactComparator(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public int compare(Contact o1, Contact o2) {
        
        Field toCompare;
        try {
            toCompare = Contact.class.getField(fieldName);
            Object v1 = toCompare.get(o1);
            Object v2 = toCompare.get(o2);
            if (v1 instanceof Comparable<?> && v2 instanceof Comparable<?>){
                Comparable c1 = (Comparable)v1;
                Comparable c2 = (Comparable)v2;
                return c1.compareTo(c2);
            }else{
                
            }
        } catch (Exception e) {
            
        }
        
    }

}
