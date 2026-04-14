package cs.sbs.web.servlet;

import cs.sbs.web.model.MenuItem;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MenuListServlet extends HttpServlet {

    // 静态菜单数据（模拟数据库）
    private static final List<MenuItem> MENU = new ArrayList<>();
    static {
        MENU.add(new MenuItem("Fried Rice", 8));
        MENU.add(new MenuItem("Fried Noodles", 9));
        MENU.add(new MenuItem("Burger", 10));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        String searchName = req.getParameter("name");
        List<MenuItem> result;

        if (searchName != null && !searchName.trim().isEmpty()) {
            String keyword = searchName.trim().toLowerCase();
            result = MENU.stream()
                    .filter(item -> item.getName().toLowerCase().contains(keyword))
                    .collect(Collectors.toList());
        } else {
            result = new ArrayList<>(MENU);
        }

        out.println("Menu List:");
        if (result.isEmpty()) {
            out.println("No menu items found.");
        } else {
            for (int i = 0; i < result.size(); i++) {
                MenuItem item = result.get(i);
                out.println((i + 1) + ". " + item.getName() + " - $" + item.getPrice());
            }
        }
    }
}