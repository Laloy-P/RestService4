package org.cloudProject.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.cloudProject.beans.Approval;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ManageData {

	public static String sendPutRequest(String payload) {
		String requestUrl = "https://projet-iut.herokuapp.com";
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setDoOutput(true);
			httpCon.setRequestMethod("PUT");
			OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
			out.write(payload);
			out.close();
			httpCon.getInputStream();

		} catch (Exception e) {
			System.err.println(e.getMessage());
			throw new RuntimeException(e.getMessage());

		}
		return "erreur lors du PUT";
	}

	public static String getRisk(int id) throws IOException {

		String webService3 = "https://projet-iut-service-3.appspot.com/checkAccount?id=" + id;

		URL url = new URL(webService3);

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestProperty("Content-Type", "text/plain; charset=utf-8");

		if (connection.getResponseCode() == 200) {
			BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String output;

			while ((output = bf.readLine()) != null) {

				String risk = output;
				System.out.println(output);
				connection.disconnect();
				return risk;
			}

		}
		connection.disconnect();
		return null;

	}

	public static JSONObject getJsonApprovals() throws IOException {

		String webService2 = "https://approvalmanagerservice.herokuapp.com/Service/ListApprovals";

		URL url = new URL(webService2);

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Accept", "application/json");

		if (connection.getResponseCode() == 200) {
			BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String output;

			while ((output = bf.readLine()) != null) {

				JSONObject approvals = new JSONObject(output);
				// JSONArray approvalName = approvals.getJSONArray("approval");
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

		for (Approval app : approvals) {

			if (id == app.getId()) {
				approval = app;
			}

		}

		return approval;
	}
}
