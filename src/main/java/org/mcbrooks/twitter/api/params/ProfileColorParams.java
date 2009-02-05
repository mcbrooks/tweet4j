/**
 * 
 */
package org.mcbrooks.twitter.api.params;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.NameValuePair;

/**
 * @author mcb
 */
public class ProfileColorParams
{
  private String backgroundColor;
  
  private String textColor;
  
  private String linkColor;
  
  private String sidebarFillColor;
  
  private String sidebarBorderColor;

  /**
   * @return the backgroundColor
   */
  public String getBackgroundColor()
  {
    return backgroundColor;
  }

  /**
   * @param backgroundColor the backgroundColor to set
   */
  public void setBackgroundColor(String backgroundColor)
  {
    this.backgroundColor = backgroundColor;
  }

  /**
   * @return the textColor
   */
  public String getTextColor()
  {
    return textColor;
  }

  /**
   * @param textColor the textColor to set
   */
  public void setTextColor(String textColor)
  {
    this.textColor = textColor;
  }

  /**
   * @return the linkColor
   */
  public String getLinkColor()
  {
    return linkColor;
  }

  /**
   * @param linkColor the linkColor to set
   */
  public void setLinkColor(String linkColor)
  {
    this.linkColor = linkColor;
  }

  /**
   * @return the sidebarFillColor
   */
  public String getSidebarFillColor()
  {
    return sidebarFillColor;
  }

  /**
   * @param sidebarFillColor the sidebarFillColor to set
   */
  public void setSidebarFillColor(String sidebarFillColor)
  {
    this.sidebarFillColor = sidebarFillColor;
  }

  /**
   * @return the sidebarBorderColor
   */
  public String getSidebarBorderColor()
  {
    return sidebarBorderColor;
  }

  /**
   * @param sidebarBorderColor the sidebarBorderColor to set
   */
  public void setSidebarBorderColor(String sidebarBorderColor)
  {
    this.sidebarBorderColor = sidebarBorderColor;
  }
  
  public NameValuePair[] toData()
  {
    List<NameValuePair> list = new ArrayList<NameValuePair>();
    if (backgroundColor != null && backgroundColor.length() > 0) 
    {
      list.add(new NameValuePair("profile_background_color", backgroundColor));
    }
    if (textColor != null && textColor.length() > 0) 
    {
      list.add(new NameValuePair("profile_text_color", textColor));
    }
    if (linkColor != null && linkColor.length() > 0) 
    {
      list.add(new NameValuePair("profile_link_color", linkColor));
    }
    if (sidebarFillColor != null && sidebarFillColor.length() > 0) 
    {
      list.add(new NameValuePair("profile_sidebar_fill_color", sidebarFillColor));
    }
    if (sidebarBorderColor != null && sidebarBorderColor.length() > 0) 
    {
      list.add(new NameValuePair("profile_sidebar_border_color", sidebarBorderColor));
    }
    NameValuePair[] data = list.toArray(new NameValuePair[0]);
    return data;
  }
}
