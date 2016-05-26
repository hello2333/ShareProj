package com.zhning.shareproj.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class AlbumBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int count;
	private int cover;
	private String name;
	private ArrayList<ImageBean> images;
	
	public AlbumBean(int count, int cover, String name) {
		super();
		this.count = count;
		this.cover = cover;
		this.name = name;
		images = new ArrayList<ImageBean>();
	}

	public int getCount() {
		return count;
	}

	public int getCover() {
		return cover;
	}

	public void setCover(int cover) {
		this.cover = cover;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<ImageBean> getImages() {
		return images;
	}

	public void addImage(ImageBean image) {
		images.add(image);
		count++;
	}
	
}
