package org.cloudProject.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.cloudProject.beans.Approval;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FetchData {
	
	public static JSONObject getJsonApprovals() throws IOException{
		
		String webService2 = "https://approvalmanagerservice.herokuapp.com/Service/ListApprovals";
		
		URL url = new URL(webService2);
		
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Accept", "application/json");
		
		if (connection.getResponseCode() == 200) {
			BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String output;
			
			while((output = bf.readLine()) != null) {
				
				JSONObject approvals = new JSONObject(output);
				
				JSONArray approvalName = approvals.getJSONArray("approval");
				
				
				connection.disconnect();
				return approvals;
			}
			
		}
		connection.disconnect();
		return null;
		
	}
	
	public static List<Approval> getApprovals() throws JSONException, IOException {
		
		List<Approval> approvals = new ArrayList<>();
		JSONArray json = getJsonApprovals().getJSONArray("approval");
		
		for (int i = 0; i < json.length(); ++i) {
			
			Approval approval = new Approval();
		    JSONObject rec = json.getJSONObject(i);
		    approval.setId(rec.getInt("id"));
		    approval.setDescription(rec.getString("description"));
		    approval.setResponse(rec.getString("response"));
		    approval.setName(rec.getString("name"));
		    
		    approvals.add(approval);
		}
		return approvals;
	}
	
	public static Approval getApprovalById(int id) throws JSONException, IOException {
		
		List<Approval> approvals = new ArrayList<>();
		Approval approval = new Approval();
		approvals = getApprovals();
		
		for(Approval app : approvals) {
			
			if (id == app.getId()) {
				approval = app;
			}
			
		}

		return approval;
	}
}
