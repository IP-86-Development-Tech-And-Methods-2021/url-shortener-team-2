package edu.kpi.developmentmethods;

import io.micronaut.runtime.Micronaut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the main application class for our project
 *
 * Run it to start the application
 */
public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    /**
     * Entrypoint
     */
    public static void main(String[] args) throws Exception {
 String longUrl = &quot;https:
 
 System.out.println(&quot;Long url: &quot; + longUrl+&quot;\n&quot;);
 
 System.out.println(&quot;Bitly short url: &quot;+shorten(&quot;Bitly&quot;, BITLY_KEY, longUrl)+&quot;\n&quot;);
 System.out.println(&quot;Google short url: &quot; + shorten(&quot;Google&quot;, GOOGLE_KEY, longUrl));
}
 
public static String shorten(String authority, String key, String longUrl) throws Exception {
 if (&quot;Bitly&quot;.equals(authority)) {
  String endpoint = &quot;https:
   + &quot;/v3/shorten?access_token=&quot; + key
   + &quot;&amp;longUrl=&quot; + longUrl;
 
  longUrl = longUrl.replace(&quot;:&quot;, &quot;%3A&quot;);
  longUrl = longUrl.replace(&quot;/&quot;, &quot;%2F&quot;);
  longUrl = longUrl.replace(&quot;&amp;&quot;, &quot;%26&quot;);
  longUrl = longUrl.replace(&quot; &quot;, &quot;%20&quot;);
 
  java.net.URL url = new java.net.URL(endpoint);
  java.net.HttpURLConnection request = (java.net.HttpURLConnection) url.openConnection();
 
  request.setRequestMethod(&quot;GET&quot;);
  request.connect();
 
  java.io.BufferedReader reader = new java.io.BufferedReader(
   new java.io.InputStreamReader(request.getInputStream()));
 
  String serviceResponse = &quot;&quot;;
  String line;
 
  while ((line = reader.readLine()) != null) {
   serviceResponse += line;
  }
 
  System.out.println(&quot;Service response: &quot;+serviceResponse);
 
  return extractUrl(serviceResponse, &quot;url&quot;);
 }
 else if (&quot;Google&quot;.equals(authority)) {
  String endpoint = &quot;https://www.googleapis.com/&quot;
   + &quot;urlshortener/v1/url?fields=id%2ClongUrl&amp;key=&quot; + key;
 
  String data = &quot;{\&quot;longUrl\&quot;: \&quot;&quot; + longUrl + &quot;\&quot;}&quot;;
 
  java.net.URL url = new java.net.URL(endpoint);
  java.net.HttpURLConnection connection = (java.net.HttpURLConnection) url.openConnection();
  connection.setRequestMethod(&quot;POST&quot;);
  connection.setDoOutput(true);
  connection.setRequestProperty(&quot;Content-Type&quot;, &quot;application/json&quot;);
 
  java.io.OutputStreamWriter output = new java.io.OutputStreamWriter(connection.getOutputStream());
  output.write(data);
  output.flush();
 
  java.io.BufferedReader response = new java.io.BufferedReader(
   new java.io.InputStreamReader(connection.getInputStream()));
 
  String serviceResponse = &quot;&quot;;
  String line;
  while ((line = response.readLine()) != null) {
   serviceResponse += line;
  }
 
  System.out.println(&quot;Service response: &quot;+serviceResponse);
 
  return extractUrl(serviceResponse, &quot;id&quot;);
 
 }
 
 return null;
}
 
public static String extractUrl(String serviceResponse, String keyword){
 serviceResponse = serviceResponse.replace(&quot; &quot;, &quot;&quot;);
 keyword = &quot;\&quot;&quot;+keyword+&quot;\&quot;:\&quot;&quot;;
 int keywordLength = keyword.length();
 
 for (int i = 0; i &lt; serviceResponse.length(); i++) {
  if (i &lt; serviceResponse.length() - keywordLength &amp;&amp; keyword.equals(serviceResponse.substring(i, i + keywordLength))) {
   for (int j = i + keywordLength; j &lt; serviceResponse.length(); j++) {
    if (&quot;\&quot;&quot;.equals(serviceResponse.substring(j, j + 1))) {
     return serviceResponse.substring(i + keywordLength, j);
    }
   }
  }
 }
 
return null;
}
}
