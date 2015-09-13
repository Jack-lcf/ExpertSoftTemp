package service;

public abstract class AbstractServiceLocator {
    abstract public <T extends Service> T getService(Class<T> key) throws ServiceException;

}
