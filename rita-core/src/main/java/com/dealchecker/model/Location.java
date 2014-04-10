package com.dealchecker.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name="world66map")
public class Location {
	@Id
	@GeneratedValue
	@Column(name="w66map_id")
	private Integer id;

	private String name;

	@Column(name="display_name")
	private String displayName;

	private Integer status;
	private String code;
	private Double lat;
	private Double lng;
	private Integer rating;
	
	public Location() {
		this.id = null;
		this.name = null;
		this.displayName = null;
		this.status = null;
		this.code = null;
		this.lat = null;
		this.lng = null;
		this.rating = null;
	}

	private Location(Integer id, String name, String displayName,
			Integer status, String code, Double lat, Double lng, Integer rating) {
		this.id = id;
		this.name = name;
		this.displayName = displayName;
		this.status = status;
		this.code = code;
		this.lat = lat;
		this.lng = lng;
		this.rating = rating;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public Integer getStatus() {
		return status;
	}

	public String getCode() {
		return code;
	}

	public Double getLat() {
		return lat;
	}

	public Double getLng() {
		return lng;
	}

	public Integer getRating() {
		return rating;
	}
	
	public static final class Builder {
		private Integer id;
		private String name;
		private String displayName;
		private Integer status;
		private String code;
		private Double lat;
		private Double lng;
		private Integer rating;
		
		public Builder() {}
		
		public Builder withId(Integer id) {
			this.id = id;
			return this;
		}

		public Builder withName(String name) {
			this.name = name;
			return this;
		}
		
		public Builder withDisplayName(String displayName) {
			this.displayName = displayName;
			return this;
		}
		
		public Builder withStatus(Integer status) {
			this.status= status;
			return this;
		}

		public Builder withCode(String code) {
			this.code= code;
			return this;
		}

		public Builder withLat(Double lat) {
			this.lat= lat;
			return this;
		}

		public Builder withLng(Double lng) {
			this.lng = lng;
			return this;
		}
		
		public Builder withRating(Integer rating) {
			this.rating = rating;
			return this;
		}
		
		public Location build() {
			return new Location(id, name, displayName, status, code, lat, lng, rating);
		}
	}
}
