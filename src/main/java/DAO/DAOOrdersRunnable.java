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
public class DAOOrdersRunnable {

    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private Session session;

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
}
