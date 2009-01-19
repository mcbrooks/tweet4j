package org.mcbrooks.twitter.api;

import java.util.List;

import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.mcbrooks.twitter.Status;


public class UserApiClient extends PublicApiClient implements PublicApi
{
  // Logger logger = Logger.getLogger(UserApiClient.class);
  /** user name */
  // private String username;
  /** user password */
  // private String password;
  /**
   *  constructor 
   */
  public UserApiClient(String username, String password)
  {
    configureClient(username, password);
  }

  /*
   * public List<Status> getFriendsTimeline() throws TwitterException { // get
   * method GetMethod method = new
   * GetMethod("http://twitter.com/statuses/friends_timeline.xml"); //
   * method.setDoAuthentication(true); // execute method Document doc =
   * executeMethod(method); return parseStatuses(doc); }
   */

  /**
   * Sets a new user for an existing UserApiClient instance.
   * 
   * @param username
   * @param password
   */
  public void setNewUser(String username, String password)
  {
    configureClient(username, password);
  }

  //
  // private methods
  //
  private void configureClient(String username, String password)
  {
    // this.username = username;
    // this.password = password;
    // set auth credentials
    client.getState().setCredentials(new AuthScope("twitter.com", 80),
        new UsernamePasswordCredentials(username, password));
  }

}
