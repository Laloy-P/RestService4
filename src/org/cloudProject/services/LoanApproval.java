package org.cloudProject.services;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.cloudProject.beans.Approval;
import org.cloudProject.model.FetchData;
import org.json.JSONArray;
import org.json.JSONObject;


@Path("/LoanApproval")
public class LoanApproval {

	@GET
	public JSONObject getApprovals() throws IOException {
		
		List<Approval> app = new ArrayList<>();
		
		app = FetchData.getApprovals();
		
		for(Approval a : app) {
			System.out.println(a.toString());
		}
		
		return null;
	}
	

	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void loanManager(@QueryParam("id") int id, @QueryParam("amount") float amount) throws IOException {

		if (amount > 10_000) {
			
			if(FetchData.getApprovalById(id).getResponse().equals("accepted")) {
				
				System.out.println("credit accepté");
				//TODO add ammount to account
				
				
			}else
				System.out.println("credit refusé !");
			
		}else {
			//TODO check risk from RestService3 if low : add amount to account if hight loan refused
		}
			

	}

}
