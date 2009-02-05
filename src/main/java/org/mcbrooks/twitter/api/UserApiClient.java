package org.mcbrooks.twitter.api;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.dom4j.Document;
import org.dom4j.Element;
import org.mcbrooks.twitter.DirectMessage;
import org.mcbrooks.twitter.Status;
import org.mcbrooks.twitter.User;
import org.mcbrooks.twitter.User.Device;
import org.mcbrooks.twitter.api.params.ApiParams;
import org.mcbrooks.twitter.api.params.ProfileColorParams;
import org.mcbrooks.twitter.api.params.ProfileParams;

public class UserApiClient extends PublicApiClient implements UserApi
{
  // Logger logger = Logger.getLogger(UserApiClient.class);
  /** user name */
  // private String username;
  /** user password */
  // private String password;
  /**
   * constructor
   */
  public UserApiClient(String username, String password)
  {
    client = HttpClientFactory.getUserClient();
    configureClient(username, password);
  }

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
  // API methods
  //

  public User blockUser(Integer id) throws TwitterException
  {
    return blockUser(id.toString());
  }

  public User blockUser(String screenName) throws TwitterException
  {
    return userPost("blocks/create/" + screenName + ".xml", null);
  }

  @Override
  public Status createFavorite(Integer id) throws TwitterException
  {
    return statusPost("favorites/create/" + id + ".xml", null);
  }

  @Override
  public User createFriendship(Integer id, boolean follow) throws TwitterException
  {
    return createFriendship(id.toString(), follow);
  }

  @Override
  public User createFriendship(String screenName, boolean follow) throws TwitterException
  {
    String url = "friendships/create/" + screenName + ".xml";
    if (follow)
    {
      url += "?follow=true";
    }
    return userPost(url, null);
  }

  @Override
  public Status destroyFavorite(Integer id) throws TwitterException
  {
    return statusPost("favorites/destroy/" + id + ".xml", null);
  }

  @Override
  public User destroyFriendship(Integer id) throws TwitterException
  {
    return destroyFriendship(id.toString());
  }

  @Override
  public User destroyFriendship(String screenName) throws TwitterException
  {
    return userPost("friendships/destroy/" + screenName + ".xml", null);
  }

  @Override
  public DirectMessage destroyMessage(Integer id) throws TwitterException
  {
    // // TODO implement message post
    // / return messagePost("direct_messages/destroy/" + id + ".xml", null);
    // post method
    PostMethod method = new PostMethod(baseUrl + "direct_messages/destroy/" + id + ".xml");

    // execute method
    Document doc = executeMethod(method);

    // parse message
    return parseDirectMessage(doc);
  }

  @Override
  public Status destroyStatus(Integer id) throws TwitterException
  {
    return statusPost("statuses/destroy/" + id + ".xml", null);
  }

  @Override
  public boolean endSession() throws TwitterException
  {
    // post method
    PostMethod method = new PostMethod(baseUrl + "account/end_session.xml");

    // execute method
    executeMethod(method);

    // TODO parse success boolean
    return true;
  }

  @Override
  public User followUser(Integer id) throws TwitterException
  {
    return followUser(id.toString());
  }

  @Override
  public User followUser(String screenName) throws TwitterException
  {
    return userPost("notifications/follow/" + screenName + ".xml", null);
  }

  @Override
  public boolean friendshipExists(Integer a, Integer b) throws TwitterException
  {
    return friendshipExists(a.toString(), b.toString());
  }

  @Override
  public boolean friendshipExists(String a, Integer b) throws TwitterException
  {
    return friendshipExists(a, b.toString());
  }

  @Override
  public boolean friendshipExists(String a, String b) throws TwitterException
  {
    // get method
    GetMethod method =
        new GetMethod(baseUrl + "friendships/exists.xml?user_a=" + a + "&user_b=" + b);

    // execute method
    Document doc = executeMethod(method);
    Element friends = (Element)doc.selectSingleNode("friends");

    return XmlParser.parseBooleanElement(friends);
  }

  @Override
  public List<Integer> getFollowerIds() throws TwitterException
  {
    return idsGet("followers/ids.xml");
  }

  @Override
  public List<Integer> getFollowerIds(Integer id) throws TwitterException
  {
    return getFollowerIds(id.toString());
  }

  @Override
  public List<Integer> getFollowerIds(String id) throws TwitterException
  {
    return idsGet("followers/ids/" + id + ".xml");
  }

  @Override
  public List<User> getFollowers() throws TwitterException
  {
    // get method
    GetMethod method = new GetMethod(baseUrl + "statuses/followers.xml");

    // execute method
    Document doc = executeMethod(method);

    // parse response
    return parseUsers(doc);
  }

  @Override
  public List<Status> getFavorites() throws TwitterException
  {
    // get method
    GetMethod method = new GetMethod(baseUrl + "favorites.xml");

    // execute method
    Document doc = executeMethod(method);

    // parse response
    return parseStatuses(doc);
  }
  

  @Override
  public List<Status> getFavorites(ApiParams params) throws TwitterException
  {
    // determining api url
    String url = "favorites";
    String screenName = params.getScreenName();
    if (screenName != null)
    {
      url += "/" + screenName + ".xml";
    }
    else if (params.getId() != null)
    {
      url += "/" + params.getId() + ".xml";
    }
    else
    {
      url += ".xml";
    }
    // get method
    GetMethod method = new GetMethod(baseUrl + url);

    // adding optional page query
    if (params.getPage() != null)
    {
      method.setQueryString("page=" + params.getPage());
    }

    // execute method
    Document doc = executeMethod(method);

    // parse response
    return parseStatuses(doc);
  }

  @Override
  public List<User> getFollowers(ApiParams params) throws TwitterException
  {
    // determing api url
    String queryString = "statuses/followers";
    String screenName = params.getScreenName();
    if (screenName != null)
    {
      queryString += "/" + screenName + ".xml";
    }
    else if (params.getId() != null)
    {
      queryString += "/" + params.getId() + ".xml";
    }
    else
    {
      queryString += ".xml";
    }
    // get method

    GetMethod method = new GetMethod(baseUrl + queryString);
    // adding optional page query
    Integer page = params.getPage();
    if (params.getPage() != null)
    {
      method.setQueryString("page=" + page);
    }

    // execute method
    Document doc = executeMethod(method);

    // parse response
    return parseUsers(doc);
  }

  @Override
  public List<Integer> getFriendIds() throws TwitterException
  {
    return idsGet("friends/ids.xml");
  }

  @Override
  public List<Integer> getFriendIds(Integer id) throws TwitterException
  {
    return getFriendIds(id.toString());
  }

  @Override
  public List<Integer> getFriendIds(String id) throws TwitterException
  {
    return idsGet("friends/ids/" + id + ".xml");
  }

  @Override
  public List<User> getFriends() throws TwitterException
  {
    // get method
    GetMethod method = new GetMethod(baseUrl + "statuses/friends.xml");

    // execute method
    Document doc = executeMethod(method);

    // parse response
    return parseUsers(doc);
  }

  @Override
  public List<User> getFriends(ApiParams params) throws TwitterException
  {
    // determing api url
    String queryString = "statuses/friends";
    String screenName = params.getScreenName();
    if (screenName != null)
    {
      queryString += "/" + screenName + ".xml";
    }
    else if (params.getId() != null)
    {
      queryString += "/" + params.getId() + ".xml";
    }
    else
    {
      queryString += ".xml";
    }
    // get method

    GetMethod method = new GetMethod(baseUrl + queryString);
    // adding optional page query
    Integer page = params.getPage();
    if (params.getPage() != null)
    {
      method.setQueryString("page=" + page);
    }

    // execute method
    Document doc = executeMethod(method);

    // parse response
    return parseUsers(doc);
  }

  @Override
  public List<Status> getFriendsTimeline() throws TwitterException
  {
    // get method
    GetMethod method = new GetMethod(baseUrl + "statuses/friends_timeline.xml");

    // execute method
    Document doc = executeMethod(method);

    // parse response
    return parseStatuses(doc);
  }

  @Override
  public List<Status> getFriendsTimeline(ApiParams params) throws TwitterException
  {
    // get method
    GetMethod method = new GetMethod(baseUrl + "statuses/friends_timeline.xml");

    // adding optional page query
    String query = "";
    if (params.getSince() != null)
    {
      // TODO handle since parameter
    }
    else if (params.getSinceId() != null)
    {
      query = "since_id=" + params.getSinceId();
    }
    else if (params.getCount() != null)
    {
      query = "count=" + params.getCount();
    }
    else if (params.getPage() != null)
    {
      query = "page=" + params.getPage();
    }
    method.setQueryString(query);

    // execute method
    Document doc = executeMethod(method);

    // parse response
    return parseStatuses(doc);
  }

  @Override
  public List<DirectMessage> getMessages() throws TwitterException
  {
    // get method
    GetMethod method = new GetMethod(baseUrl + "direct_messages.xml");

    // execute method
    Document doc = executeMethod(method);

    // parse response
    return parseDirectMessages(doc);
  }

  @Override
  public List<DirectMessage> getMessages(ApiParams params) throws TwitterException
  {
    // get method
    GetMethod method = new GetMethod(baseUrl + "direct_messages.xml");

    // adding optional page query
    String query = "";
    if (params.getSince() != null)
    {
      // TODO handle since parameter
    }
    else if (params.getSinceId() != null)
    {
      query = "since_id=" + params.getSinceId();
    }
    else if (params.getPage() != null)
    {
      query = "page=" + params.getPage();
    }
    method.setQueryString(query);

    // execute method
    Document doc = executeMethod(method);

    // parse response
    return parseDirectMessages(doc);
  }

  @Override
  public List<Status> getReplies() throws TwitterException
  {
    // get method
    GetMethod method = new GetMethod(baseUrl + "statuses/replies.xml");

    // execute method
    Document doc = executeMethod(method);

    // parse response
    return parseStatuses(doc);
  }

  @Override
  public List<Status> getReplies(ApiParams params) throws TwitterException
  {
    // get method
    GetMethod method = new GetMethod(baseUrl + "statuses/replies.xml");

    // adding optional page query
    String query = "";
    if (params.getPage() != null)
    {
      query = "page=" + params.getPage();
    }
    else if (params.getSince() != null)
    {
      // TODO handle since parameter
    }
    else if (params.getSinceId() != null)
    {
      query = "since_id=" + params.getSinceId();
    }
    method.setQueryString(query);

    // execute method
    Document doc = executeMethod(method);

    // parse response
    return parseStatuses(doc);
  }

  @Override
  public List<DirectMessage> getSentMessages() throws TwitterException
  {
    // get method
    GetMethod method = new GetMethod(baseUrl + "direct_messages/sent.xml");

    // execute method
    Document doc = executeMethod(method);

    // parse response
    return parseDirectMessages(doc);
  }

  @Override
  public List<DirectMessage> getSentMessages(ApiParams params) throws TwitterException
  {
    // get method
    GetMethod method = new GetMethod(baseUrl + "direct_messages/sent.xml");

    // adding optional page query
    String query = "";
    if (params.getSince() != null)
    {
      // TODO handle since parameter
    }
    else if (params.getSinceId() != null)
    {
      query = "since_id=" + params.getSinceId();
    }
    else if (params.getPage() != null)
    {
      query = "page=" + params.getPage();
    }
    method.setQueryString(query);

    // execute method
    Document doc = executeMethod(method);

    // parse response
    return parseDirectMessages(doc);
  }

  @Override
  public List<Status> getUserTimeline() throws TwitterException
  {
    // get method
    GetMethod method = new GetMethod(baseUrl + "statuses/user_timeline.xml");

    // execute method
    Document doc = executeMethod(method);

    // parse response
    return parseStatuses(doc);
  }

  @Override
  public List<Status> getUserTimeline(ApiParams params) throws TwitterException
  {
    // determing api url
    String url = "statuses/user_timeline";
    String screenName = params.getScreenName();
    if (screenName != null)
    {
      url += "/" + screenName + ".xml";
    }
    else if (params.getId() != null)
    {
      url += "/" + params.getId() + ".xml";
    }
    else
    {
      url += ".xml";
    }
    // get method
    GetMethod method = new GetMethod(baseUrl + url);

    // adding optional page query
    String query = "";
    if (params.getCount() != null)
    {
      query = "count=" + params.getCount();
    }
    else if (params.getSince() != null)
    {
      // TODO handle since parameter
    }
    else if (params.getSinceId() != null)
    {
      query = "since_id=" + params.getSinceId();
    }
    else if (params.getPage() != null)
    {
      query = "page=" + params.getPage();
    }
    method.setQueryString(query);

    // execute method
    Document doc = executeMethod(method);

    // parse response
    return parseStatuses(doc);
  }

  @Override
  public User leaveUser(Integer id) throws TwitterException
  {
    return leaveUser(id.toString());
  }

  @Override
  public User leaveUser(String screenName) throws TwitterException
  {
    return userPost("notifications/leave/" + screenName + ".xml", null);
  }

  @Override
  public Integer rateLimitStatus() throws TwitterException
  {
    // post method
    PostMethod method = new PostMethod(baseUrl + "account/rate_limit_status.xml");

    // execute method
    Document doc = executeMethod(method);
    Element el = (Element)doc.selectSingleNode("//remaining-hits");

    // parse rate number
    return XmlParser.parseIntElementData(el);
  }

  @Override
  public DirectMessage sendMessage(Integer id, String text) throws TwitterException
  {
    return sendMessage(id.toString(), text);
  }

  @Override
  public DirectMessage sendMessage(String screenName, String text) throws TwitterException
  {
    // post method
    PostMethod method = new PostMethod(baseUrl + "direct_messages/new.xml");
    NameValuePair[] data = new NameValuePair[] {
        new NameValuePair("user", screenName), new NameValuePair("text", text)
    };
    method.setRequestBody(data);

    // execute method
    Document doc = executeMethod(method);

    // return parsed user
    return parseDirectMessage(doc);
  }

  @Override
  public User unblockUser(Integer id) throws TwitterException
  {
    return unblockUser(id.toString());
  }

  @Override
  public User unblockUser(String screenName) throws TwitterException
  {
    return userPost("blocks/destroy/" + screenName + ".xml", null);
  }

  @Override
  public User updateDeliveryDevice(Device device) throws TwitterException
  {
    NameValuePair[] data = {
      new NameValuePair("device", device.toString().toLowerCase()),
    };
    return userPost("account/update_delivery_device.xml", data);
  }

  @Override
  public User updateProfile(ProfileParams params) throws TwitterException
  {
    return userPost("account/update_profile.xml", params.toData());
  }

  @Override
  public User updateProfileColors(ProfileColorParams params) throws TwitterException
  {
    String url = baseUrl + "";
    NameValuePair[] data = params.toData();
    return userPost(url, data);
  }

  // @Override
  // public User updateProfileImage(File file) throws TwitterException
  // {
  // return userPostImage(file, "account/update_profile_image.xml");
  // }
  //
  // @Override
  // public User updateProfileBackgroundImage(File file) throws TwitterException
  // {
  // return userPostImage(file, "account/update_profile_background_image.xml");
  // }

  @Override
  public Status updateStatus(String status) throws TwitterException
  {
    NameValuePair[] data = {
      new NameValuePair("status", status)
    };
    return statusPost("statuses/update.xml", data);
  }

  @Override
  public Status updateStatus(String status, Integer responseId) throws TwitterException
  {
    NameValuePair[] data =
        {
            new NameValuePair("status", status),
            new NameValuePair("in_reply_to_status_id", responseId.toString())
        };
    return statusPost("statuses/update.xml", data);
  }

  @Override
  public User verifyCredentials() throws TwitterException
  {
    return userGet("account/verify_credentials.xml");
  }

  //

  //
  // private implementations
  //

  @SuppressWarnings("unchecked")
  private List<DirectMessage> parseDirectMessages(Document doc)
  {
    List<DirectMessage> messages = new ArrayList<DirectMessage>();
    List<Element> els = doc.selectNodes("//direct_message");
    for (Element el : els)
    {
      DirectMessage message = parseDirectMessage(el);
      if (message != null)
      {
        messages.add(message);
      }
    }
    return messages;
  }

  private DirectMessage parseDirectMessage(Document doc)
  {
    DirectMessage message = null;
    Element el = (Element)doc.selectSingleNode("//direct_message");
    if (el != null)
    {
      message = parseDirectMessage(el);
    }
    return message;
  }

  private DirectMessage parseDirectMessage(Element el)
  {
    DirectMessage message = new DirectMessage();
    // id
    Element elem = (Element)el.selectSingleNode("id");
    message.setId(XmlParser.parseIntElementData(elem));
    // sender id
    elem = (Element)el.selectSingleNode("sender_id");
    message.setSenderId(XmlParser.parseIntElementData(elem));
    // text
    elem = (Element)el.selectSingleNode("text");
    message.setText(XmlParser.parseStringElementData(elem));
    // recipient id
    elem = (Element)el.selectSingleNode("recipient_id");
    message.setRecipientId(XmlParser.parseIntElementData(elem));
    // created at
    elem = (Element)el.selectSingleNode("created_at");
    SimpleDateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
    try
    {
      message.setCreatedAt(df.parse(XmlParser.parseStringElementData(elem)));
    }
    catch (Exception e)
    {
      logger.warn("Message creation date parsing error: " + e);
    }
    // sender screen name
    elem = (Element)el.selectSingleNode("sender_screen_name");
    message.setSenderScreenName(XmlParser.parseStringElementData(elem));
    // recipient screen name
    elem = (Element)el.selectSingleNode("recipient_screen_name");
    message.setRecipientScreenName(XmlParser.parseStringElementData(elem));
    // sender
    elem = (Element)el.selectSingleNode("sender");
    message.setSender(parseUser(elem));
    // recipient
    elem = (Element)el.selectSingleNode("recipient");
    message.setRecipient(parseUser(elem));
    if (logger.isDebugEnabled())
    {
      logger.debug("Parsed direct message: " + message);
    }
    // return the message
    return message;
  }

  private void configureClient(String username, String password)
  {
    // this.username = username;
    // this.password = password;
    // set auth credentials
    client.getState().setCredentials(new AuthScope("twitter.com", 80),
        new UsernamePasswordCredentials(username, password));
  }

  private List<Integer> idsGet(String url) throws TwitterException
  {
    // get method
    GetMethod method = new GetMethod(baseUrl + url);

    // execute method
    Document doc = executeMethod(method);

    // return list of ids
    return parseIds(doc);
  }

  private List<Integer> parseIds(Document doc)
  {
    // get list of ids
    List<Element> els = doc.selectNodes("ids/id");
    logger.debug("els: " + els);
    
    if (els == null) {
      return null;
    }
    
    List<Integer> ids = new ArrayList<Integer>();
    for (Element el : els) {
      Integer id = XmlParser.parseIntElementData(el);
      if (id != null) {
        ids.add(id);
      }
    }
    logger.debug("ids: " + ids);
    return ids;
  }

  private Status statusGet(String url) throws TwitterException
  {
    // get method
    GetMethod method = new GetMethod(baseUrl + url);

    // execute method
    Document doc = executeMethod(method);

    // return user
    return parseStatus(doc);
  }

  private Status statusPost(String url, NameValuePair[] data) throws TwitterException
  {
    // post method
    PostMethod method = new PostMethod(baseUrl + url);
    if (data != null)
    {
      method.setRequestBody(data);
    }

    // execute method
    Document doc = executeMethod(method);

    // return status
    return parseStatus(doc);
  }

  private User userGet(String url) throws TwitterException
  {
    // get method
    GetMethod method = new GetMethod(baseUrl + url);

    // execute method
    Document doc = executeMethod(method);

    // return user
    return parseUser(doc);
  }

  private User userPost(String url, NameValuePair[] data) throws TwitterException
  {
    // post method
    PostMethod method = new PostMethod(baseUrl + url);
    if (data != null)
    {
      method.setRequestBody(data);
    }

    // execute method
    Document doc = executeMethod(method);

    // return user
    return parseUser(doc);
  }

  /**
   * Posts an image file, validating that the file is a file, correct filename,
   * and is less than 700K
   * 
   * @param file
   * @return User - an extend information user
   * @throws TwitterException
   */
  private User userPostImage(File file, String url) throws TwitterException
  {
    // validate image file
    String path = file.getPath();
    boolean isValid =
        file.isFile() && (path.contains(".gif") || path.contains(".png") || path.contains(".jpg"))
            && file.length() < 700000l;
    if (!isValid)
    {
      throw new TwitterException("Profile image is not valid.");
    }

    // create post method and request entity
    PostMethod post = new PostMethod(baseUrl + url);
    try
    {
      Part part = new FilePart(file.getName(), file);
      Part[] parts = {
        part
      };
      MultipartRequestEntity entity = new MultipartRequestEntity(parts, post.getParams());
      post.setRequestEntity(entity);
    }
    catch (Exception e)
    {
      logger.error("error uploading file: " + e);
      throw new TwitterException("Error uploading profile image.");
    }
    // execute method
    Document doc = executeMethod(post);

    // return parsed user
    return parseUser(doc);
  }
}
