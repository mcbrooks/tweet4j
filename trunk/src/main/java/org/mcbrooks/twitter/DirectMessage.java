/**
 * 
 */
package org.mcbrooks.twitter;

import java.util.Date;

/**
 * @author mcb
 *
 */
public class DirectMessage 
{
  private int id;
  
  private String text;
  
  private int senderId;
  
  private String senderScreenName;
  
  private User sender;
  
  private int recipientId;
  
  private String recipientScreenName;
  
  private User recipient;
  
  private Date createdAt;

  /**
   * @return the id
   */
  public int getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * @return the text
   */
  public String getText() {
    return text;
  }

  /**
   * @param text the text to set
   */
  public void setText(String text) {
    this.text = text;
  }

  /**
   * @return the senderId
   */
  public int getSenderId() {
    return senderId;
  }

  /**
   * @param senderId the senderId to set
   */
  public void setSenderId(int senderId) {
    this.senderId = senderId;
  }

  /**
   * @return the senderScreenName
   */
  public String getSenderScreenName() {
    return senderScreenName;
  }

  /**
   * @param senderScreenName the senderScreenName to set
   */
  public void setSenderScreenName(String senderScreenName) {
    this.senderScreenName = senderScreenName;
  }

  /**
   * @return the sender
   */
  public User getSender() {
    return sender;
  }

  /**
   * @param sender the sender to set
   */
  public void setSender(User sender) {
    this.sender = sender;
  }

  /**
   * @return the recipientId
   */
  public int getRecipientId() {
    return recipientId;
  }

  /**
   * @param recipientId the recipientId to set
   */
  public void setRecipientId(int recipientId) {
    this.recipientId = recipientId;
  }

  /**
   * @return the recipientScreenName
   */
  public String getRecipientScreenName() {
    return recipientScreenName;
  }

  /**
   * @param recipientScreenName the recipientScreenName to set
   */
  public void setRecipientScreenName(String recipientScreenName) {
    this.recipientScreenName = recipientScreenName;
  }

  /**
   * @return the recipient
   */
  public User getRecipient() {
    return recipient;
  }

  /**
   * @param recipient the recipient to set
   */
  public void setRecipient(User recipient) {
    this.recipient = recipient;
  }

  /**
   * @return the createdAt
   */
  public Date getCreatedAt() {
    return createdAt;
  }

  /**
   * @param createdAt the createdAt to set
   */
  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }
}
