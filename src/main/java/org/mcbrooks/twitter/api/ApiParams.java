/**
 * 
 */
package org.mcbrooks.twitter.api;

/**
 * @author mcb
 *
 */
public class ApiParams
{
  private Integer id;
  
  private String screenName;
  
  private Integer page;
  
  private Integer sinceId;
  
  private String since;

  private Integer count;

  /**
   * @param screenName
   */
  public ApiParams(String screenName)
  {
    this.screenName = screenName;
  }

  /**
   * @param id
   */
  public ApiParams(Integer id)
  {
    this.id = id;
  }

  /**
   * @param screenName
   * @param page
   */
  public ApiParams(String screenName, Integer page)
  {
    this.screenName = screenName;
    this.page = page;
  }

  /**
   * @param id
   * @param page
   */
  public ApiParams(Integer id, Integer page)
  {
    this.id = id;
    this.page = page;
  }

  /**
   * @return the id
   */
  public Integer getId()
  {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(Integer id)
  {
    this.id = id;
  }

  /**
   * @return the screenName
   */
  public String getScreenName()
  {
    return screenName;
  }

  /**
   * @param screenName the screenName to set
   */
  public void setScreenName(String screenName)
  {
    this.screenName = screenName;
  }

  /**
   * @return the page
   */
  public Integer getPage()
  {
    return page;
  }

  /**
   * @param page the page to set
   */
  public void setPage(Integer page)
  {
    this.page = page;
  }

  /**
   * @return the sinceId
   */
  public Integer getSinceId()
  {
    return sinceId;
  }

  /**
   * @param sinceId the sinceId to set
   */
  public void setSinceId(Integer sinceId)
  {
    this.sinceId = sinceId;
  }

  /**
   * @return the since
   */
  public String getSince()
  {
    return since;
  }

  /**
   * @param since the since to set
   */
  public void setSince(String since)
  {
    this.since = since;
  }

  /**
   * @return the count
   */
  public Integer getCount()
  {
    return count;
  }

  /**
   * @param count the count to set
   */
  public void setCount(Integer count)
  {
    this.count = count;
  }
}
