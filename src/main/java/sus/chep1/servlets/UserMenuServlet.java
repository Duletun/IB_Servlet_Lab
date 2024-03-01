package sus.chep1.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sus.chep1.database.UserTable;

import java.io.IOException;

@WebServlet(name = "UserMenuServlet", value = "/user-menu")
public class UserMenuServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
    }

    protected void check404(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getSession().getAttribute("Logged") == null){
            req.setAttribute("submitFlag", "Нет доступа");
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/404.jsp");
            requestDispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        check404(req,resp);

        if (req.getSession().getAttribute("isNew") != null && (boolean)req.getSession().getAttribute("isNew")){
            resp.sendRedirect("/change-pass");
        }else if (req.getSession().getAttribute("passLimit") != null && (boolean)req.getSession().getAttribute("passLimit")){
            if(!UserTable.isPassAfford(null, req.getSession().getAttribute("Logged").toString())) {
                resp.sendRedirect("/change-pass");
            }else{
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/user-menu.jsp");
                requestDispatcher.forward(req, resp);
            }
        }else{
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/user-menu.jsp");
            requestDispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        check404(req,resp);
        if (req.getParameter("b1") != null) {
            resp.sendRedirect("/change-pass");
        }
    }


}
