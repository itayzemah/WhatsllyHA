
[![Whatslly](https://www.whatslly.com/wp-content/uploads/2020/08/vertical-colors.png)](https://www.whatslly.com/)
# Whatslly Home Assignment

## Endpoint Testing

This repository present tests on endpoints.
It based on a Spring Boot and contains some unit test using Juint. 

##### EndPoints: 
> https://api-us1.whatslly.com/test/ping.json

> https://api-br1.whatslly.com/test/ping.json

> https://api-eu1.whatslly.com/test/ping.json

> https://api-st1.whatslly.com/test/ping.json

Each endpoint returns JSON represented by
```
public class PingResponse {
	private boolean success;
	private String sfVersion;
	private String host;
	private String version;
	private String saEndpoint;
	private boolean newPackage;
}
```

##### The repository contains the following tests : 
* Kind of sanity test - checks if all servers up and can response
* Checks the performance of the servers by sending many http requests(overload).
* Checks that worng URL response a 404 Not Found Error.
* Verifies that the response matches the desired values.
