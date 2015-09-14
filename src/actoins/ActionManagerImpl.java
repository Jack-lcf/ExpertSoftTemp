package actoins;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import actoins.contact.ActionContact;
import constants.Command;
import service.AbstractServiceLocator;
import service.ServiceLocatorFactory;

public class ActionManagerImpl implements ActionManager {
    private static Map<String, Class<? extends AbstractActoin>> actions = new ConcurrentHashMap<String, Class<? extends AbstractActoin>>();

    static {
        actions.put(Command.CONTACT_COM, ActionContact.class);
    }

    @Override
    public String execute(String actionName, HttpServletRequest request, HttpServletResponse response) {
        AbstractServiceLocator serviceLocator = ServiceLocatorFactory.getServiceLocator();
        Class<? extends AbstractActoin> actionClass = actions.get(actionName);
        if (actionClass != null) {
            try {
                AbstractActoin actoin = actionClass.getConstructor(AbstractServiceLocator.class).newInstance(
                        serviceLocator);
                return actoin.exec(request, response);
            } catch (Exception e) {

            }
        }
        return null;
    }

}
