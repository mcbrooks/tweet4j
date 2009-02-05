/**
 * 
 */
package org.mcbrooks.twitter.api;

import java.util.List;

import org.mcbrooks.twitter.DirectMessage;
import org.mcbrooks.twitter.Status;
import org.mcbrooks.twitter.User;
import org.mcbrooks.twitter.User.Device;
import org.mcbrooks.twitter.api.params.ApiParams;
import org.mcbrooks.twitter.api.params.ProfileColorParams;
import org.mcbrooks.twitter.api.params.ProfileParams;

/**
 * @author mcb
 */
public interface UserApi extends PublicApi
{
  //
  // status methods
  //

  public List<Status> getFriendsTimeline() throws TwitterException;

  public List<Status> getFriendsTimeline(ApiParams params) throws TwitterException;

  public List<Status> getUserTimeline() throws TwitterException;

  public List<Status> getUserTimeline(ApiParams params) throws TwitterException;

  public Status updateStatus(String status) throws TwitterException;

  public Status updateStatus(String status, Integer responseId) throws TwitterException;

  public List<Status> getReplies() throws TwitterException;

  public List<Status> getReplies(ApiParams params) throws TwitterException;

  public Status destroyStatus(Integer id) throws TwitterException;

  //
  // user methods
  //

  public List<User> getFriends() throws TwitterException;

  public List<User> getFriends(ApiParams params) throws TwitterException;

  public List<User> getFollowers() throws TwitterException;

  public List<User> getFollowers(ApiParams params) throws TwitterException;

  //
  // direct message methods
  //

  public List<DirectMessage> getMessages() throws TwitterException;

  public List<DirectMessage> getMessages(ApiParams params) throws TwitterException;

  public List<DirectMessage> getSentMessages() throws TwitterException;

  public List<DirectMessage> getSentMessages(ApiParams params) throws TwitterException;

  public DirectMessage sendMessage(Integer id, String text) throws TwitterException;

  public DirectMessage sendMessage(String ScreenName, String text) throws TwitterException;

  public DirectMessage destroyMessage(Integer id) throws TwitterException;

  //
  // friendship methods
  //

  public User createFriendship(Integer id, boolean follow) throws TwitterException;

  public User createFriendship(String screenName, boolean follow) throws TwitterException;

  public User destroyFriendship(Integer id) throws TwitterException;

  public User destroyFriendship(String id) throws TwitterException;

  public boolean friendshipExists(Integer a, Integer b) throws TwitterException;

  public boolean friendshipExists(String a, Integer b) throws TwitterException;

  public boolean friendshipExists(String a, String b) throws TwitterException;

  //
  // social graph methods
  //
  
  public List<Integer> getFollowerIds() throws TwitterException;
  
  public List<Integer> getFollowerIds(Integer id) throws TwitterException;
  
  public List<Integer> getFollowerIds(String id) throws TwitterException;
  
  public List<Integer> getFriendIds() throws TwitterException;

  public List<Integer> getFriendIds(Integer id) throws TwitterException;

  public List<Integer> getFriendIds(String id) throws TwitterException;

  //
  // account methods
  //

  public User verifyCredentials() throws TwitterException;

  public boolean endSession() throws TwitterException;

  public User updateDeliveryDevice(Device device) throws TwitterException;

  // TODO: figure out configuration for profile updating, and other profile
  // updating issues
  public User updateProfileColors(ProfileColorParams params) throws TwitterException;
  
//  public User updateProfileImage(File image) throws TwitterException;
  
//  public User updateProfileBackgroundImage(File image) throws TwitterException;
  
  public User updateProfile(ProfileParams params) throws TwitterException;

  public Integer rateLimitStatus() throws TwitterException;

  //
  // favorite methods
  //

  public List<Status> getFavorites() throws TwitterException;

  public List<Status> getFavorites(ApiParams params) throws TwitterException;

  public Status createFavorite(Integer id) throws TwitterException;

  public Status destroyFavorite(Integer id) throws TwitterException;

  //
  // notification methods
  //

  public User followUser(Integer id) throws TwitterException;

  public User followUser(String screenName) throws TwitterException;

  public User leaveUser(Integer id) throws TwitterException;

  public User leaveUser(String screenName) throws TwitterException;

  //
  // block methods
  //

  public User blockUser(Integer id) throws TwitterException;

  public User blockUser(String screenName) throws TwitterException;

  public User unblockUser(Integer id) throws TwitterException;

  public User unblockUser(String screenName) throws TwitterException;
}
