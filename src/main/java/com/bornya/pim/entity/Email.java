package com.bornya.pim.entity;

//邮件实体
public class Email {

    private int id;
    private String campaignName; //公司名字
    private String subject;
    private String sentAt;  //发送日期
    private String sent;  //发送日期
    private String delivered;  //发送日期
    private String total_clicks;  //发送日期
    private String unique_clicks;  //发送日期
    private String soft_bounced;  //发送日期
    private String hard_bounced;  //发送日期
    private String opt_outs;  //发送日期
    private String spam_complaints;  //发送日期
    private String opens;  //发送日期
    private String unique_opens;  //发送日期
    private String delivery_rate;  //发送日期
    private String opens_rate;  //发送日期
    private String click_through_rate;  //发送日期
    private String unique_click_through_rate;  //发送日期
    private String click_open_ratio;  //发送日期
    private String opt_out_rate;  //发送日期
    private String spam_complaint_rate;  //发送日期

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSentAt() {
        return sentAt;
    }

    public void setSentAt(String sentAt) {
        this.sentAt = sentAt;
    }

    public String getSent() {
        return sent;
    }

    public void setSent(String sent) {
        this.sent = sent;
    }

    public String getDelivered() {
        return delivered;
    }

    public void setDelivered(String delivered) {
        this.delivered = delivered;
    }

    public String getTotal_clicks() {
        return total_clicks;
    }

    public void setTotal_clicks(String total_clicks) {
        this.total_clicks = total_clicks;
    }

    public String getUnique_clicks() {
        return unique_clicks;
    }

    public void setUnique_clicks(String unique_clicks) {
        this.unique_clicks = unique_clicks;
    }

    public String getSoft_bounced() {
        return soft_bounced;
    }

    public void setSoft_bounced(String soft_bounced) {
        this.soft_bounced = soft_bounced;
    }

    public String getHard_bounced() {
        return hard_bounced;
    }

    public void setHard_bounced(String hard_bounced) {
        this.hard_bounced = hard_bounced;
    }

    public String getOpt_outs() {
        return opt_outs;
    }

    public void setOpt_outs(String opt_outs) {
        this.opt_outs = opt_outs;
    }

    public String getSpam_complaints() {
        return spam_complaints;
    }

    public void setSpam_complaints(String spam_complaints) {
        this.spam_complaints = spam_complaints;
    }

    public String getOpens() {
        return opens;
    }

    public void setOpens(String opens) {
        this.opens = opens;
    }

    public String getUnique_opens() {
        return unique_opens;
    }

    public void setUnique_opens(String unique_opens) {
        this.unique_opens = unique_opens;
    }

    public String getDelivery_rate() {
        return delivery_rate;
    }

    public void setDelivery_rate(String delivery_rate) {
        this.delivery_rate = delivery_rate;
    }

    public String getOpens_rate() {
        return opens_rate;
    }

    public void setOpens_rate(String opens_rate) {
        this.opens_rate = opens_rate;
    }

    public String getClick_through_rate() {
        return click_through_rate;
    }

    public void setClick_through_rate(String click_through_rate) {
        this.click_through_rate = click_through_rate;
    }

    public String getUnique_click_through_rate() {
        return unique_click_through_rate;
    }

    public void setUnique_click_through_rate(String unique_click_through_rate) {
        this.unique_click_through_rate = unique_click_through_rate;
    }

    public String getClick_open_ratio() {
        return click_open_ratio;
    }

    public void setClick_open_ratio(String click_open_ratio) {
        this.click_open_ratio = click_open_ratio;
    }

    public String getOpt_out_rate() {
        return opt_out_rate;
    }

    public void setOpt_out_rate(String opt_out_rate) {
        this.opt_out_rate = opt_out_rate;
    }

    public String getSpam_complaint_rate() {
        return spam_complaint_rate;
    }

    public void setSpam_complaint_rate(String spam_complaint_rate) {
        this.spam_complaint_rate = spam_complaint_rate;
    }
}
