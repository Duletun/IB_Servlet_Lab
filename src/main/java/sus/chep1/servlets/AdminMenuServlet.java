package sus.chep1.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sus.chep1.database.User;
import sus.chep1.database.UserTable;

import java.io.IOException;
import java.sql.PreparedStatement;

@WebServlet(name = "AdminMenuServlet", value = "/admin-menu")
public class AdminMenuServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
    }

    protected void check404(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object a = req.getSession().getAttribute("Logged");
        if(a == null || !a.toString().equals("ADMIN")){
            req.setAttribute("submitFlag", "Нет доступа");
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/404.jsp");
            requestDispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        check404(req,resp);
        if (req.getSession().getAttribute("isNew") != null && (boolean)req.getSession().getAttribute("isNew") == true){
            resp.sendRedirect("/change-pass");
        }else{
            req.setAttribute("aMenuStatus", "");
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/admin-menu.jsp");
            requestDispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        check404(req,resp);
        if (req.getParameter("b1") != null) {
            resp.sendRedirect("/change-pass");
        } else if (req.getParameter("b2") != null) {
            resp.sendRedirect("/add-user");
        } else if (req.getParameter("b3") != null) {
            if (UserTable.getUsers().size() != 0){
                resp.sendRedirect("/user-list");
            }else{
                req.setAttribute("aMenuStatus", "Ошибка, нет зарегистрированных пользователей");
                req.getRequestDispatcher("views/admin-menu.jsp").forward(req, resp);
            }

        }
    }


}
