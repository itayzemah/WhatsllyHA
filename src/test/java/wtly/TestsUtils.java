package wtly;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import wtly.boundries.PingResponse;

@NoArgsConstructor
@Component
@Getter
public class TestsUtils {
	@Value("${test.wtly.servers}")
	private List<String> serversNames;

	@Value("${test.wtly.other.saEndpoint.servers}")
	private List<String> otherSaEndpointServersNames;

	@Value("${test.wtly.url.base.path}")
	private String baseUrl;

	@Value("${test.wtly.url.test.path.ok}")
	private String okEndUrl;

	@Value("${test.wtly.url.test.path.invalid}")
	private String badEndUrl;

	@Value("${test.wtly.app.version}")
	private String version;
	@Value("${test.wtly.salesforceversion}")
	private String salesforceversion;
	@Value("${test.wtly.saEndpoint.a}")
	private String saEndpointA;
	@Value("${test.wtly.saEndpoint.b}")
	private String saEndpointB;
	@Value("${test.wtly.newPackage}")
	private boolean isNewPackage;
	@Value("${test.wtly.overload.requestNumber}")
	private int numberOfCalls;

	public String buildPath(String serverName, String endUrl) {
		return new StringBuilder().append(getFixBaseUrlByServerName(serverName)).append(endUrl).toString();
	}

	public List<String> getAllOkUrls() {
		return serversNames.stream().map(sn -> buildPath(sn, this.okEndUrl)).collect(Collectors.toList());
	}

	public List<String> getAllBadUrls() {
		return serversNames.stream().map(sn -> buildPath(sn, this.badEndUrl)).collect(Collectors.toList());
	}

	public PingResponse getOkResponseWithoutHost(String serverName) {
		PingResponse rv = new PingResponse();
		if (otherSaEndpointServersNames.contains(serverName)) {
			rv.setSaEndpoint(saEndpointB);
		} else {
			rv.setSaEndpoint(saEndpointA);
		}

		rv.setSfVersion(salesforceversion);
		rv.setVersion(version);
		rv.setNewPackage(isNewPackage);
		rv.setSuccess(true);
		rv.setHost(getFixBaseUrlByServerName(serverName));
		return rv;
	}

	private String getFixBaseUrlByServerName(String serverName) {
		return String.format(this.baseUrl, serverName);
	}

}
