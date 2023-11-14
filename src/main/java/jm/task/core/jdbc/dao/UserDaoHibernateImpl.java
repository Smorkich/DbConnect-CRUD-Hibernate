package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    Transaction transaction;
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try(Session session = Util.getConnection().openSession()) {
            String sql = "CREATE TABLE IF NOT EXISTS Users(Id MEDIUMINT NOT NULL AUTO_INCREMENT," +
                    "Name VARCHAR(255),LastName VARCHAR(255),Age TINYINT,PRIMARY KEY (Id))";

            transaction = session.beginTransaction();
            session.createNativeQuery(sql).executeUpdate();
            transaction.commit();

            System.out.println("Таблица создана");
        } catch(HibernateException ex) {
            System.out.println(ex.getMessage());

            if (transaction!= null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        try(Session session = Util.getConnection().openSession()) {
            String sql = "DROP TABLE IF EXISTS Users";

            transaction = session.beginTransaction();
            session.createNativeQuery(sql).executeUpdate();
            transaction.commit();

            System.out.println("Таблица удалена");
        } catch(HibernateException ex) {
            System.out.println(ex.getMessage());

            if (transaction!= null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name,lastName,age);

        try(Session session = Util.getConnection().openSession()) {

            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();

            System.out.println("User с именем –" + name + " добавлен в базу данных");
        } catch(HibernateException ex) {
            System.out.println(ex.getMessage());
            if (transaction!= null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try(Session session = Util.getConnection().openSession()) {
            transaction = session.beginTransaction();

            User user = session.get(User.class,id);
            session.delete(user);
            transaction.commit();

            System.out.println("Успешно удалено");
        } catch(HibernateException ex) {
            System.out.println(ex.getMessage());

            if (transaction!= null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try(Session session = Util.getConnection().openSession()) {
            CriteriaQuery<User> query = session.getCriteriaBuilder().createQuery(User.class);
            query.from(User.class);

            transaction = session.beginTransaction();
            users = session.createQuery(query).getResultList();
            transaction.commit();

        } catch(HibernateException ex) {
            System.out.println(ex.getMessage());

            if (transaction!= null) {
                transaction.rollback();
            }
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try(Session session = Util.getConnection().openSession()) {

            transaction = session.beginTransaction();
            List<User> users = session.createCriteria(User.class).list();
            for (User u: users) {
                session.delete(u);
            }
            transaction.commit();

            System.out.println("Все записи успешно удалены");
        } catch(HibernateException ex) {
            System.out.println(ex.getMessage());

            if (transaction!= null) {
                transaction.rollback();
            }
        }
    }
}
