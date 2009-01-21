/**
 * 
 */
package org.mcbrooks.twitter.api;

import java.util.List;

import org.mcbrooks.twitter.Status;
import org.mcbrooks.twitter.User;

/**
 * Interface representing the public Twitter API (not requiring user
 * authentication): all method descriptions copied verbatim from the Twitter API
 * wiki: <http://apiwiki.twitter.com/REST+API+Documentation>
 * 
 * @author mcb
 */
public interface PublicApi
{
  /**
   * Returns the 20 most recent statuses from non-protected users who have set a
   * custom user icon.
   * 
   * @return
   */
  public List<Status> getPublicTimeline() throws TwitterException;

  /**
   * Returns a single status, specified by the id parameter. The status's author
   * will be returned inline.
   * 
   * @param id - of the status to retrieve
   * @return
   */
  public Status getStatus(Integer id) throws TwitterException;

  /**
   * Returns extended information of a given user, specified by ID as per the
   * required id parameter below. This information includes design settings, so
   * third party developers can theme their widgets according to a given user's
   * preferences. You must be properly authenticated to request the page of a
   * protected user. So calls to the Public API for protected users will throw
   * an exception
   * 
   * @param id - numeric id of the user
   * @return user
   */
  public User getUser(Integer id) throws TwitterException;

  /**
   * Returns extended information of a given user, specified by screen name as
   * per the required screen name parameter below. This information includes
   * design settings, so third party developers can theme their widgets
   * according to a given user's preferences. You must be properly authenticated
   * to request the page of a protected user. So calls to the Public API for
   * protected users will throw an exception
   * 
   * @param screenName - screen name of the user
   * @return user
   */
  public User getUser(String screenName) throws TwitterException;

  /**
   * Returns extended information of a given user, specified by an email address
   * as per the required email parameter below. This information includes design
   * settings, so third party developers can theme their widgets according to a
   * given user's preferences. You must be properly authenticated to request the
   * page of a protected user. So calls to the Public API for protected users
   * will throw an exception
   * 
   * @param email - email address of the user
   * @return user
   */
  public User getUserByEmail(String email) throws TwitterException;
}
