package Service;

import DAO.*;
import Model.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Data
@Service
@RequiredArgsConstructor(onConstructor = @_(@Autowired) )
public class HotelService implements Runnable{

    private String message;
    private String validDataMessage;
    private String loginMessage;
    private String validRoomMessage;
    private boolean threadStarted = false;

    @Autowired
    private DAOOrdersRunnable daoOrdersRunnable;
    @Autowired
    private final User user;
    @Autowired
    private final DAOGuests daoGuests;
    @Autowired
    private final DAOUsers daoUsers;
    @Autowired
    private final DAOOrders daoOrders;
    @Autowired
    private final DateUtil dateUtil;
    @Autowired
    private final DAORooms daoRooms;

    public void makeOrder(String name, String surname, String patronymic, String birthDate,
                          String phoneNumber, String settlementDate, String leaveDate,
                          int room_id, int roomRent){
        Guest guest = daoGuests.getByNameAndPhoneNumber(name, surname, phoneNumber);
        if(guest == null) {
            guest = Guest.builder()
                    .name(name)
                    .surname(surname)
                    .patronymic(patronymic)
                    .phoneNumber(phoneNumber)
                    .birthDate(dateUtil.stringToDate(birthDate))
                    .user(daoUsers.getByName(user.getName()))
                    .build();
            daoGuests.save(guest);
            guest = daoGuests.getByNameAndPhoneNumber(name, surname, phoneNumber);
        }

        Order order = Order.builder()
                .guest(guest)
                .room(daoRooms.getById(Long.valueOf(room_id)))
                .settlementDate(dateUtil.stringToDate(settlementDate))
                .leaveDate(dateUtil.stringToDate(leaveDate))
                .rent(roomRent)
                .settleState(checkingSettlingDate(dateUtil.stringToDate(settlementDate)))
                .build();
        daoOrders.save(order);
    }

    public void addRoom(int number, String places, String level, int rent){
        Room room = Room.builder()
                .number(number)
                .places(Room.Places.valueOf(places))
                .level(Room.Level.valueOf(level))
                .rent(rent)
                .build();
        daoRooms.save(room);
    }

    public Order.SettleState checkingSettlingDate(LocalDateTime settlementDate){
        return settlementDate.isBefore(LocalDateTime.now())
                || settlementDate.equals(LocalDateTime.now())
                ? Order.SettleState.SETTLED : Order.SettleState.RESERVED;
    }

    public boolean checkingFreeRoom(Long room_id, String settlementDate, String leaveDate){
        ArrayList<Order> orders = daoOrders.getByRoomId(room_id);
        if (orders.size() == 0) {
            return true;
        }
        else {
            for (Order order : orders) {
                if (!(dateUtil.stringToDate(leaveDate).isBefore(order.getSettlementDate()) ||
                        dateUtil.stringToDate(settlementDate).isAfter(order.getLeaveDate()))) {
                    return false;
                }
            }
            return true;
        }
    }

    public boolean validData(String name, String surname, String number,
            String settlementDate, String leaveDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try {
            formatter.parse(settlementDate);
            formatter.parse(leaveDate);
            if (dateUtil.stringToDate(leaveDate).isBefore(dateUtil.stringToDate(settlementDate))
                    || dateUtil.stringToDate(leaveDate).equals(dateUtil.stringToDate(settlementDate))
                    || dateUtil.stringToDate(settlementDate).isBefore(LocalDateTime.now())
                    || name == null || surname == null || number == null){
                return false;
            } else {
                return true;
            }
        } catch (Exception exception) {
            return false;
        }
    }

    @Override
    public void run(){
        try {
            int i = 0;

            while (!Thread.interrupted()) {
                i++;
                System.out.println(String.format("Checking cycle is %s", i));
                ArrayList<Order> orders = daoOrdersRunnable.getBySettleState(Order.SettleState.SETTLED);
                for (Order order: orders) {
                    System.out.println("Checking the order id " + order.getId());
                    System.out.println("Time is " + LocalDateTime.now());
                    if (order.getLeaveDate().isBefore(LocalDateTime.now()) ||
                            order.getLeaveDate().equals(LocalDateTime.now())){
                        leavingOrSettlingIntoTheRoom(order, Order.SettleState.ARCHIVE);
                        System.out.println("The guest with order id "+order.getId()+
                                " left the room number " + order.getRoom().getId());
                    }
                }
                orders = daoOrdersRunnable.getBySettleState(Order.SettleState.RESERVED);
                for (Order order: orders) {
                    System.out.println("Checking the order id " + order.getId());
                    System.out.println("Time is " + LocalDateTime.now());
                    System.out.println("Time of order is " + order.getSettlementDate());
                    if (order.getSettlementDate().isBefore(LocalDateTime.now()) ||
                            order.getSettlementDate().equals(LocalDateTime.now())){
                        leavingOrSettlingIntoTheRoom(order, Order.SettleState.SETTLED);
                        System.out.println("The guest with order id "+order.getId()+
                                " settled into the room number " + order.getRoom().getId());
                    }
                }
                Thread.sleep(60000);
                System.out.println();
            }
        }
        catch (InterruptedException e){
            System.out.println(e.getMessage());
        }
    }

    public void leavingOrSettlingIntoTheRoom (Order order, Order.SettleState settleState){
        order.setSettleState(settleState);
        daoOrdersRunnable.updateOrder(order);
    }

    public ArrayList<Guest> getAllGuests(){
        return daoGuests.getAll();
    }

    public ArrayList<Room> getAllRooms(){
        return daoRooms.getAll();
    }

    public boolean validLoginData () {
        if(user.getName().equals("superUser") && !user.getPassword().equals("1111")){
            return false;
        }
        if(!(user.getName() == null) && !(user.getPassword() == null) && !user.getName().equals("")
                && !user.getPassword().equals("")){
            for(User user1: daoUsers.getAll()){
                if(user.getName().equals(user1.getName()) && !user.getPassword().equals(user1.getPassword())){
                    return false;
                }
                if(user.getName().equals(user1.getName()) && user.getPassword().equals(user1.getPassword())){
                    return true;
                }
            }
            daoUsers.save(user);
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<Order> getOrdersForCurrentUser() {
        if (daoGuests.getByUserName(user.getName()) != null
                && daoGuests.getByUserName(user.getName()).size() > 0){
            ArrayList<Order> orders = new ArrayList<>();
            for(Guest guest: daoGuests.getByUserName(user.getName())){
                orders.addAll(daoOrders.getByGuestId(guest.getId()));
            }
            return orders;
        } else {
            return null;
        }
    }

    public ArrayList<Order> getReservedOrders(){
        ArrayList<Order> orders = new ArrayList<>();
        orders.addAll(daoOrders.getBySettleState(Order.SettleState.SETTLED));
        orders.addAll(daoOrders.getBySettleState(Order.SettleState.RESERVED));
        return orders;
    }

    public boolean validRoomData(String number, String places, String level, String rent) {
        try {
            Integer.parseInt(number);
            Integer.parseInt(rent);
            Room.Places.valueOf(places);
            Room.Level.valueOf(level);
        } catch (Exception ex) {
            return false;
        }

        if (daoRooms.getByNumber(Integer.parseInt(number)) != null) {
            return false;
        }

        return true;
    }
}
