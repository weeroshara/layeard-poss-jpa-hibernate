package dao;

import db.DBConnection;
import entity.Customer;
import entity.Orders;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdersDAO {

    public static List<Orders> findAllOrders(){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM Orders");
            List<Orders> orders = new ArrayList<>();
            while (rst.next()) {
                orders.add(new Orders(rst.getString(1),
                        rst.getDate(2),
                        rst.getString(3)));
            }
            return orders;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }

    }

    public static Orders findOrder(String orderId){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM Orders WHERE orderId=?");
            pstm.setObject(1, orderId);
            ResultSet rst = pstm.executeQuery();
            if (rst.next()) {
                return new Orders(rst.getString(1),
                        rst.getDate(2),
                        rst.getString(3));
            }
            return null;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public static boolean saveOrder(Orders orders){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO Orders VALUES (?,?,?)");
            pstm.setObject(1, orders.getOrderID());
            pstm.setObject(2, orders.getOrderDate());
            pstm.setObject(3, orders.getCustomerId());
            return pstm.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public static boolean updateOrder(Orders orders){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("UPDATE Orders SET orderDate=?, customerId=? WHERE orderId=?");
            pstm.setObject(1, orders.getOrderID());
            pstm.setObject(2, orders.getOrderDate());
            pstm.setObject(3, orders.getCustomerId());
            return pstm.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public static boolean deleteOrder(String orderId){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("DELETE FROM Orders WHERE itemCode=?");
            pstm.setObject(1, orderId);
            return pstm.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }
}
