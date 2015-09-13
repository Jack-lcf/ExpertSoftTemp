package service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import dao.DaoFactory;
import dao.mysql.DaoFactoryImpl;

public class ServiceLocatorImpl extends AbstractServiceLocator {
    private static Map<Class<? extends Service>, Class<? extends Service>> services = new ConcurrentHashMap<Class<? extends Service>, Class<? extends Service>>();

    static {

    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Service> T getService(Class<T> key) throws ServiceException {
        Class<? extends Service> value = services.get(key);
        Service service = null;
        if (value != null) {
            try {
                service = value.getConstructor(DaoFactory.class).newInstance(new DaoFactoryImpl());
            } catch (Exception e) {
                throw new ServiceException(e);
            }
        }
        return (T) service;
    }

}
