package com.whut.data.model;

public class APListModel {
	
	private int id;
	private String mac;
	private int shopId;
	private String type;
	private String authcode;
	private String ssid;
	private String openid;
	private int owner;
	/***
	 * 模拟的三个数据
	 */
	private int upload;
	private int download;
	private int online;
	private String alias;
	

	public APListModel(int id,int shopId,String ssid,String alias,String mac,int upload,int dowmload,int online) {
		// TODO Auto-generated constructor stub
		
		this.id = id;
		this.shopId = shopId;
		this.ssid = ssid;
		this.alias = alias;
		this.mac = mac;
		this.upload = upload;
		this.download = dowmload;
		this.online = online;
	}
	
	public int getUpload() {
		return upload;
	}
	public void setUpload(int upload) {
		this.upload = upload;
	}
	public int getDownload() {
		return download;
	}
	public void setDownload(int download) {
		this.download = download;
	}
	public int getOnline() {
		return online;
	}
	public void setOnline(int online) {
		this.online = online;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public int getShopId() {
		return shopId;
	}
	public void setShopId(int shopId) {
		this.shopId = shopId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAuthcode() {
		return authcode;
	}
	public void setAuthcode(String authcode) {
		this.authcode = authcode;
	}
	public String getSsid() {
		return ssid;
	}
	public void setSsid(String ssid) {
		this.ssid = ssid;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public int getOwner() {
		return owner;
	}
	public void setOwner(int owner) {
		this.owner = owner;
	}
	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

}
