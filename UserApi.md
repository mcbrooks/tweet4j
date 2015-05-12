# Introduction #

tweet4j offers a UserApi interface to the methods of the [Twitter API](http://apiwiki.twitter.com/REST+API+Documentation) that require user authentication.

# Details #

The UserApi interface extends the PublicApi interface, offering all of those available methods and also the methods that require user authentication.
(more documentation coming soon)

```
// instantiate new UserApi
UserApi api = new UserApiClient(username, password);

// update status
Status newStatus = api.updateStatus("Testing, testing");
```