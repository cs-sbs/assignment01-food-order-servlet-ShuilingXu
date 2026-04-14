package cs.sbs.web.servlet;

import cs.sbs.web.model.MenuItem;
import cs.sbs.web.model.Order;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class OrderCreateServlet extends HttpServlet {

    private static final List<MenuItem> MENU = new ArrayList<>();
    static {
        MENU.add(new MenuItem("Fried Rice", 8));
        MENU.add(new MenuItem("Fried Noodles", 9));
        MENU.add(new MenuItem("Burger", 10));
    }

    // 处理 POST：创建订单
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        String customer = req.getParameter("customer");
        String food = req.getParameter("food");
        String quantityStr = req.getParameter("quantity");

        // 参数非空校验
        if (customer == null || customer.trim().isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("Error: Customer name cannot be empty");
            return;
        }
        if (food == null || food.trim().isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("Error: Food name cannot be empty");
            return;
        }
        if (quantityStr == null || quantityStr.trim().isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("Error: Quantity cannot be empty");
            return;
        }

        // 数量合法性校验
        int quantity;
        try {
            quantity = Integer.parseInt(quantityStr.trim());
            if (quantity <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("Error: Quantity must be a valid positive number");
            return;
        }

        // ---------- 修正点：支持模糊匹配食物名称 ----------
        String userInput = food.trim();
        MenuItem matchedItem = null;

        // 1. 精确匹配（不区分大小写）
        for (MenuItem item : MENU) {
            if (item.getName().equalsIgnoreCase(userInput)) {
                matchedItem = item;
                break;
            }
        }
        // 2. 若精确匹配失败，尝试包含匹配（用户输入是菜单项名称的子串）
        if (matchedItem == null) {
            for (MenuItem item : MENU) {
                if (item.getName().toLowerCase().contains(userInput.toLowerCase())) {
                    matchedItem = item;
                    break;
                }
            }
        }

        if (matchedItem == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("Error: Food '" + food + "' is not on the menu. Available: Fried Rice, Fried Noodles, Burger");
            return;
        }

        String standardFoodName = matchedItem.getName();
        Order newOrder = Order.createOrder(customer.trim(), standardFoodName, quantity);

        out.println("Order Created: " + newOrder.getId());
        out.println("Customer: " + newOrder.getCustomer());
        out.println("Food: " + newOrder.getFood());
        out.println("Quantity: " + newOrder.getQuantity());
    }

    // 处理 GET：返回所有订单的 HTML 列表（用于 order.html 异步加载）
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        List<Order> orders = Order.getAllOrders();
        out.println("<div style='margin-top:20px;'>");
        out.println("<h3>📋 已创建的订单</h3>");
        if (orders.isEmpty()) {
            out.println("<p>暂无订单，请填写表单创建。</p>");
        } else {
            out.println("<ul style='list-style:none; padding-left:0;'>");
            for (Order order : orders) {
                out.printf("<li style='margin-bottom:10px; padding:8px; background:#fef6e8; border-radius:8px;'>"
                                + "订单 #%d - %s (%s x%d) "
                                + "<a href='/order/%d' target='_blank' style='margin-left:15px; color:#ff7b2c;'>查看详情 🔍</a>"
                                + "</li>",
                        order.getId(), order.getCustomer(), order.getFood(),
                        order.getQuantity(), order.getId());
            }
            out.println("</ul>");
        }
        out.println("</div>");
    }
}