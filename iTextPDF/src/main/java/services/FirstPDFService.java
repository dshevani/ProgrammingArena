package services;

import java.io.File;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import cityguides.FirstPDF;
import yjava.ws.util.YahooResource;

@Path("/")
public class FirstPDFService extends YahooResource {

	@GET
	@Path("/pdfguide")
	@Produces("application/pdf")
	public Response generateGuide(
			@DefaultValue("sample") @QueryParam("id") String id) {
		Status statusOK = Status.OK;
		Status statusNF = Status.NOT_FOUND;
		String FILE_PATH = "/tmp/pdf/" + id + ".pdf";
		File file = new File(FILE_PATH);
		if (file.exists()) {
			return Response.status(statusOK).entity(file).build();
		} else {
			try {
				FirstPDF.generatePDF(id);
			}
			catch (Exception e) {
				return Response.status(statusNF).entity(file).build();
			}
		}
		if (file.exists()) {
			return Response.status(statusOK).entity(file).build();
		}
		return Response.status(statusNF).entity(file).build();
	}
}
