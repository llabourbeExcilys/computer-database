import javax.servlet.http.HttpServlet;

public class HelloWorld extends HttpServlet{
	
	public  void doGet(HttpServletRequest request, HttpServletResponse response)
			 throws ServletException, IOException  {
			
				response.setContentType("text/html") ;
				
				PrintWriter out = response.getWriter() ;
				out.println("<html>") ;
				out.println("<head>") ;
				out.println("<title>Bonjour le monde !</title>") ;
				out.println("</head>") ;
				out.println("<body>") ;
				out.println("<h1>Bonjour le monde !</h1>") ;
				out.println("</body>") ;
				out.println("</html>") ;   
			}

			 public  void doPost(HttpServletRequest request, HttpServletResponse response)
			 throws ServletException, IOException  {
				doGet(request, response) ;
			}

}
