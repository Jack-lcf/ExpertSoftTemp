package service.contact;

import java.util.List;

import service.ServiceImpl;
import dao.ContactDao;
import dao.DaoException;
import dao.DaoFactory;
import domain.Contact;

public class ContactServiceImpl extends ServiceImpl implements ContactService {

    public ContactServiceImpl(DaoFactory factory) {
        super(factory);
    }

    @Override
    public Integer create(Contact contact) throws DaoException {
        return getContactDao().create(contact);
    }

    @Override
    public Contact getById(Integer id) throws DaoException {
        return getContactDao().getById(id);
    }

    @Override
    public List<Contact> getAll() throws DaoException {
        return getContactDao().getAll();
    }

    @Override
    public void update(Contact contact) throws DaoException {
        getContactDao().update(contact);
    }

    @Override
    public void delete(Integer id) throws DaoException {
        getContactDao().delete(id);
    }

    private ContactDao getContactDao() {
        return getDaoFactory().createDao(ContactDao.class);
    }

    @Override
    public Contact findByLogin(String login) throws DaoException {
        return getContactDao().findByLogin(login);
    }

    @Override
    public List<Contact> getContacts(Integer offset, Integer noOfrecords) throws DaoException {
        return getContactDao().getContacts(offset, noOfrecords);
    }

    @Override
    public Integer getNumberOfRecords() throws DaoException {
        return getContactDao().getNumberOfRecords();
    }

}
