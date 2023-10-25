package uz.albertodelacru.jhxtable.http.manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpManager {
    private String endpointUrl = null;

    public HttpManager(String endpointUrl){
        this.endpointUrl = endpointUrl;
    }

    /**
	 * Manage HTTP Requests
	 * @param endpointUrl URL to call
	 * @return String with the html content of the URL
	 */
	public String makeHttpRequest(){
        // Check if endpoint is setted
        if( this.endpointUrl == null ){
            return null;
        }

		String result = null;
		URL url = null;
		HttpURLConnection con = null;

		try {
			url = new URL(this.endpointUrl);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Content-Type", "text/html");

			result = readHttpRequestContent(con);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if( con != null){
				con.disconnect();
			}
		}

		return result;
	}

    private String readHttpRequestContent( HttpURLConnection con ){
        String inputLine = null;
        StringBuilder content = null;

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

            content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return (content != null) ? content.toString() : null;
    }

    public String getEndpointUrl() {
        return endpointUrl;
    }

    public void setEndpointUrl(String endpointUrl) {
        this.endpointUrl = endpointUrl;
    }
}
