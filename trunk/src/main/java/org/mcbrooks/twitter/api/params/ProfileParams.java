/**
 * 
 */
package org.mcbrooks.twitter.api.params;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.NameValuePair;

/**
 * 
 * @author mcb
 */
public class ProfileParams
{
  /** profile name - maximum of 40 characters */
  private String name;
  
  /** email - maximum of 40 characters, must be a valid email address */
  private String email;

  /** profile url - maximum of 100 characters */
  private String url;
  
  /** 
   * profile location - Maximum of 30 characters. 
   * The contents are not normalized or geocoded in any way.
   */
  private String location;
  
  /** Maximum of 160 characters */
  private String description;

  /**
   * @return the name
   */
  public String getName()
  {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name)
  {
    this.name = name;
  }

  /**
   * @return the email
   */
  public String getEmail()
  {
    return email;
  }

  /**
   * @param email the email to set
   */
  public void setEmail(String email)
  {
    this.email = email;
  }

  /**
   * @return the url
   */
  public String getUrl()
  {
    return url;
  }

  /**
   * @param url the url to set
   */
  public void setUrl(String url)
  {
    this.url = url;
  }

  /**
   * @return the location
   */
  public String getLocation()
  {
    return location;
  }

  /**
   * @param location the location to set
   */
  public void setLocation(String location)
  {
    this.location = location;
  }

  /**
   * @return the description
   */
  public String getDescription()
  {
    return description;
  }

  /**
   * @param description the description to set
   */
  public void setDescription(String description)
  {
    this.description = description;
  }

  /**
   * Converts the configuration bean into a NameValuePair array to be posted
   * 
   * @return NameValuePair[]
   */
  public NameValuePair[] toData()
  {
    List<NameValuePair> list = new ArrayList<NameValuePair>();
    if (name != null && name.length() > 0) 
    {
      list.add(new NameValuePair("name", name));
    }
    if (email != null && email.length() > 0)
    {
      list.add(new NameValuePair("email", email));
    }
    if (url != null && url.length() > 0)
    {
      list.add(new NameValuePair("url", url));
    }
    if (location != null && location.length() > 0)
    {
      list.add(new NameValuePair("location", location));
    }
    if (description != null && description.length() > 0)
    {
      list.add(new NameValuePair("description", description));
    }
    NameValuePair[] data = list.toArray(new NameValuePair[0]);
    return data;
  }
}
