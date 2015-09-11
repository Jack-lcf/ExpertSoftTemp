package dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

}
