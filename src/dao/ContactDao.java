package dao;

import java.util.List;

import domain.Contact;

public interface ContactDao extends Dao<Contact>{
    public Contact findByLogin(String login) throws DaoException;
    
    public List<Contact> getContacts(Integer offset, Integer noOfrecords) throws DaoException;
    
    public Integer getNumberOfRecords() throws DaoException;
}
