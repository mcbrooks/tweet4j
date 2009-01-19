/**
 * 
 */
package org.mcbrooks.twitter.api;

import org.mcbrooks.twitter.DirectMessage;
import org.mcbrooks.twitter.Status;
import org.mcbrooks.twitter.User;
import org.mcbrooks.twitter.User.Device;


/**
 * @author mcb
 */
public interface UserApi
{
  //
  // status methods
  //

  // TODO: figure out optional parameter for status calls
  // public List<Status> getFriendsTimeline() throws TwitterException;
  // public List<Status> getUserTimeline() throws TwitterException;
  // public Status updateStatus() throws TwitterException;
  // public Status getReplies() throws TwitterException;
  public boolean destroyStatus(Integer id) throws TwitterException;

  //
  // user methods
  //

  // TODO: figure out optional parameter for user calls
  // public List<Status> getFriends(Object id) throws TwitterException;
  // public List<Status> getFollowers(Object id) throws TwitterException;

  //
  // direct message methods
  //

  // TODO: figure out optional parameter for message calls
  // public List<DirectMessage> getMessages() throws TwitterException;
  // public List<DirectMessage> getSentMessages() throws TwitterException;

  public DirectMessage sendMessage(Integer id, String text) throws TwitterException;

  public DirectMessage sendMessage(String ScreenName, String text) throws TwitterException;

  public boolean destroyMessage(Integer id) throws TwitterException;

  //
  // friendship methods
  //

  // TODO: figure out optional parameter for message calls
  // public User createFriendship(Integer id) throws TwitterException;
  // public User createFriendship(String screenName) throws TwitterException;

  public User destroyFriendship(Integer id) throws TwitterException;

  public User destroyFriendship(String id) throws TwitterException;

  public boolean friendshipExists(Integer a, Integer b) throws TwitterException;

  public boolean friendshipExists(String a, Integer b) throws TwitterException;

  public boolean friendshipExists(Integer a, String b) throws TwitterException;

  public boolean friendshipExists(String a, String b) throws TwitterException;

  //
  // account methods
  //

  public boolean verifyCredentials() throws TwitterException;

  public boolean endSession() throws TwitterException;

  public boolean updateDeliveryDevice(Device device) throws TwitterException;

  // TODO: figure out configuration for profile updating, and other profile
  // updating issues
  // public boolean updateProfileColors() throws TwitterException;
  // public boolean updateProfileImage() throws TwitterException;
  // public boolean updateProfileBackgroundImage() throws TwitterException;
  // public boolean updateProfile() throws TwitterException;

  public Integer rateLimitStatus() throws TwitterException;

  //
  // favorite methods
  //

  // TODO: figure out configuration for favorite methods
  // public List<Status> getFavorites(Integer id) throws TwitterException;
  // public List<Status> getFavorites(String screenName) throws
  // TwitterException;

  public Status createFavorite(Integer id) throws TwitterException;

  public Status destroyFavorite(Integer id) throws TwitterException;

  //
  // notification methods
  //

  public boolean followUser(Integer id) throws TwitterException;

  public boolean followUser(String screenName) throws TwitterException;

  public boolean leaveUser(Integer id) throws TwitterException;

  public boolean leaveUser(String screenName) throws TwitterException;

  //
  // block methods
  //

  public User blockUser(Integer id) throws TwitterException;

  public User blockUser(String screenName) throws TwitterException;

  public User unblockUser(Integer id) throws TwitterException;

  public User unblockUser(String screenName) throws TwitterException;
}
