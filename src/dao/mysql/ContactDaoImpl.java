package dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import logger.Log;
import constants.Messages;
import dao.DaoException;
import dao.ContactDao;
import domain.Contact;

public class ContactDaoImpl extends AbstractJDBCDao<Contact> implements ContactDao {

    public ContactDaoImpl(ConnectionFactory cf) {
        super(cf);
    }

    @Override
    public String getSelectQuery() {
        return "SELECT `id`, `name`, `surname`,`login`,`email`,`phone` FROM `contacts`";
    }

    @Override
    public String getInsertQuery() {
        return "INSERT INTO `contacts` (`name`, `surname`,`login`,`email`,`phone`) VALUES (?,?,?,?,?)";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE `contacts` SET `name`=?,`surname`=?,`login`=?,`email`=?,`phone`=? WHERE `id`=?";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM `contacts` WHERE `id` = ?";
    }

    @Override
    protected List<Contact> parseResultSet(ResultSet rs) throws DaoException {
        List<Contact> result = new ArrayList<Contact>();
        try {
            while (rs.next()) {
                Contact contact = new Contact();
                contact.setId(rs.getInt("id"));
                contact.setName(rs.getString("name"));
                contact.setSurname(rs.getString("surname"));
                contact.setLogin(rs.getString("login"));
                contact.setEmail(rs.getString("email"));
                contact.setPhone(rs.getString("phone"));
                result.add(contact);
            }
            return result;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Contact object) throws DaoException {
        try {
            statement.setString(1, object.getName());
            statement.setString(2, object.getSurname());
            statement.setString(3, object.getLogin());
            statement.setString(4, object.getEmail());
            statement.setString(5, object.getPhone());
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Contact object) throws DaoException {
        try {
            statement.setString(1, object.getName());
            statement.setString(2, object.getSurname());
            statement.setString(3, object.getLogin());
            statement.setString(4, object.getEmail());
            statement.setString(5, object.getPhone());
            statement.setInt(6, object.getId());
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Contact findByLogin(String login) throws DaoException {
        List<Contact> list = null;
        String query = getSelectQuery() + " WHERE `login` = ?";
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = getConnectionFactory().getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, login);
            ResultSet result = statement.executeQuery();
            list = parseResultSet(result);
        } catch (SQLException e) {
            Log.error(Messages.CONNECTION_ERROR + e);
            throw new DaoException(e);
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException | NullPointerException e) {
                throw new DaoException(e);
            }
        }
        if (list == null || list.size() == 0) {
            return null;
        }
        if (list.size() > 1) {
            throw new DaoException(Messages.MORE_THAN_ONE_ERROR);
        }
        return list.iterator().next();
    }

    @Override
    public List<Contact> getContacts(Integer offset, Integer noOfrecords) throws DaoException {
        List<Contact> list = null;
        String query = "SELECT SQL_CALC_FOUND_ROWS * FROM `contacts` LIMIT " + offset + ", " + noOfrecords;
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = getConnectionFactory().getConnection();
            statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            list = parseResultSet(result);
        } catch (SQLException e) {
            Log.error(Messages.CONNECTION_ERROR + e);
            throw new DaoException(e);
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException | NullPointerException e) {
                throw new DaoException(e);
            }
        }
        return list;
    }

    @Override
    public Integer getNumberOfRecords() throws DaoException {
        Integer number = 0;
        String query = "SELECT COUNT(*) FROM `contacts`";
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = getConnectionFactory().getConnection();
            statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                number = result.getInt(1);
            }
        } catch (SQLException e) {
            Log.error(Messages.CONNECTION_ERROR + e);
            throw new DaoException(e);
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException | NullPointerException e) {
                throw new DaoException(e);
            }
        }
        return number;
    }

}
