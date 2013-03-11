package services;

import java.io.File;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import yjava.ws.util.YahooResource;

@Path("/")
public class CityGuideService extends YahooResource  {
	@GET
	@Path("/cityguide/{id}")
	@Produces("application/pdf")
	public Response generateCityGuide(
			@DefaultValue("191501967") @PathParam("id") String id) {
		Status statusOK = Status.OK;
		String FILE_PATH = "/tmp/pdf/" + id + ".pdf";
		File file = new File(FILE_PATH);
		return Response.status(statusOK).entity(file).build();
	}
	
}
