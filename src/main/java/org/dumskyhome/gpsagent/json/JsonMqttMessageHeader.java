package org.dumskyhome.gpsagent.json;

public class JsonMqttMessageHeader {

    private String msgId;
    private Long chipId;
    private String macAddress;
    private String ipAddress;
    private Integer authorized;
    private String securityToken;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public Long getChipId() {
        return chipId;
    }

    public void setChipId(Long chipId) {
        this.chipId = chipId;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Integer getAuthorized() {
        return authorized;
    }

    public void setAuthorized(Integer authorized) {
        this.authorized = authorized;
    }

    public String getToken() {
        return securityToken;
    }

    public void setToken(String token) {
        this.securityToken = token;
    }
}
