package sus.chep1.servlets;

import com.sun.tools.javac.util.StringUtils;
import sus.chep1.database.User;
import sus.chep1.database.UserTable;


import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletContext;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.SplittableRandom;

@WebServlet(name = "LoginUserServlet", value = "/login")
public class LoginUserServlet extends HttpServlet {

    private int sus = 0;

    @Override
    public void init() throws ServletException {
    }

    protected boolean check404(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object a = req.getSession().getAttribute("Unlocked");
        if(a == null || !(boolean)a == true){
            req.setAttribute("submitFlag", "Нет доступа");
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/403.jsp");
            requestDispatcher.forward(req, resp);
            return true;
        }else {
            return false;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!check404(req, resp)) {
            req.getSession().setAttribute("Logged", null);
            req.setAttribute("LoginStatus", "Пожалуйста, заполните поля для входа");
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/login.jsp");
            requestDispatcher.forward(req, resp);
            UserTable.updateFile();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        check404(req, resp);
        String login = req.getParameter("login");
        String password = UserTable.toSHA256(req.getParameter("pass"));

        boolean isLogPassValid = UserTable.checkPass(login, password);
        if (req.getParameter("b1") != null) {
            if (isLogPassValid) {
                sus = 0;
                routing(req, resp, login);
            } else {
                sus++;
                if (sus > 2){
                    System.out.println("Трехкратный ввод неверного пароля, выход...");
                    System.exit(0);
                }
                req.setAttribute("LoginStatus", "Неверный логин / пароль, осталось попыток: " + (3-sus));
                req.getRequestDispatcher("views/login.jsp").forward(req, resp);
            }
        }else if (req.getParameter("b2") != null) {
            System.exit(0);
        }
    }

    private void routing(HttpServletRequest req, HttpServletResponse resp, String login) throws ServletException, IOException {
        req.getSession().setAttribute("Logged", login);
        req.getSession().setAttribute("isNew", UserTable.isUserNew(login));
        req.getSession().setAttribute("passLimit", UserTable.isUserPassLimited(login));

        if ("ADMIN".equals(login)) {
            resp.sendRedirect(req.getContextPath()+"/admin-menu");
        } else {
            if (UserTable.getUser(login).getBlockStatus() != true){
                resp.sendRedirect(req.getContextPath()+"/user-menu");
            }else{
                req.getSession().setAttribute("Logged", null);
                req.getSession().setAttribute("isNew", null);
                req.getSession().setAttribute("passLimit", null);
                req.setAttribute("submitFlag", "Пользователь \"" + login + "\" был заблокирован администратором");
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/404.jsp");
                requestDispatcher.forward(req, resp);
            }
        }
    }
}
