/**
 * 
 */
package org.mcbrooks.twitter.api;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.mcbrooks.twitter.Status;
import org.mcbrooks.twitter.User;

/**
 * @author mcb
 */
public abstract class ApiClient
{
  /** log4j logger */
  protected static final Logger logger = Logger.getLogger(ApiClient.class);

  /** http client instance */
  protected HttpClient client = HttpClientFactory.getClient();

  /** base url - defaults to "http://twitter.com/" */
  protected String baseUrl = "http://twitter.com/";

  public ApiClient()
  {
    logger.info("Instantiating ApiClient with baseUrl: " + baseUrl);
  }

  protected Document executeMethod(HttpMethod method) throws TwitterException
  {
    // execute method
    Document doc = null;
    try
    {
      // log request address
      logger.info("ApiClient making Http request to: " + method.getURI());

      int statusCode = client.executeMethod(method);
      if (statusCode == HttpStatus.SC_OK)
      {
        // parse OK response
        doc = HttpUtility.parseMethod(method);
      }
      else
      {
        throw new TwitterException("Twitter HTTP request not OK: " + statusCode);
      }
    }
    catch (Exception e)
    {
      throw new TwitterException("Error contacting Twitter search API: " + e, e.getCause());
    }
    finally
    {
      method.releaseConnection();
    }

    // log xml document
    HttpUtility.dumpXML(logger, "twitter call", doc);

    // return dom4j document
    return doc;
  }

  @SuppressWarnings("unchecked")
  protected List<Status> parseStatuses(Document doc)
  {
    List<Status> statuses = new ArrayList<Status>();
    // select status elements from document
    List<Element> els = doc.selectNodes("//statuses/status");
    // iterate through status elements
    for (Element el : els)
    {
      // parse status from element
      Status status = parseStatus(el);
      if (status != null)
      {
        // add status to list
        statuses.add(status);
      }
    }
    return statuses;
  }

  protected Status parseStatus(Document doc)
  {
    Status status = null;
    Element el = (Element) doc.selectSingleNode("//status");
    if (el != null)
    {
      status = parseStatus(el);
    }
    return status;
  }

  protected Status parseStatus(Element el)
  {
    Status status = new Status();
    // id
    Element elem = (Element) el.selectSingleNode("id");
    status.setId(XmlParser.parseIntElementData(elem));
    // text
    elem = (Element) el.selectSingleNode("text");
    status.setText(XmlParser.parseStringElementData(elem));
    // source
    elem = (Element) el.selectSingleNode("source");
    status.setSource(XmlParser.parseStringElementData(elem));
    // user
    elem = (Element) el.selectSingleNode("user");
    if (elem != null)
    {
      status.setUser(parseUser(elem));
    }
    // reply to screen name
    elem = (Element) el.selectSingleNode("in_reply_to_screen_name");
    status.setInReplyToScreenName(XmlParser.parseStringElementData(elem));
    // reply to status id
    elem = (Element) el.selectSingleNode("in_reply_to_status_id");
    status.setInReplyToStatusId(XmlParser.parseIntElementData(elem));
    // reply to user id
    elem = (Element) el.selectSingleNode("in_reply_to_user_id");
    status.setInReplyToUserId(XmlParser.parseIntElementData(elem));
    // is favorited
    elem = (Element) el.selectSingleNode("favorited");
    status.setFavorited(XmlParser.parseBooleanElement(elem));
    // is truncated
    elem = (Element) el.selectSingleNode("truncated");
    status.setTruncated(XmlParser.parseBooleanElement(elem));
    // creation date
    elem = (Element) el.selectSingleNode("created_at");
    SimpleDateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
    try
    {
      status.setCreatedAt(df.parse(XmlParser.parseStringElementData(elem)));
    }
    catch (Exception e)
    {
      logger.warn("Status date parsing error: " + e);
    }
    if (logger.isDebugEnabled())
    {
      logger.debug("Parsed status: " + status);
    }
    // return status
    return status;
  }

  @SuppressWarnings("unchecked")
  protected List<User> parseUsers(Document doc)
  {
    List<User> users = new ArrayList<User>();
    List<Element> els = doc.selectNodes("//user");
    for (Element el : els)
    {
      User user = parseUser(el);
      if (user != null)
      {
        users.add(user);
      }
    }
    return users;
  }

  protected User parseUser(Document doc)
  {
    User user = null;
    // get user element
    Element el = (Element) doc.selectSingleNode("//user");
    if (el != null)
    {
      user = parseUser(el);
    }
    // return user
    return user;
  }

  protected User parseUser(Element el)
  {
    User user = new User();
    // id
    Element elem = (Element) el.selectSingleNode("id");
    user.setId(XmlParser.parseIntElementData(elem));
    // name
    elem = (Element) el.selectSingleNode("name");
    user.setName(XmlParser.parseStringElementData(elem));
    // screen name
    elem = (Element) el.selectSingleNode("screen_name");
    user.setScreenName(XmlParser.parseStringElementData(elem));
    // description
    elem = (Element) el.selectSingleNode("description");
    user.setDescription(XmlParser.parseStringElementData(elem));
    // status
    elem = (Element) el.selectSingleNode("status");
    if (elem != null)
    {
      user.setStatus(parseStatus(elem));
    }
    // user url
    elem = (Element) el.selectSingleNode("url");
    user.setUrl(XmlParser.parseStringElementData(elem));
    // friendsCount
    elem = (Element) el.selectSingleNode("friends_count");
    user.setFriendsCount(XmlParser.parseIntElementData(elem));
    // followersCount
    elem = (Element) el.selectSingleNode("followers_count");
    user.setFollowersCount(XmlParser.parseIntElementData(elem));
    // favouritesCount
    elem = (Element) el.selectSingleNode("favourites_count");
    user.setFavouritesCount(XmlParser.parseIntElementData(elem));
    // url
    // protected
    elem = (Element) el.selectSingleNode("protected");
    user.setProtected(XmlParser.parseBooleanElement(elem));
    // profile image url
    elem = (Element) el.selectSingleNode("profile_image_url");
    user.setProfileImageUrl(XmlParser.parseStringElementData(elem));
    // TODO: profile information
    // created at
    elem = (Element) el.selectSingleNode("created_at");
    SimpleDateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
    try
    {
      user.setCreatedAt(df.parse(XmlParser.parseStringElementData(elem)));
    }
    catch (Exception e)
    {
      logger.warn("User date parsing error: " + e);
    }
    if (logger.isDebugEnabled()) 
    {
      logger.debug("Parsed user: " + user);
    }
    return user;
  }

}
