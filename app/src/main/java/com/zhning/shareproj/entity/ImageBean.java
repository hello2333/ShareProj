package com.zhning.shareproj.entity;

import java.io.Serializable;

public class ImageBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int imageid;
	private int bucketid;
	private String imagename;
	private String bucketname;
	private String data;
	private boolean selected;
	
	public ImageBean(int imageid, int buckerid, String imagename,
			String bucketname, String data) {
		super();
		this.imageid = imageid;
		this.bucketid = buckerid;
		this.imagename = imagename;
		this.bucketname = bucketname;
		this.data = data;
		selected = false;
	}

	public int getImageid() {
		return imageid;
	}

	public void setImageid(int imageid) {
		this.imageid = imageid;
	}

	public int getBucketid() {
		return bucketid;
	}

	public void setBucketid(int bucketid) {
		this.bucketid = bucketid;
	}

	public String getImagename() {
		return imagename;
	}

	public void setImagename(String imagename) {
		this.imagename = imagename;
	}

	public String getBucketname() {
		return bucketname;
	}

	public void setBucketname(String bucketname) {
		this.bucketname = bucketname;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public void select() {
		selected = true;
	}
	
	public void deselect() {
		selected = false;
	}
}
