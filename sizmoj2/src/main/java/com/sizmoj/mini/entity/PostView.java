package com.sizmoj.mini.entity;

import java.util.ArrayList;
import java.util.List;

public class PostView {
	private String dateTime;
	private List<String> urlname = new ArrayList<String>();
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public List<String> getUrlname() {
		return urlname;
	}
	public void setUrlname(List<String> urlname) {
		this.urlname = urlname;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dateTime == null) ? 0 : dateTime.hashCode());
		result = prime * result + ((urlname == null) ? 0 : urlname.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PostView other = (PostView) obj;
		if (dateTime == null) {
			if (other.dateTime != null)
				return false;
		} else if (!dateTime.equals(other.dateTime))
			return false;
		if (urlname == null) {
			if (other.urlname != null)
				return false;
		} else if (!urlname.equals(other.urlname))
			return false;
		return true;
	}
	
	
}

