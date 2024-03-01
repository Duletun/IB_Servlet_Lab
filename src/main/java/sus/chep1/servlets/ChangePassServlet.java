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

@WebServlet(name = "ChangePassServlet", value = "/change-pass")
public class ChangePassServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
    }

    // Вариант 20. Чередование букв, цифр и снова букв.


    protected void check404(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getSession().getAttribute("Logged") == null){
            req.setAttribute("submitFlag", "Нет доступа");
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/404.jsp");
            requestDispatcher.forward(req, resp);
        }
        req.setAttribute("PassLimitStatus", "");

        if(req.getSession().getAttribute("Logged").toString().equals("ADMIN")){
            if((boolean)req.getSession().getAttribute("isNew")){
                req.setAttribute("newStatus", "администратора - первый вход");
                req.setAttribute("link", "/login");
            }else{
                req.setAttribute("newStatus", "администратора");
                req.setAttribute("link", "/admin-menu");
            }
        }else if((boolean)req.getSession().getAttribute("isNew")){
            if((boolean)req.getSession().getAttribute("passLimit")) {
                req.setAttribute("PassLimitStatus", "Ограничение на пароль: Чередование букв, цифр и снова букв.");
            }
            req.setAttribute("newStatus", "пользователя - первый вход");
            req.setAttribute("link", "/login");
        }else{
            if((boolean)req.getSession().getAttribute("passLimit")){
                if (!UserTable.isPassAfford(null, req.getSession().getAttribute("Logged").toString())){
                    req.setAttribute("newStatus", " - обновилось ограничение на пароль");
                    req.setAttribute("link", "/login");
                }else{
                    req.setAttribute("newStatus", "пользователя");
                    req.setAttribute("link", "/user-menu");
                }
                req.setAttribute("PassLimitStatus", "Ограничение на пароль: Чередование букв, цифр и снова букв.");
            }else{
                req.setAttribute("newStatus", "пользователя");
                req.setAttribute("link", "/user-menu");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        check404(req,resp);
        req.setAttribute("status", "Заполните, пожалуйста, формы");
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/change-pass.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        check404(req,resp);
        String old_pass = UserTable.toSHA256(req.getParameter("old_pass"));
        if (req.getSession().getAttribute("isNew").toString() == "true"){
            old_pass = "";
        }
        String new_pass = UserTable.toSHA256(req.getParameter("new_pass"));
        String check_pass = UserTable.toSHA256(req.getParameter("check_pass"));
        String login = req.getSession().getAttribute("Logged").toString();

        if (req.getParameter("b1") != null) {
            if (UserTable.checkPass(login, old_pass)){
                if (new_pass.equals(check_pass)) {
                    if(!new_pass.equals("")) {
                        if(UserTable.isUserPassLimited(login)) {
                            if (UserTable.isPassAfford(new_pass, null)){
                                UserTable.changePass(login, old_pass, new_pass);
                                req.getSession().setAttribute("isNew", false);
                                req.setAttribute("status", "Пароль успешно изменен");
                            }else{
                                req.setAttribute("status", "Пароль не удовлетворяет условиям его ограничения.");
                            }
                        } else{
                            UserTable.changePass(login, old_pass, new_pass);
                            req.getSession().setAttribute("isNew", false);
                            req.setAttribute("status", "Пароль успешно изменен");
                        }
                    }else{
                        req.setAttribute("status", "Пароль не может быть пустым.");
                    }

                }else{
                    req.setAttribute("status", "Подтверждение введено неверно");
                }
            }else{
                req.setAttribute("status", "Старый пароль введен неверно");
            }
        }
        check404(req,resp);
        req.getRequestDispatcher("views/change-pass.jsp").forward(req, resp);

    }
}
