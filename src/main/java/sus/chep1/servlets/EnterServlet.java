package sus.chep1.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sus.chep1.database.User;
import sus.chep1.database.UserTable;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@WebServlet(name = "EnterServlet", value = "/enter")
public class EnterServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("submitFlag", "Введите, пожалуйста, парольную фразу");
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/enter.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String key = req.getParameter("key");

        if (req.getParameter("b1") != null) {
            if (UserTable.isFileEmpty()){
                req.getSession().setAttribute("Unlocked", true);
                User admin = new User("ADMIN", "");
                UserTable.addUser(admin);
                resp.sendRedirect("/login");
            }else {
                try {
                    if (UserTable.readFile(key) == true) {
                        req.getSession().setAttribute("Unlocked", true);
                        resp.sendRedirect("/login");
                    } else {
                        resp.sendRedirect("/exit");
                    }
                } catch (IOException | InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException |
                         BadPaddingException | IllegalBlockSizeException e) {
                    throw new RuntimeException(e);
                }
            }
        } else if (req.getParameter("b2") != null) {
            System.exit(0);
        }
    }


}
