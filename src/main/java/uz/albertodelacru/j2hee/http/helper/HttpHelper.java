package uz.albertodelacru.j2hee.http.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpHelper {
    private String endpointUrl = null;
    private URL url = null;

    public HttpHelper(String endpointUrl){
        this.endpointUrl = endpointUrl;

        try {
            this.url = new URL(endpointUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public HttpHelper(String endpointUrl, Map<String, String> params){
        this.endpointUrl = endpointUrl + "?" + formatAllUrlParams(params);

        try {
            this.url = new URL(this.endpointUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public HttpHelper(String endpointUrl, String params){
        this.endpointUrl = endpointUrl + "?" + params;

        try {
            this.url = new URL(this.endpointUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public HttpHelper(URL url){
        this.url = url;
        this.endpointUrl = url.getFile();
    }

    public HttpHelper(URL url, Map<String, String> params){
        this.endpointUrl = url.getFile() + "?" + formatAllUrlParams(params);
        
        try {
            this.url = new URL(this.endpointUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public HttpHelper(URL url, String params){
        this.endpointUrl = url.getFile() + "?" + params;
        
        try {
            this.url = new URL(this.endpointUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        
    }

    private String formatUrlParam(String paramName, String paramValue){
        return paramName + "=" + paramValue;
    }

    private String formatAllUrlParams(Map<String, String> params){
        List<String> formatedParams = new ArrayList<>();

        for(Map.Entry<String, String> param : params.entrySet()){
            formatedParams.add( formatUrlParam(param.getKey(), param.getValue()) );
        }

        return String.join("&", formatedParams);
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
		HttpURLConnection con = null;

		try {
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
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));

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

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }
}
