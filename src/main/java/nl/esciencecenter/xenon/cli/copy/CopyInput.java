package nl.esciencecenter.xenon.cli.copy;

import java.util.Map;

import nl.esciencecenter.xenon.credentials.Credential;

/**
 * Data required for a source or target of a copy command
 */
public class CopyInput {
    private String scheme;
    private String location = null;
    private String path;
    private Credential credential = null;
    private boolean stream = false;
    private Map<String, String> properties = null;

    public CopyInput(String scheme, String location, String path, Credential credential) {
        this.scheme = scheme;
        this.location = location;
        if ("-".equals(path) && ("file".equals(scheme) || "local".equals(scheme))) {
            // can only stream stdin or stdout using local adaptor,
            // if not local adaptor will treat path as path, so will read/write remote file called '-'
            this.stream = true;
        }
        this.path = path;
        this.credential = credential;
    }

    public String getScheme() {
        return scheme;
    }

    public String getLocation() {
        return location;
    }

    public String getPath() {
        return path;
    }

    public Credential getCredential() {
        return credential;
    }

    public boolean isStream() {
        return stream;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public boolean isLocal() {
        return "local".equals(scheme) || "file".equals(scheme);
    }

    public boolean isAbsolute() {
        return path.startsWith("/");
    }
}
