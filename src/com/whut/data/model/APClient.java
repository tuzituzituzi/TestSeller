package com.whut.data.model;

import android.R.bool;

public class APClient {

	
	private String clientName;
	private ClientDetail client;
	

	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public ClientDetail getClient() {
		return client;
	}
	public void setClient(ClientDetail client) {
		this.client = client;
	}
	
	public static class ClientDetail {
		private int cnnTime;
		private int authTime;
		private int state;
		private String name;
		private int upload;
		private int download;
		private int isBlack;
		private int isWhite;
		private int isVip;
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
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
		public int getIsBlack() {
			return isBlack;
		}
		public void setIsBlack(int isBlack) {
			this.isBlack = isBlack;
		}
		public int getIsWhite() {
			return isWhite;
		}
		public void setIsWhite(int isWhite) {
			this.isWhite = isWhite;
		}
		public int getIsVip() {
			return isVip;
		}
		public void setIsVip(int isVip) {
			this.isVip = isVip;
		}
		public int getCnnTime() {
			return cnnTime;
		}
		public void setCnnTime(int cnnTime) {
			this.cnnTime = cnnTime;
		}
		public int getAuthTime() {
			return authTime;
		}
		public void setAuthTime(int authTime) {
			this.authTime = authTime;
		}
		public int getState() {
			return state;
		}
		public void setState(int state) {
			this.state = state;
		}
		

	}
	
}
