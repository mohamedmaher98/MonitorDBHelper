package classes;

import java.util.List;

public class TempViewPage {

	private String lastTemp;
	private String lastTime;
	private List<Temp> last10Reads;
	private String serviceStatus;
	private int failedAttempts;
	private String lastError;

	public String getLastTemp() {
		return lastTemp;
	}

	public void setLastTemp(String lastTemp) {
		this.lastTemp = lastTemp;
	}

	public String getLastTime() {
		return lastTime;
	}

	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}

	public List<Temp> getLast10Reads() {
		return last10Reads;
	}

	public void setLast10Reads(List<Temp> last10Reads) {
		this.last10Reads = last10Reads;
	}

	public String getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	public int getFailedAttempts() {
		return failedAttempts;
	}

	public void setFailedAttempts(int failedAttempts) {
		this.failedAttempts = failedAttempts;
	}

	public String getLastError() {
		return lastError;
	}

	public void setLastError(String lastError) {
		this.lastError = lastError;
	}

}
