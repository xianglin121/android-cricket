package com.longya.live.model;

import android.widget.TextView;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/12/16
 */
public class ConfigurationBean {
    private String announcement;
    private String websocket;
    private String CustomerService;
    private String privacy_policy;
    private String user_agreement;
    private String h5_url;
    private int androidMandatoryUpdateSandbox;
    private String androidVersionMumber;
    private String androidDownloadUrl;
    private String androidDownloadText;
    private List<CountryCodeBean> CountryCode;
    private String live_notice;

    public String getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(String announcement) {
        this.announcement = announcement;
    }

    public String getWebsocket() {
        return websocket;
    }

    public void setWebsocket(String websocket) {
        this.websocket = websocket;
    }

    public String getCustomerService() {
        return CustomerService;
    }

    public void setCustomerService(String customerService) {
        CustomerService = customerService;
    }

    public String getPrivacy_policy() {
        return privacy_policy;
    }

    public void setPrivacy_policy(String privacy_policy) {
        this.privacy_policy = privacy_policy;
    }

    public String getUser_agreement() {
        return user_agreement;
    }

    public void setUser_agreement(String user_agreement) {
        this.user_agreement = user_agreement;
    }

    public String getH5_url() {
        return h5_url;
    }

    public void setH5_url(String h5_url) {
        this.h5_url = h5_url;
    }

    public int getAndroidMandatoryUpdateSandbox() {
        return androidMandatoryUpdateSandbox;
    }

    public void setAndroidMandatoryUpdateSandbox(int androidMandatoryUpdateSandbox) {
        this.androidMandatoryUpdateSandbox = androidMandatoryUpdateSandbox;
    }

    public String getAndroidVersionMumber() {
        return androidVersionMumber;
    }

    public void setAndroidVersionMumber(String androidVersionMumber) {
        this.androidVersionMumber = androidVersionMumber;
    }

    public String getAndroidDownloadUrl() {
        return androidDownloadUrl;
    }

    public void setAndroidDownloadUrl(String androidDownloadUrl) {
        this.androidDownloadUrl = androidDownloadUrl;
    }

    public String getAndroidDownloadText() {
        return androidDownloadText;
    }

    public void setAndroidDownloadText(String androidDownloadText) {
        this.androidDownloadText = androidDownloadText;
    }

    public List<CountryCodeBean> getCountryCode() {
        return CountryCode;
    }

    public void setCountryCode(List<CountryCodeBean> countryCode) {
        CountryCode = countryCode;
    }

    public String getLive_notice() {
        return live_notice;
    }

    public void setLive_notice(String live_notice) {
        this.live_notice = live_notice;
    }
}
