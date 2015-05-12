# tweet4j #

## Description ##
Tweet4j aims to be a comprehensive, efficient Java interface for the Twitter REST and Search API's. Right now the public API (no user authentication) has been implemented, and 80% of the authentication based API (User API) has been implemented with development continuing on the project to finish implementing the user based API and implement the search API.

### updated 8/1/2009 ###
I've made available for download the 0.4 version of the library, which implements the REST API, and the Search API. More documentation to follow


---

## Using ##
Download the latest JAR @: http://code.google.com/p/tweet4j/downloads/


## Examples ##

### RestApi ###
Support for API methods that don't require user authentication:
```
// creating a REST API client
RestApi restApi = new RestApiClient();

// retrieving the public timeline
List<Status> publicTimeline = restApi.getPublicTimeline();

// retrieving a publicly available user
User user = restApi.getUser(userId);
```

Support for most user authenticated API methods (profile-related methods still in development):
```
// creating an authentication for a user
Authentication auth = new Authentication(username, password);

// updating a user's status
Status status = restApi.updateStatus(auth, "I wrote a twitter library and all I got was this stupid T-shirt?");
Status destroyedStatus = restApi.destroyStatus(auth, status.getId());

// getting a list of the user's followers
List<User> followers = restApi.getFollowers(auth);
```


### Search API ###
Support for the Search API, you can execute a string based search:
```
// create a Search API client
SearchApi searchApi = new SearchApiClient();

// get current trends
List<Topic> trends = searchApi.getCurrentTrends();

// execute a twitter search
String queryString = "twitter api";
SearchResponse response = searchApi.search(queryString);
List<SearchResult> results = response.getResults();
SearchResult firstResult = results.get(0);
String statusText = firstResult.getText();
```

Or you can create a SearchQuery object to execute:
```
SearchQuery query = new SearchQuery("twitter api").fromUser("twitterapi");
SearchResult firstResult = searchApi.search(query).getResults().get(0);
```

---


## Goals ##
  * Implementing the User API (90% done, updating profile methods outstanding)
  * Adding more source files
  * Improving Documentation, Wiki Pages, JavaDoc
  * Adding Maven Distribution Support
  * Adding support for different formats (JSON, RSS?, ATOM?)
  * More meaningful exceptions


## Dependencies ##

This JAR is dependent upon the following JAR's (and their dependencies, etc.) at runtime:

  * [commons-httpclient-3.1](http://hc.apache.org/downloads.cgi)
  * [dom4j-1.6.1](http://www.dom4j.org/dom4j-1.6.1/download.html)
  * [jaxen-1.1.1](http://jaxen.codehaus.org/releases.html)
  * [log4j-1.2.14](http://archive.apache.org/dist/logging/log4j/1.2.14/)

## RoadMap ##

  * 0.5 release - mavenized release + minor fixes

## Change Log ##

  * 08.02.2009 - 0.4 jar uploaded with REST and Search API's fully implemented.
  * 02.05.2009 - 0.3 jar uploaded with new social graph methods included.
  * 01.20.2009 - 0.2 jar uploaded with 90% of UserApi implemented.
  * 01.18.2009 - Project created, 0.1 jar uploaded, with PublicApi methods implemented.