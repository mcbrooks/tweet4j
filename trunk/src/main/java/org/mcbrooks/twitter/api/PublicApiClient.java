/**
 * 
 */
package org.mcbrooks.twitter.api;

import java.util.List;

import org.apache.commons.httpclient.methods.GetMethod;
import org.dom4j.Document;
import org.mcbrooks.twitter.Status;
import org.mcbrooks.twitter.User;

/**
 * 
 * @author mcb
 *
 */
public class PublicApiClient extends ApiClient implements PublicApi
{
  /* (non-Javadoc)
   * @see org.mcbrooks.twitter.api.PublicApi#getPublicTimeline()
   */
  public List<Status> getPublicTimeline() throws TwitterException 
  {
    // get method
    GetMethod method = new GetMethod(baseUrl + "statuses/public_timeline.xml");
    
    // get document
    Document doc = executeMethod(method);
    
    // parse document
    return parseStatuses(doc);
  }

  /* (non-Javadoc)
   * @see org.mcbrooks.twitter.api.PublicApi#getStatus(java.lang.Integer)
   */
  public Status getStatus(Integer id) throws TwitterException 
  {
    // get method
    GetMethod method = new GetMethod(baseUrl + "statuses/show/" + id + ".xml");
      
    // get document
    Document doc = executeMethod(method);
      
    // parse document
    return parseStatus(doc);
  }

  /* (non-Javadoc)
   * @see org.mcbrooks.twitter.api.PublicApi#getUser(java.lang.Integer)
   */
  public User getUser(Integer id) throws TwitterException
  {
    return getUserById(id);
  }

  /* (non-Javadoc)
   * @see org.mcbrooks.twitter.api.PublicApi#getUser(java.lang.String)
   */
  public User getUser(String screenName) throws TwitterException 
  {
    return getUserById(screenName);
  }
  
  /* (non-Javadoc)
   * @see org.mcbrooks.twitter.api.PublicApi#getUserByEmail(java.lang.String)
   */
  public User getUserByEmail(String email) throws TwitterException 
  {
    // TODO: validate email?
      
    // get method
    GetMethod method = new GetMethod(baseUrl + "users/show.xml?email=" + email);
        
    // get document
    Document doc = executeMethod(method);
        
    // parse document
    return parseUser(doc);
  }
  
  //
  // private implementations
  //
  
  /**
   * Private implementation of getUser for both a String and an Integer.
   * 
   * @param id
   * @return
   * @throws TwitterException
   */
  private User getUserById(Object id) throws TwitterException
  {
    // get method
    GetMethod method = new GetMethod(baseUrl + "users/show/" + id + ".xml");
      
    // get document
    Document doc = executeMethod(method);
        
    // parse document
    return parseUser(doc);
  }

}
