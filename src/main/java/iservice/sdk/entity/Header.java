package iservice.sdk.entity;

/**
 * @author Yelong
 */
public class Header {
    private String version = "1.0";
    private Location location = new Location();

    public Header() {
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public class Location {
        private String status = "off";

        public Location() {
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
