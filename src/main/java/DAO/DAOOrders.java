package DAO;

import Model.Order;
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
public class DAOOrders {

    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private Session session;


    @Transactional(readOnly = true)
    public ArrayList<Order> getAll(){
        return  (ArrayList<Order>)session
                .createQuery("FROM Order ").list();
    }

    @Transactional(readOnly = true)
    public ArrayList<Order> getBySettleState(Order.SettleState settleState){
        ArrayList<Order> list = (ArrayList<Order>)session.createQuery("FROM Order WHERE settleState=:settleState")
                .setParameter("settleState", settleState)
                .list();
        System.out.println("The orders were taken from the table");
        return list;
    }

    @Transactional
    public void updateOrder(Order order){
        session.getTransaction().begin();
        session.update(order);
        session.getTransaction().commit();
    }

    @Transactional(readOnly = true)
    public Order getById(Long id){
        ArrayList<Order> list = (ArrayList<Order>)session.createQuery(
                "SELECT orders FROM Order orders WHERE id=:id")
                .setParameter("id", id)
                .list();
        return (list != null && list.size() > 0) ? (Order)(list.toArray()[0]) : null;
    }

    @Transactional(readOnly = true)
    public ArrayList<Order> getByGuestId(Long guestId){
        ArrayList<Order> list = (ArrayList<Order>)session.createQuery(
                "SELECT orders FROM Order orders WHERE guest.id=:guestId")
                .setParameter("guestId", guestId)
                .list();
        return (list != null && list.size() > 0) ? list : null;
    }

    @Transactional(readOnly = true)
    public ArrayList<Order> getByRoomId(Long room_id){
        return (ArrayList<Order>) session.createQuery("FROM Order WHERE room.id=:room_id")
                .setParameter("room_id", room_id)
                .list();
    }

    @Transactional
    public void save(Order order){
        session.save(order);
    }

    @Transactional
    public void delete(Order order) {
        session.getTransaction().begin();
        session.delete(order);
        session.getTransaction().commit();
    }
}