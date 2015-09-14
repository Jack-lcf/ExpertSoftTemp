package actoins;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ActionManager {
    public String execute(String actionName, HttpServletRequest request, HttpServletResponse response);

}
