package cs.sbs.web.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Order {

    private int id;
    private String customer;
    private String food;
    private int quantity;

    // 静态订单存储（模拟数据库）
    private static final List<Order> orderDB = new ArrayList<>();
    private static final AtomicInteger idGenerator = new AtomicInteger(1002); // 改为1002，使第一个订单ID=1003

    public Order(int id, String customer, String food, int quantity) {
        this.id = id;
        this.customer = customer;
        this.food = food;
        this.quantity = quantity;
    }

    // 创建新订单并存入静态列表
    public static synchronized Order createOrder(String customer, String food, int quantity) {
        int newId = idGenerator.incrementAndGet(); // 第一次调用得到1003
        Order order = new Order(newId, customer, food, quantity);
        orderDB.add(order);
        return order;
    }

    // 根据ID查询订单
    public static Order findById(int id) {
        return orderDB.stream()
                .filter(o -> o.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // 获取所有订单（用于页面展示）
    public static List<Order> getAllOrders() {
        return new ArrayList<>(orderDB);
    }

    // Getter
    public int getId() {
        return id;
    }

    public String getCustomer() {
        return customer;
    }

    public String getFood() {
        return food;
    }

    public int getQuantity() {
        return quantity;
    }
}