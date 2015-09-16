package service.contact;

import java.util.List;

import dao.DaoException;
import domain.Contact;
import service.Service;

public interface ContactService extends Service {

    public Integer create(Contact contact) throws DaoException;

    public Contact getById(Integer id) throws DaoException;

    public List<Contact> getAll() throws DaoException;

    public void update(Contact contact) throws DaoException;

    public void delete(Integer id) throws DaoException;

    public Contact findByLogin(String login) throws DaoException;

}
