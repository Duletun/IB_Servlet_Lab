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
import java.util.ArrayList;

@WebServlet(name = "UserListServlet", value = "/user-list")
public class UserListServlet extends HttpServlet {

    private int user_id;

    @Override
    public void init() throws ServletException {
        user_id = 0;
    }

    protected void check404(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object a = req.getSession().getAttribute("Logged");
        if(a == null || !a.toString().equals("ADMIN")){
            req.setAttribute("submitFlag", "Нет доступа");
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/404.jsp");
            requestDispatcher.forward(req, resp);
        }
    }

    protected boolean selected(HttpServletRequest req, HttpServletResponse resp, String name) throws ServletException, IOException {
        return req.getParameter(name) != null;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        check404(req,resp);
        if(UserTable.getUsers().size() != 0){
            req.setAttribute("id_User", UserTable.getUsers().get(user_id));
            req.setAttribute("Status", "Пользователь с ID: " + user_id);
            req.getRequestDispatcher("views/user-list.jsp").forward(req, resp);
        }else{
            req.setAttribute("aMenuStatus", "Ошибка, нет зарегистрированных пользователей");
            req.getRequestDispatcher("views/admin-menu.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        check404(req,resp);
        String login = req.getParameter("login");
        String password = "";


        if (req.getParameter("<b") != null) {
            if(user_id>0){
                user_id--;
                req.setAttribute("id_User", UserTable.getUsers().get(user_id));
                req.setAttribute("Status", "Пользователь с ID: " + user_id);
                req.getRequestDispatcher("views/user-list.jsp").forward(req, resp);
            }else{
                req.setAttribute("id_User", UserTable.getUsers().get(user_id));
                req.setAttribute("Status", "Перейти к предыдущему элементу нельзя, т.к. Пользователь с ID: " + user_id +" крайний.");
                req.getRequestDispatcher("views/user-list.jsp").forward(req, resp);
            }

        } else if (req.getParameter("b>") != null) {
            if(user_id+1<UserTable.getUsers().size()){
                user_id++;
                req.setAttribute("id_User", UserTable.getUsers().get(user_id));
                req.setAttribute("Status", "Пользователь с ID: " + user_id);
                req.getRequestDispatcher("views/user-list.jsp").forward(req, resp);
            }else{
                req.setAttribute("id_User", UserTable.getUsers().get(user_id));
                req.setAttribute("Status", "Перейти к следующему элементу нельзя, т.к. Пользователь с ID: " + user_id +" крайний.");
                req.getRequestDispatcher("views/user-list.jsp").forward(req, resp);
            }

        } else if (req.getParameter("b3") != null) {
            if (UserTable.setStatus(login,selected(req,resp,"block"), selected(req,resp,"pass_block"))){
                int index = 0;
                for (User a: UserTable.getUsers()) {
                    if(a.getLogin().equals(login)){
                        break;
                    }
                    index++;
                }
                user_id = index;
                req.setAttribute("id_User", UserTable.getUsers().get(user_id));
                req.setAttribute("Status", "Пользователь с ID: " + user_id + " успешно изменен");

            }else{
                req.setAttribute("id_User", UserTable.getUsers().get(user_id));
                req.setAttribute("Status", "Пользователь не найден или не изменен, возврат к ID: " + user_id);
            }
            req.getRequestDispatcher("views/user-list.jsp").forward(req, resp);
        }
    }


}
