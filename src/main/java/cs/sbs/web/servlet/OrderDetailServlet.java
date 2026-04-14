package cs.sbs.web.servlet;

import cs.sbs.web.model.Order;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

public class OrderDetailServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("Error: Order ID is required");
            return;
        }

        // 提取订单ID，例如 /1001 → 1001
        String idStr = pathInfo.substring(1);
        int orderId;
        try {
            orderId = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("Error: Invalid order ID format");
            return;
        }

        Order order = Order.findById(orderId);
        if (order == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            out.println("Error: Order not found with ID: " + orderId);
            return;
        }

        out.println("Order Detail");
        out.println("Order ID: " + order.getId());
        out.println("Customer: " + order.getCustomer());
        out.println("Food: " + order.getFood());
        out.println("Quantity: " + order.getQuantity());
    }
}