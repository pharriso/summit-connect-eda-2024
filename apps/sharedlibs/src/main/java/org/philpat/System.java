
package org.philpat;

public class System {

    private String hostname;
    private String os;
    private String platform;
    private String platformFamily;
    private String platformVersion;
    private String arch;
    private String libcType;

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getPlatformFamily() {
        return platformFamily;
    }

    public void setPlatformFamily(String platformFamily) {
        this.platformFamily = platformFamily;
    }

    public String getPlatformVersion() {
        return platformVersion;
    }

    public void setPlatformVersion(String platformVersion) {
        this.platformVersion = platformVersion;
    }

    public String getArch() {
        return arch;
    }

    public void setArch(String arch) {
        this.arch = arch;
    }

    public String getLibcType() {
        return libcType;
    }

    public void setLibcType(String libcType) {
        this.libcType = libcType;
    }

}
