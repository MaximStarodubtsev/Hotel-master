package DAO;

import Model.Guest;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Data
@Repository
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class DAOGuests {

    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private Session session;

    @Transactional(readOnly = true)
    public ArrayList<Guest> getAll(){
        Session session1 = sessionFactory.openSession();
            return  (ArrayList<Guest>)session1
                    .createQuery("FROM Guest").list();
    }

    @Transactional(readOnly = true)
    public Guest getByNameAndPhoneNumber(String name, String surname, String phoneNumber){
        ArrayList<Guest> list = (ArrayList<Guest>)session.createQuery("SELECT guests FROM Guest guests WHERE name=:name" +
                " AND surname=:surname AND phoneNumber=:phoneNumber")
                .setParameter("name", name)
                .setParameter("surname", surname)
                .setParameter("phoneNumber", phoneNumber)
                .list();

        if(list != null && list.size() > 0){
            return (Guest)(list.toArray()[0]);
        }
        else {
            return null;
        }
    }

    @Transactional(readOnly = true)
    public ArrayList<Guest> getByUserName(String userName){
        return (ArrayList<Guest>)session.createQuery(
                "SELECT guests FROM Guest guests WHERE user.name=:username")
                .setParameter("username", userName)
                .list();
    }

    @Transactional
    public void save(Guest guest){
        session.save(guest);
    }

    @Transactional
    public void delete(Guest guest){
        session.getTransaction().begin();
        session.delete(guest);
        session.getTransaction().commit();
    }

}
