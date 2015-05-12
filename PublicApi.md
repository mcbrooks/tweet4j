# Introduction #

tweet4j offers a PublicApi interface to the [Twitter API](http://apiwiki.twitter.com/REST+API+Documentation) methods that don't require any user authentication.


# Details #
Support for API methods that don't require user authentication:
Generally the user will use the `PublicApi` interface, instantiating a `new PublicApiClient()` instance with a no-parameter-constructor.  The example below shows the methods that are available with this interface.

```
// instantiate a PublicApi instance
PublicApi api = new PublicApiClient();

// get the twitter public timeline
List<Status> publicTimeline = api.getPublicTimeline();

// retrieve a twitter user by userId or screenName
User user = api.getUser(userId);

// retrieve a twitter user by email address
User user = api.getUserByEmail(email);

// retrieve a twitter status by id
Status status = api.getStatus(statusId);
```

If these methods don't fill your requirements, you'll probably need to look at the UserApi interface.