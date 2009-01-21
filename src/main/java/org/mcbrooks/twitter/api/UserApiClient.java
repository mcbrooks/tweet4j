package org.mcbrooks.twitter.api;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.Element;
import org.mcbrooks.twitter.DirectMessage;
import org.mcbrooks.twitter.Status;
import org.mcbrooks.twitter.User;
import org.mcbrooks.twitter.User.Device;

public class UserApiClient extends PublicApiClient implements PublicApi, UserApi
{
  // Logger logger = Logger.getLogger(UserApiClient.class);
  /** user name */
  // private String username;
  /** user password */
  // private String password;
  /** make constructor private */
  private UserApiClient()
  {
  }

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

  public User blockUser(Integer id) throws TwitterException
  {
    return blockUser(id.toString());
  }

  public User blockUser(String screenName) throws TwitterException
  {
    // post method
    PostMethod method = new PostMethod(baseUrl + "blocks/create/" + screenName + ".xml");

    // execute method
    Document doc = executeMethod(method);

    // return parsed user
    return parseUser(doc);
  }

  @Override
  public Status createFavorite(Integer id) throws TwitterException
  {
    // post method
    PostMethod method = new PostMethod(baseUrl + "favorites/create/" + id + ".xml");

    // execute method
    Document doc = executeMethod(method);

    // return parsed user
    return parseStatus(doc);
  }

  @Override
  public User createFriendship(Integer id, boolean follow) throws TwitterException
  {
    return createFriendship(id.toString(), follow);
  }

  @Override
  public User createFriendship(String screenName, boolean follow) throws TwitterException
  {
    // post method
    PostMethod method = new PostMethod(baseUrl + "friendships/create/" + screenName + ".xml");
    if (follow)
    {
      method.setQueryString("follow=true");
    }

    // execute method
    Document doc = executeMethod(method);

    // return parsed user
    return parseUser(doc);
  }

  @Override
  public Status destroyFavorite(Integer id) throws TwitterException
  {
    // post method
    PostMethod method = new PostMethod(baseUrl + "favorites/destroy/" + id + ".xml");

    // execute method
    Document doc = executeMethod(method);

    // return parsed user
    return parseStatus(doc);
  }

  @Override
  public User destroyFriendship(Integer id) throws TwitterException
  {
    return destroyFriendship(id.toString());
  }

  @Override
  public User destroyFriendship(String screenName) throws TwitterException
  {
    // post method
    PostMethod method = new PostMethod(baseUrl + "friendships/destroy/" + screenName + ".xml");

    // execute method
    Document doc = executeMethod(method);

    // return parsed user
    return parseUser(doc);
  }

  @Override
  public DirectMessage destroyMessage(Integer id) throws TwitterException
  {
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
    // post method
    PostMethod method = new PostMethod(baseUrl + "statuses/destroy/" + id + ".xml");

    // execute method
    Document doc = executeMethod(method);

    // parse status
    return parseStatus(doc);
  }

  @Override
  public boolean endSession() throws TwitterException
  {
    // post method
    PostMethod method = new PostMethod(baseUrl + "account/end_session.xml");

    // execute method
    Document doc = executeMethod(method);

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
    // post method
    PostMethod method = new PostMethod(baseUrl + "notifications/follow/" + screenName + ".xml");

    // execute method
    Document doc = executeMethod(method);

    // return parsed user
    return parseUser(doc);
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
    GetMethod method = new GetMethod(baseUrl + "friendships/exists.xml?user_a=" + a + "&user_b="
        + b);

    // execute method
    Document doc = executeMethod(method);
    Element friends = (Element) doc.selectSingleNode("friends");

    return XmlParser.parseBooleanElement(friends);
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
    // post method
    PostMethod method = new PostMethod(baseUrl + "notifications/leave/" + screenName + ".xml");

    // execute method
    Document doc = executeMethod(method);

    // return parsed user
    return parseUser(doc);
  }

  @Override
  public Integer rateLimitStatus() throws TwitterException
  {
    // post method
    PostMethod method = new PostMethod(baseUrl + "account/rate_limit_status.xml");

    // execute method
    Document doc = executeMethod(method);
    Element el = (Element) doc.selectSingleNode("//remaining-hits");

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
    NameValuePair[] data = new NameValuePair[] { new NameValuePair("user", screenName),
        new NameValuePair("text", text) };
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
    // post method
    PostMethod method = new PostMethod(baseUrl + "blocks/destroy/" + screenName + ".xml");

    // execute method
    Document doc = executeMethod(method);

    // return parsed user
    return parseUser(doc);
  }

  @Override
  public User updateDeliveryDevice(Device device) throws TwitterException
  {
    // post method
    PostMethod method = new PostMethod(baseUrl + "account/update_delivery_device.xml");
    NameValuePair[] data = { new NameValuePair("device", device.toString().toLowerCase()), };
    method.setRequestBody(data);

    // execute method
    Document doc = executeMethod(method);

    // parse response
    return parseUser(doc);
  }

  @Override
  public Status updateStatus(String status) throws TwitterException
  {
    // post method
    PostMethod method = new PostMethod(baseUrl + "statuses/update.xml");
    NameValuePair[] data = { new NameValuePair("status", status) };
    method.setRequestBody(data);

    // execute method
    Document doc = executeMethod(method);

    // parse response
    return parseStatus(doc);
  }

  @Override
  public Status updateStatus(String status, Integer responseId) throws TwitterException
  {
    // post method
    PostMethod method = new PostMethod(baseUrl + "statuses/update.xml");
    NameValuePair[] data = { new NameValuePair("status", status),
        new NameValuePair("in_reply_to_status_id", responseId.toString()) };
    method.setRequestBody(data);

    // execute method
    Document doc = executeMethod(method);

    // parse response
    return parseStatus(doc);
  }

  @Override
  public User verifyCredentials() throws TwitterException
  {
    // get method
    GetMethod method = new GetMethod(baseUrl + "account/verify_credentials.xml");

    // execute method
    Document doc = executeMethod(method);

    // parse response
    return parseUser(doc);
  }

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
    Element el = (Element) doc.selectSingleNode("//direct_message");
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
    Element elem = (Element) el.selectSingleNode("id");
    message.setId(XmlParser.parseIntElementData(elem));
    // sender id
    elem = (Element) el.selectSingleNode("sender_id");
    message.setSenderId(XmlParser.parseIntElementData(elem));
    // text
    elem = (Element) el.selectSingleNode("text");
    message.setText(XmlParser.parseStringElementData(elem));
    // recipient id
    elem = (Element) el.selectSingleNode("recipient_id");
    message.setRecipientId(XmlParser.parseIntElementData(elem));
    // created at
    elem = (Element) el.selectSingleNode("created_at");
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
    elem = (Element) el.selectSingleNode("sender_screen_name");
    message.setSenderScreenName(XmlParser.parseStringElementData(elem));
    // recipient screen name
    elem = (Element) el.selectSingleNode("recipient_screen_name");
    message.setRecipientScreenName(XmlParser.parseStringElementData(elem));
    // sender
    elem = (Element) el.selectSingleNode("sender");
    message.setSender(parseUser(elem));
    // recipient
    elem = (Element) el.selectSingleNode("recipient");
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
}
