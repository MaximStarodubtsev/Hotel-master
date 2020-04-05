package DAO;

import Model.User;
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
public class DAOUsers {

    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private Session session;

    @Transactional
    public void save(User user){
        session.save(user);
    }

    @Transactional(readOnly = true)
    public User getByName(String name){
        ArrayList<User> list = (ArrayList<User>)session.createQuery(
                "SELECT users FROM User users WHERE name=:name")
                .setParameter("name", name)
                .list();
        return (list != null && list.size() > 0) ? (User)(list.toArray()[0]) : null;
    }

    @Transactional(readOnly = true)
    public ArrayList<User> getAll(){
        return (ArrayList<User>)session.createQuery("FROM User")
                .list();
    }
}
