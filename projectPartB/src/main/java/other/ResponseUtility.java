package other;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Marc Weiss
 * Utility class which facilitate sending response
 * and writing to log with log4j at the same time
 */
public class ResponseUtility {
	private static final Logger log4j = LogManager.getLogger("cpFileLogger");

	/**
	 * @param id, to identify the user
	 * @param theResponse, a string added to pinpoint the response
	 * @param http status code in response
	 * @return Response
	 */
	public  static Response myResponse( String id,String theResponse, Status status){
		log4j.trace(id+ "trace message:" + theResponse + "---trace status code:" + status);
		GenericEntity<String> entity = new GenericEntity<String>(theResponse){};
		return Response.ok(entity,MediaType.TEXT_PLAIN).status(status).build();	
	}

	/**
	 * @param id, to identify the user
	 * @param http status code in response
	 * @return Response
	 */
	public static Response myResponse(String id,Status status){
		log4j.trace(id+ "---trace status code:" + status);
		return Response.status(status).build();	
	}
	
	
	/**
	 * Method who send a Response
	 * indicating the client must login
	 * @param userType
	 * @return Response
	 */
	public static Response badLogin(String userType) {
		return myResponse("noId "+userType, Status.RESET_CONTENT);
	}
}
