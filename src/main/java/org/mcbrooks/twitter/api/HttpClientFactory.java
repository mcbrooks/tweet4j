/**
 * 
 */
package org.mcbrooks.twitter.api;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.params.HttpClientParams;

/**
 * Abstract class for accessing a singleton HttpClient instance. The instance is configured with
 * reasonable default values, but you can override the client configuration manually.<p>
 * 
 * To configure the client:
 * <p>
 * <code><pre>
 * HttpClientParams params = new HttpClientParams();
 // Add parameters to the HttpClientParams object
 * ...
 * HttpClientFactory.configureClient(params);
 * </pre></code>
 * See more information on <a href="http://hc.apache.org/httpclient-3.x/apidocs/org/apache/commons/httpclient/params/HttpClientParams.html">
 * HttpClient configuration.</a> 
 * 
 * 
 * @author mcb
 *
 */
public abstract class HttpClientFactory 
{
  /** singleton HttpClient instance */
  private static final HttpClient client = new HttpClient();
  
  // http client initializer
  static {
    configureClient();
  }

  /**
   * Accesses the singleton HttpClient instance
   * 
   * @return client
   */
  protected static HttpClient getClient()
  {
    return client;
  }

  /**
   * Private configuration of HttpClient instance. Configures the instance with default values.
   */
  private static void configureClient() 
  {
    // Create connection manager
    MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
    client.setHttpConnectionManager(connectionManager);

    // Create and set appropriate parameter settings
    HttpClientParams clientParams = new HttpClientParams();
    
    //connections, superfluous?
    Map<HostConfiguration, Integer> hostConnect = new HashMap<HostConfiguration, Integer>();
    hostConnect.put(HostConfiguration.ANY_HOST_CONFIGURATION, 100);
    clientParams.setParameter("http.connection-manager.max-per-host", hostConnect);
    clientParams.setParameter("http.connection-manager.max-total", 100);
    
    // timeouts
    clientParams.setParameter("http.socket.timeout", 10000);
    clientParams.setParameter("http.connection.timeout", 5000);
    clientParams.setParameter("http.connection.stalecheck", true);
    clientParams.setParameter("http.connection-manager.timeout", 5000L);
    
    client.setParams(clientParams);

    // ignore cookies for maximum efficiency
    client.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
  }

  /**
   * Publicly available configuration of the HttpClient instance.
   * See more information on <a href="http://hc.apache.org/httpclient-3.x/apidocs/org/apache/commons/httpclient/params/HttpClientParams.html">
   * HttpClient configuration.</a> 
   * 
   * 
   * @param params - HttpClientParams provided by the user
   */
  public static void configureClient(HttpClientParams params)
  {
    client.setParams(params);
  }
}
