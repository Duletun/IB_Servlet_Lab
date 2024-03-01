package sus.chep1.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sus.chep1.other.TimedExit;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@WebServlet(name = "ExitServlet", value = "/exit")
public class ExitServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            TimedExit a = new TimedExit();
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/exit.jsp");
            requestDispatcher.forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }


}
