package actoins;

public class ActionManagerFactory {
    public static ActionManager getActionManager() {
        return new ActionManagerImpl();
    }
}
