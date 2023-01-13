package com.onecric.live.model;

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
    private int android_mandatory_update_sandbox;
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
        return android_mandatory_update_sandbox;
    }

    public void setAndroidMandatoryUpdateSandbox(int androidMandatoryUpdateSandbox) {
        this.android_mandatory_update_sandbox = androidMandatoryUpdateSandbox;
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

    public String getCountryListAbbr() {
        StringBuilder county = new StringBuilder();
        if (CountryCode == null || CountryCode.size() <= 0) {
            return null;
        }
        for (CountryCodeBean bean : CountryCode) {
            county.append(bean.getCountry_code() + ",");
        }
        return county.substring(0, county.length() - 1);
    }
}
