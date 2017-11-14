package marcwe.couponService;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import cpCore.CouponException;
import entry.CouponSystem;
import facade.Facade;
import org.apache.logging.log4j.Logger; 
import org.apache.logging.log4j.LogManager;

/**
 * @author Marc Weiss
 * Servlet responding to login and logout request
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;     
	private static final Logger log4j = LogManager.getLogger("cpFileLogger");
	
    public Login() {
        super();
    }

    
	/** 
	 * Handling HTTP POST request
	 * Method to check user name and password with database
	 * If successful associate a facade with the session in the container,
	 * and redirect to the correct page
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log4j.trace("starting logging");
		HttpSession ss = request.getSession();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String userType = request.getParameter("userType");
		String remember = request.getParameter("remember");
		log4j.trace(username);
		try {
			Facade facade = CouponSystem.getInstance().login(username, password, userType);
			if(facade==null) {
				log4j.trace("tracing bad logging");
				throw new CouponException("bad login");
			}
			log4j.trace("tracing logging succesfull as: " + username + " usertype: "+ userType);
			ss.setAttribute("facade", facade);
			
			if(remember!=null){
				
				Cookie cookieName = new Cookie ("username",username);
				cookieName.setMaxAge(60*60*24*14);
				cookieName.setPath("/");
				
				Cookie cookiePas = new Cookie ("password",password);
				cookiePas.setMaxAge(60*60*24*14);
				cookiePas.setPath("/");

				Cookie cookieType = new Cookie ("userType",userType);
				cookieType.setMaxAge(60*60*24*14);
				cookieType.setPath("/");

				response.addCookie(cookieName);
				response.addCookie(cookiePas);
				response.addCookie(cookieType);}
			
		response.sendRedirect("http://localhost:8080/couponService/"+userType+"/"+userType+"Window.html");
		} catch (CouponException  | NullPointerException e) {
			response.setContentType("text/html"); 
			log4j.trace("tracing logging failed");
			request.setAttribute("message", "bad login man");
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}
	}
	
	/**
	 * Handling HTTP DELETE request
	 * invalidate session
	 */
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    log4j.trace("deleting facade");
		HttpSession session = request.getSession(false);
		if (session != null) {
		    session.invalidate();
		    log4j.trace("facade deleted");
		    }
	}

}
