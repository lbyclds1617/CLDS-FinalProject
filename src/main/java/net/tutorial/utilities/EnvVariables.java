package net.tutorial.utilities;


import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class EnvVariables {
	
	boolean hasVcap = false;
	JSONObject vcap = null;
	
	public EnvVariables() {
		String VCAP_SERVICES = System.getenv("VCAP_SERVICES");
				
		if (VCAP_SERVICES != null) {
			this.hasVcap = true;
			JSONParser parser = new JSONParser();
			Object obj;
			
			try {
				obj = parser.parse(VCAP_SERVICES);
				vcap =  (JSONObject) obj;
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public Map<String, String> getCredentials(String serviceName) {
		
		Map<String, String> creds = new HashMap<String, String>();
		
		if (this.hasVcap) {
			JSONArray serviceConfig = (JSONArray) vcap.get(serviceName);
	        JSONObject serviceInstance = (JSONObject) serviceConfig.get(0);
	        JSONObject serviceCreds = (JSONObject) serviceInstance.get("credentials");
			
			creds.put("jdbcUrl", serviceCreds.get("jdbcUrl").toString());
		} else {
			creds.put("jdbcUrl", "jdbc:mysql://us-cdbr-iron-east-03.cleardb.net/ad_a2bb3d0dc772c67?user=b3a17fd08c8a5a&password=033bff8e"); // Put username here if you are testing in local
		}
		
		return creds;
	}

}
