import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        String username = request.getParameter("username");
        String pwd = request.getParameter("pwd");
        String cnf = request.getParameter("cnf");

        String driver = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        if (cnf.equals(pwd)) {
            try {
                Class.forName(driver);
                Connection con = DriverManager.getConnection(url, "devil", "1234");

                String query = "INSERT INTO register(username, password) VALUES(?, ?)";
                PreparedStatement ps = con.prepareStatement(query);

                ps.setString(1, username);
                ps.setString(2, pwd);

                int count = ps.executeUpdate();

                if (count > 0) {
                    out.println("<h4 style='color:red'>Registration Successful</h4>");
                    RequestDispatcher rd = request.getRequestDispatcher("login.html");
                    rd.include(request, response);
                } else {
                    out.println("<h4 style='color:red'>Error: Registration Failed</h4>");
                    RequestDispatcher rd = request.getRequestDispatcher("register.html");
                    rd.include(request, response);
                }
            } catch (Exception e) {
                out.print("<h4 style='color:red'>Error: " + e.getMessage() + "</h4>");
            }
        } else {
            out.println("<h4 style='color:red'>Error: Password mismatch</h4>");
            RequestDispatcher rd = request.getRequestDispatcher("register.html");
            rd.include(request, response);
        }
    }
}
