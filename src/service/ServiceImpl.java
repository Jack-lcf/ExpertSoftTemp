package service;

import dao.DaoFactory;

public class ServiceImpl implements Service {
    private DaoFactory factory = null;

    public ServiceImpl(DaoFactory factory) {
        this.factory = factory;
    }

    @Override
    public DaoFactory getDaoFactory() {
        return factory;
    }

}
