/**
 * 
 */
package org.mcbrooks.twitter.api;

/**
 * @author mcb
 *
 */
public class TwitterException extends Exception 
{
  /** serial id */
  private static final long serialVersionUID = -276006174156931100L;

  /**
   * 
   */
  public TwitterException() {
    super();
  }

  /**
   * @param message
   * @param cause
   */
  public TwitterException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * @param message
   */
  public TwitterException(String message) {
    super(message);
  }

  /**
   * @param cause
   */
  public TwitterException(Throwable cause) {
    super(cause);
  }

}
