package com.cts.fms.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "fms", ignoreUnknownFields = false)
public class ApplicationProperties {

	private final Http http = new Http();

	private final Cache cache = new Cache();

	private final Mail mail = new Mail();

	private final Shared shared = new Shared();

	public Shared getShared() {
		return shared;
	}

	public Http getHttp() {
		return http;
	}

	public Cache getCache() {
		return cache;
	}

	public Mail getMail() {
		return mail;
	}

	public static class Http {

		public enum Version {
			V_1_1, V_2_0
		}

		private final Cache cache = new Cache();

		/**
		 * HTTP version, must be "V_1_1" (for HTTP/1.1) or V_2_0 (for (HTTP/2)
		 */
		public Version version = Version.V_1_1;

		public Cache getCache() {
			return cache;
		}

		public Version getVersion() {
			return version;
		}

		public void setVersion(Version version) {
			this.version = version;
		}

		public static class Cache {

			private int timeToLiveInDays = 1461;

			public int getTimeToLiveInDays() {
				return timeToLiveInDays;
			}

			public void setTimeToLiveInDays(int timeToLiveInDays) {
				this.timeToLiveInDays = timeToLiveInDays;
			}
		}
	}

	public static class Cache {

		private final Hazelcast hazelcast = new Hazelcast();

		private final Ehcache ehcache = new Ehcache();

		public Hazelcast getHazelcast() {
			return hazelcast;
		}

		public Ehcache getEhcache() {
			return ehcache;
		}

		public static class Hazelcast {

			private int timeToLiveSeconds = 3600;

			private int backupCount = 1;

			public int getTimeToLiveSeconds() {
				return timeToLiveSeconds;
			}

			public void setTimeToLiveSeconds(int timeToLiveSeconds) {
				this.timeToLiveSeconds = timeToLiveSeconds;
			}

			public int getBackupCount() {
				return backupCount;
			}

			public void setBackupCount(int backupCount) {
				this.backupCount = backupCount;
			}
		}

		public static class Ehcache {

			private int timeToLiveSeconds = 3600;

			private long maxEntries = 100;

			public int getTimeToLiveSeconds() {
				return timeToLiveSeconds;
			}

			public void setTimeToLiveSeconds(int timeToLiveSeconds) {
				this.timeToLiveSeconds = timeToLiveSeconds;
			}

			public long getMaxEntries() {
				return maxEntries;
			}

			public void setMaxEntries(long maxEntries) {
				this.maxEntries = maxEntries;
			}
		}
	}

	public static class Mail {

		private String from = "";

		private String baseUrl = "";

		public String getFrom() {
			return from;
		}

		public void setFrom(String from) {
			this.from = from;
		}

		public String getBaseUrl() {
			return baseUrl;
		}

		public void setBaseUrl(String baseUrl) {
			this.baseUrl = baseUrl;
		}
	}

	private final Logging logging = new Logging();

	public Logging getLogging() {
		return logging;
	}

	public static class Shared {

		private final File file = new File();

		public File getFile() {
			return file;
		}

		public static class File {

			private String path ;
			private String newPath;
			private String excelVolunteerNotAttended ;
			private String excelVolunteerUnregistered ;
			private String excelVolunteerEventInformation;
			private String excelVolunteerEventSummary ;

			public String getPath() {
				return path;
			}

			public void setPath(String path) {
				this.path = path;
			}

			public String getNewPath() {
				return newPath;
			}

			public void setNewPath(String newPath) {
				this.newPath = newPath;
			}

			public String getExcelVolunteerNotAttended() {
				return excelVolunteerNotAttended;
			}

			public void setExcelVolunteerNotAttended(String excelVolunteerNotAttended) {
				this.excelVolunteerNotAttended = excelVolunteerNotAttended;
			}

			public String getExcelVolunteerUnregistered() {
				return excelVolunteerUnregistered;
			}

			public void setExcelVolunteerUnregistered(String excelVolunteerUnregistered) {
				this.excelVolunteerUnregistered = excelVolunteerUnregistered;
			}

			public String getExcelVolunteerEventInformation() {
				return excelVolunteerEventInformation;
			}

			public void setExcelVolunteerEventInformation(String excelVolunteerEventInformation) {
				this.excelVolunteerEventInformation = excelVolunteerEventInformation;
			}

			public String getExcelVolunteerEventSummary() {
				return excelVolunteerEventSummary;
			}

			public void setExcelVolunteerEventSummary(String excelVolunteerEventSummary) {
				this.excelVolunteerEventSummary = excelVolunteerEventSummary;
			}

		}

	}

	public static class Logging {

		private final Logstash logstash = new Logstash();

		public Logstash getLogstash() {
			return logstash;
		}

		public static class Logstash {

			private boolean enabled = false;

			private String host = "localhost";

			private int port = 5000;

			private int queueSize = 512;

			public boolean isEnabled() {
				return enabled;
			}

			public void setEnabled(boolean enabled) {
				this.enabled = enabled;
			}

			public String getHost() {
				return host;
			}

			public void setHost(String host) {
				this.host = host;
			}

			public int getPort() {
				return port;
			}

			public void setPort(int port) {
				this.port = port;
			}

			public int getQueueSize() {
				return queueSize;
			}

			public void setQueueSize(int queueSize) {
				this.queueSize = queueSize;
			}
		}

	}

}
