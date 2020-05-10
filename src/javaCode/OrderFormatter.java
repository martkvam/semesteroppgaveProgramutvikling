package javaCode;

import javaCode.objects.Order;

import java.util.List;

public class OrderFormatter {
    public static String DELIMITER = ";";

    public static String formatOrder(Order order){
        return order.getOrderNr() + DELIMITER + order.getPersonId() + DELIMITER + order.getCarId() + DELIMITER + order.getOrderStarted()
                + DELIMITER + order.getOrderFinished() + DELIMITER + order.formatComponents(order.getComponentList()) + DELIMITER + order.formatAdjustments(order.getAdjustmentList())
                + DELIMITER + order.getTotalPrice() + DELIMITER + order.getCarColor() + DELIMITER + order.getOrderStatus();
    }

    public static String formatOrders (List<Order> orderlist){
        StringBuffer str = new StringBuffer();
        for (Order o : orderlist){
            str.append(formatOrder(o));
            str.append("\n");
        }
        return str.toString();
    }
}
