package wtly.boundries;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class PingResponse {
	private boolean success;
	private String sfVersion;
	private String host;
	private String version;
	private String saEndpoint;
	private boolean newPackage;
}
