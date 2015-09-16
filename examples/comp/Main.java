import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class Main {

    public static void main(String[] args) {
        final Class[] EMPTY_CLASS_ARRAY = new Class[] {};
        try {
            Method toCompare = Contact.class.getMethod("getStr1",EMPTY_CLASS_ARRAY);
            Class returnClass = toCompare.getReturnType();
            System.out.println("return " + returnClass.getSimpleName());
        } catch (Exception e) {
            System.out.println("Error: " + e);
        } 
    }

}
