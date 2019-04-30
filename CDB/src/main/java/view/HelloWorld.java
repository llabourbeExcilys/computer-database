package view;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet( name = "HelloWorldServlet",
description = "Testing servlet",
urlPatterns = {"/"})
public class HelloWorld extends HttpServlet {

	private static final long serialVersionUID = 5700829257941123519L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		/*
		 * response.setContentType("text/html") ;
		 * 
		 * PrintWriter out = response.getWriter() ; out.println("<html>") ;
		 * out.println("<head>") ; out.println("<title>Bonjour le monde !</title>") ;
		 * out.println("</head>") ; out.println("<body>") ;
		 * out.println("<h1>Bonjour le monde !</h1>") ; out.println("</body>") ;
		 * out.println("</html>") ;
		 */

		getServletContext().getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
