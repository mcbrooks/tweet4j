/**
 * 
 */
package org.mcbrooks.twitter.api;

import org.apache.commons.httpclient.methods.GetMethod;
import org.dom4j.Document;


/**
 * @author mcb
 *
 */
public class SearchApiClient extends ApiClient implements SearchApi
{
  /** default search URL */
  private String baseUrl = "http://search.twitter.com/search.atom";

  public SearchApiClient() {}

  public SearchApiClient(String url)
  {
    baseUrl = url;
  }
  
  
  //
  // public api methods
  //
  
  public Document search(String query) throws TwitterException
  {
    // create get method
    GetMethod method = new GetMethod(baseUrl);
    method.setQueryString("q=" + query);

    // return dom4j document
    return executeMethod(method);
  }

}
