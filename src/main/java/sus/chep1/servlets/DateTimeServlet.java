package sus.chep1.servlets;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.ServletException;
import sus.chep1.database.UserTable;

import java.io.IOException;
import java.util.Date;

@WebServlet(name = "DateTimeServlet", value =  "/show-date-time")
public class DateTimeServlet extends HttpServlet {
    private Date date;

    @Override
    public void init() throws ServletException {
        super.init();
        date = new Date();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("date", date.toString());
        req.getRequestDispatcher("views/show-date-time.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        // and the rest of your method.
    }

}
