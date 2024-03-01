package sus.chep1.servlets;

import sus.chep1.database.User;
import sus.chep1.database.UserTable;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@WebServlet(name = "SubmitUserServlet", value = "/add-user")
public class SubmitUserServlet extends HttpServlet {

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
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/add-user.jsp");
        req.setAttribute("submitFlag", "Заполните, пожалуйста, формы");
        requestDispatcher.forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        check404(req,resp);
        String login = req.getParameter("login");
        String password = "";


        User user = new User(login, password);

        boolean submitFlag = UserTable.addUser(user);

        if (submitFlag == false){
            req.setAttribute("submitFlag", "Создание данного пользователя невозможно");
        }else{
            req.setAttribute("submitFlag", "Пользователь успешно создан");
        }

        req.getRequestDispatcher("views/add-user.jsp").forward(req, resp);
    }


}
