

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
import java.sql.ResultSet;


public class DisplayServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		String num = request.getParameter("num");
		
		try {
			String driver = "oracle.jdbc.driver.OracleDriver";
			Class.forName(driver);
			
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String uname = "devil";
			String pwd = "1234";
			String query = null;
			PreparedStatement ps = null;
			Connection con = DriverManager.getConnection(url,uname,pwd);
			if(num==null) {				
				query = "select * from LoginApp";
				ps = con.prepareStatement(query);
			}else {
				query = "select * from LoginApp where num = ?";
				ps = con.prepareStatement(query);
				ps.setInt(1,Integer.parseInt(num));
			}
			
			
			ResultSet rs = ps.executeQuery(); 
						
			out.print("<table border = '1' ><tr>");
			out.print("<th>Num</th><th>Name</th><th>Balance</th></tr>");
			while(rs.next()) {
			out.print("<tr><td>"+rs.getInt(1)+"</td><td>"+rs.getString(2)+"</td><td>"+rs.getInt(3)+"</td></tr>");
			
			}out.println("</br></br>");
			out.println("</table>");
			con.close();
			RequestDispatcher rd = request.getRequestDispatcher("Success.html");
			rd.include(request, response);
			
		}catch(Exception e) {
			out.println("<h1>Exception :" + e.getMessage()+ "</h1>");
		}
	}

}
