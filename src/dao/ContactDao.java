package dao;

import domain.Contact;

public interface ContactDao extends Dao<Contact>{
    public Contact findByLogin(String login) throws DaoException;
}
