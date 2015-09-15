package actoins;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DaoException;
import service.AbstractServiceLocator;
import service.Service;
import service.ServiceException;

public abstract class AbstractActoin {
    private AbstractServiceLocator serviceLocator;

    /**
     * @param serviceLocator
     */
    public AbstractActoin(AbstractServiceLocator serviceLocator) {
        this.serviceLocator = serviceLocator;
    }

    public <T extends Service> T getService(Class<T> key) throws ServiceException {
        return serviceLocator.getService(key);
    }

    abstract public String exec(HttpServletRequest request, HttpServletResponse response) throws ServiceException,
            DaoException;

}
