package org.mcbrooks.twitter.api;

import org.apache.log4j.Appender;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.mcbrooks.twitter.Status;
import org.mcbrooks.twitter.User;

public class Twitter 
{
  /** log4j */
  private static final Logger logger = Logger.getLogger(Twitter.class);
  
  public static void main(String[] args)
  {
    System.out.println("mcb twitter jar");
    BasicConfigurator.configure();
    logger.debug("testing");
    PublicApi api = new PublicApiClient();
    try {
//      logger.debug(api.publicTimeline());
//      logger.debug(api.showUserById("mcb"));
//      logger.debug(api.getStatus(1115143488));
//      logger.debug(api.showUserByEmail("m_c_brooks@hotmail.com"));
//      new SearchApiClient().search("");
      /*
      Status status = new Status();
      logger.debug(status.getText());
      status.setText("testing, testing");
      logger.debug(status.getText());
      status.setText(null);
      logger.debug(status.getText());
      */
      UserApiClient uApi = new UserApiClient("mcb", "micheal");
      logger.debug(uApi.getPublicTimeline());
//      uApi.getFollowers((Object)"mcb");
    }
    catch (Exception e) {
      e.printStackTrace();
      logger.error("api error: " + e.getLocalizedMessage());
    }
  }
}
