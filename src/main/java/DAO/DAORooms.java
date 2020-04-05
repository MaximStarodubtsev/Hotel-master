package DAO;

import Model.Room;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.ArrayList;

@Data
@Repository
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class DAORooms {

    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private Session session;

    @Transactional(readOnly = true)
    public ArrayList<Room> getAll(){
        return (ArrayList<Room>)session
                .createQuery("FROM Room").list();
    }

    @Transactional(readOnly = true)
    public Room getById(Long id){
        ArrayList<Room> rooms = (ArrayList<Room>)session.createQuery("FROM Room WHERE id=:id")
                .setParameter("id", id)
                .list();
        return ((rooms == null) || (rooms.size() == 0)) ? null : (Room)(rooms.toArray()[0]);
    }

    @Transactional(readOnly = true)
    public Room getByNumber(int number){
        ArrayList<Room> rooms = (ArrayList<Room>)session.createQuery("FROM Room WHERE number=:number")
                .setParameter("number", number)
                .list();
        return ((rooms == null) || (rooms.size() == 0)) ? null : (Room)(rooms.toArray()[0]);
    }

    @Transactional
    public void save(Room room){
        session.save(room);
    }
}

