/**
 * 
 */
package org.mcbrooks.twitter.api;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * @author mcb
 *
 */
public abstract class HttpUtility 
{
  /**
   * Reads an HttpMethod response into a string, throwing an exception if the method is null or
   * hasn't been executed yet.
   * 
   * @param method
   * @return
   * @throws Exception
   */
  protected static String readMethod(HttpMethod method) throws Exception
  {
    validateMethod(method);
    // parse method response into a string
    String line;
    StringBuffer response = new StringBuffer();
    InputStream in = method.getResponseBodyAsStream();
    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    while ((line = reader.readLine()) != null)
    {
      response.append(line);
    }
    // close input stream
    in.close();
    return response.toString();
  }
  
  /**
   * Parses an HttpMethod response's cookie header into a string that can be 
   * set on returned strings, will throw an exception if the method is null
   * or has not been executed yet.
   * 
   * @param method
   * @return
   * @throws Exception
   */
  protected static String parseResponseCookies(HttpMethod method) throws Exception
  {
    validateMethod(method);
    // method variables
    String cookies = "";
    Header cookieHeader = method.getResponseHeader("Set-Cookie");
    String cookieStr = cookieHeader.getValue();
    if (cookieHeader != null) {
      // pull the cookies out of the header, match against a /{prop=value}/ 
      // pattern, and filter out expires, path and domain props
      String regex = "(\\S+)=(\\S+)";
      String exclude = "expires|path|domain";
      Pattern pattern = Pattern.compile(regex);
      Pattern excludePattern = Pattern.compile(exclude);
      Matcher matcher = pattern.matcher(cookieStr);
      Matcher excludeMatcher;
      while (matcher.find()) 
      {
        String match = matcher.group();
        excludeMatcher = excludePattern.matcher(match);
        if (!excludeMatcher.find()) 
        {
          cookies += match + " ";
        }
      }
    }
    return cookies;
  }

  /**
   * Parses an HttpClient HttpMethod into a log4j Document.
   * 
   * @param method
   * @return
   * @throws Exception 
   */
  protected static Document parseMethod(HttpMethod method) throws Exception
  {
    validateMethod(method);
    SAXReader xmlReader = new SAXReader();
    xmlReader.setValidation(false);
    InputStream in = null;
    Document doc = null;
    try
    {
      in = method.getResponseBodyAsStream();
      doc = xmlReader.read(in);
    }
    finally
    {
      if (in != null)
      {
        in.close();
      }
    }
    return doc;
  }

  /**
   * Parses a relocation string from a method, returning an empty string if not 
   * present, and throwing an exception for a null method or if the method hasn't
   * been executed yet.
   * 
   * @param method
   * @return
   * @throws Exception 
   */
  protected static String parseRelocationLocation(HttpMethod method) throws Exception
  {
    validateMethod(method);
    String relocation = "";
    Header header = method.getResponseHeader("Location");
    if (header != null) 
    {
      relocation = header.getValue();
    }
    return relocation;
  }
  
  /**
   * Dump the xml response to log4j
   * 
   * @param log
   * @param tag
   * @param xml
   */
  protected static void dumpXML(Logger log, String tag, Document xml)
  {
    // Optimization, only dump if debug is enabled
    if (log.isDebugEnabled()) 
    {
      // null check
      if (xml == null)
      {
        log.debug("[" + tag + "]\nEMPTY OR NULL DOCUMENT\n[XML_END]");
      }
      else {
        try
        {
          StringWriter sw = new StringWriter();
          OutputFormat outformat = OutputFormat.createPrettyPrint();
          XMLWriter writer = new XMLWriter(sw, outformat);
          writer.write(xml);
          writer.flush();
          log.debug("[" + tag + "]\n" + sw.toString() + "\n[XML_END]");    
        }
        catch(Exception e)
        {
          log.warn("Unable to dump XML (e=" + e + ")");
        }
      }
    }
  }

  //
  // private methods
  //
  
  /**
   * Validates an HttpMethod for parsing, throwing an Exception if the method 
   * is null or hasn't been executed yet.
   * 
   * @param method
   * @throws Exception
   */
  private static void validateMethod(HttpMethod method) throws Exception 
  {
    // null check method and test to see if request has been sent
    if (method == null || !method.hasBeenUsed()) 
    {
      throw new Exception("HttpMethod is null or has not been executed yet.");
    }
  }

}
