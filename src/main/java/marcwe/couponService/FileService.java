package marcwe.couponService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 * @author Marc Weiss
 * class to manage file upload and download
 */
@Path("/file")
public class FileService {
	
	/**
	 * File object representing folder location
	 */
	File filePath = new File("/cpStemp");

	/**
	 * Method to upload a file
	 * @param uploadedInputStream
	 * @param fileDetail
	 * @return
	 */
	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(
		@FormDataParam("file") InputStream uploadedInputStream,
		@FormDataParam("file") FormDataContentDisposition fileDetail) {

//		check for directory
		File upLoadiretory = new File("c:/"+filePath.toString());
		upLoadiretory.mkdirs();
		
//		building file name
		String fileName = "";
		
		try {
			fileName = Long.toString(getIndex())+"_" ;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		fileName += fileDetail.getFileName();
		String uploadedFileLocation = filePath.toString()+ "/" + fileName;

//		save it
		writeToFile(uploadedInputStream, uploadedFileLocation);
		
//		send the filename as response to indicate successful operation
		GenericEntity<String> entity = new GenericEntity<String>(fileName){};
		return Response.ok(entity,MediaType.TEXT_PLAIN).status(Status.ACCEPTED).build();	
		
	}
	
	/**
	 * Method to download image
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	@GET
	@Path("/{filename}")
	public Response getImage(@PathParam("filename") String fileName) throws IOException{
		
		//getting file
		if (fileName == null) {
			return Response.status(Status.NOT_FOUND).build();
        }
        File file = new File(filePath, URLDecoder.decode(fileName, "UTF-8"));
        if (!file.exists()) {
			file = new File("C://Users/happyCouple/Desktop/workspace/couponProject02/couponService/src/main/webapp/image/error.jpg");
			
        }
        String fileExtention = getExtention(fileName);
		GenericEntity<File> entity = new GenericEntity<File>(file){};
		return Response.ok(entity,new MediaType("image",fileExtention)).build();
	}
	

	
	/**
	 * IO method to save a file to new location
	 * @param uploadedInputStream
	 * @param uploadedFileLocation
	 */
	private void writeToFile(InputStream uploadedInputStream, String uploadedFileLocation) {

		try {
			OutputStream out = new FileOutputStream(new File(
					uploadedFileLocation));
			int read = 0;
			byte[] bytes = new byte[1024];

			out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	
	/**
	 * Method to get the extension of the specified file
	 * @param filePath
	 * @return
	 */
	private String getExtention(String filePath){
		String extension = "";
		int i = filePath.lastIndexOf('.');
		if (i > 0) {
		    extension = filePath.substring(i+1);
		}
		return extension;
	}

	/**
	 * getting index for files
	 * used to make sure file name are unique
	 * @return
	 * @throws IOException
	 */
	private long getIndex() throws IOException{
		return Files.list(Paths.get("/cpStemp")).count()+1;
	}

}
