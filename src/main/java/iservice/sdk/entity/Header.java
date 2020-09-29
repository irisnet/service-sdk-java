package iservice.sdk.entity;

import iservice.sdk.enums.EncryptionType;

/**
 * @author Yelong
 */
public class Header {
    private String version = "1.0";
    private Location location = new Location();

    private String encoding;
    private Encryption encryption;

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

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public Encryption getEncryption() {
        return encryption;
    }

    public void setEncryption(Encryption encryption) {
        this.encryption = encryption;
    }

    public static class Location {
        private String url;
        private String hash;

        public Location() {
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getHash() {
            return hash;
        }

        public void setHash(String hash) {
            this.hash = hash;
        }

        @Override
        public String toString() {
            return "Location{" +
                    "url='" + url + '\'' +
                    ", hash='" + hash + '\'' +
                    '}';
        }
    }

    public static class Encryption {
        private EncryptionType encryptionType;
        private String publicKey;

        public EncryptionType getEncryptionType() {
            return encryptionType;
        }

        public void setEncryptionType(EncryptionType encryptionType) {
            this.encryptionType = encryptionType;
        }

        public String getPublicKey() {
            return publicKey;
        }

        public void setPublicKey(String publicKey) {
            this.publicKey = publicKey;
        }
    }
}
