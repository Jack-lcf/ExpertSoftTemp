package domain;

import java.lang.reflect.Method;
import java.util.Comparator;

public class ContactComparator implements Comparator<Contact> {

    @SuppressWarnings("rawtypes")
    private static final Class[] EMPTY_CLASS_ARRAY = new Class[] {};
    private static final Object[] EMPTY_OBJECT_ARRAY = new Object[] {};

    private Method method;

    public ContactComparator(String methodName) {
        // Make sure the method exists in the given bean class
        try {
            method = Contact.class.getMethod(methodName, EMPTY_CLASS_ARRAY);
        } catch (NoSuchMethodException nsme) {
            String message = methodName + "() method does not exist";
            throw new IllegalArgumentException(message);
        }

        // Make sure the method returns a value
        @SuppressWarnings("rawtypes")
        Class returnClass = method.getReturnType();

        if (returnClass.getName().equals("void")) {
            String message = methodName + " has a void return type";
            throw new IllegalArgumentException(message);
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public int compare(Contact o1, Contact o2) {
        Object field1 = null;
        Object field2 = null;
        try {
            field1 = method.invoke(o1, EMPTY_OBJECT_ARRAY);
            field2 = method.invoke(o2, EMPTY_OBJECT_ARRAY);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Treat empty strings like nulls
        if (field1 instanceof String && ((String) field1).length() == 0) {
            field1 = null;
        }

        if (field2 instanceof String && ((String) field2).length() == 0) {
            field2 = null;
        }

        if (field1 instanceof Comparable) {
            if (field1 instanceof String)
                return ((String) field1).compareToIgnoreCase((String) field2);
            else
                return ((Comparable) field1).compareTo(field2);
        } else {

            return field1.toString().compareToIgnoreCase(field1.toString());

        }
    }

}
