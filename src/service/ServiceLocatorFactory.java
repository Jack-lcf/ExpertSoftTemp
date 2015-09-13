package service;

public class ServiceLocatorFactory {
    public static AbstractServiceLocator getServiceLocator() {
        return new ServiceLocatorImpl();
    }

}
