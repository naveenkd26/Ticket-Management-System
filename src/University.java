import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/getData")
public class University {


	public University() {
		// TODO Auto-generated constructor stub
	}

	@GET
	public String getData(){
		
		return "Hello World";
	}
	
}
