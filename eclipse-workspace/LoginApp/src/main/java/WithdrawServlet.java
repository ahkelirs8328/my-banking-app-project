

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

public class WithdrawServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	PrintWriter out = response.getWriter();
	response.setContentType("text/html");
	
	int num = Integer.parseInt(request.getParameter("num"));
	int amount = Integer.parseInt(request.getParameter("amount"));
	
	try {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		Class.forName(driver);
		Connection con = DriverManager.getConnection(url,"devil","1234");
		
		String query = "update LoginApp set balance = balance - ? where num = ?";
		PreparedStatement ps = con.prepareStatement(query);
		
		ps.setInt(1, amount);
		ps.setInt(2, num);
		
		int count = ps.executeUpdate();
		if (count == 0) {
			out.println("<h3>Error: No Such Account Found</h3>");
			out.println("<h3>Enter Details Again</h3>");
			RequestDispatcher rd = request.getRequestDispatcher("Withdraw.html");
			rd.include(request, response);
		
		}else {
			out.println("Successfuly Withdraw......");
			RequestDispatcher rd = request.getRequestDispatcher("Success.html");
			rd.include(request, response);
		}
		
		
	}catch(Exception e) {
		out.println(e.getMessage());
	}
	}

}
