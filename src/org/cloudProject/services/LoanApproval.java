package org.cloudProject.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.cloudProject.beans.Approval;
import org.cloudProject.model.ManageData;
import org.json.JSONObject;

@Path("/LoanApproval")
public class LoanApproval {

	@GET
	public JSONObject getApprovals() throws IOException {

		List<Approval> approvals = new ArrayList<>();
		approvals = ManageData.getApprovals();

		for (Approval a : approvals) {
			System.out.println(a.toString());
		}

		return null;
	}
	


	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void loanManager(@QueryParam("id") int id, @QueryParam("amount") float amount) throws IOException {
		
		JSONObject addJson = new JSONObject();
		addJson.put("amount", amount);
		addJson.put("id", id);
		
		try {
			System.out.println(id + " " + amount);
			if (amount > 10_000) {
				System.out.print("superieur a 10 000");

				if (ManageData.getApprovalById(id).getResponse().equals("accepted")) {

					System.out.println("credit accepté");
					ManageData.sendPutRequest( addJson.toString());

				} else
					System.out.println("credit refusé !");

			} else {
				
				if (ManageData.getRisk(id).equals("high")) {
					System.out.print("risque elevé : credit refus");

				} else if (ManageData.getRisk(id).equals("low")) {

					ManageData.sendPutRequest( addJson.toString());
				}

			}
		} catch (Exception e) {

			System.out.println("erreur 500");

		}
	}
  
}
