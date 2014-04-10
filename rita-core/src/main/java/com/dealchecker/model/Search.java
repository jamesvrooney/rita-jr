package com.dealchecker.model;

public final class Search {

	private final String type;
	private final String departure;
	private final Integer departureId;
	private final String destination;
	private final Integer destinationId;
	private final String departureDate;
	private final String returnDate;
	private final Integer adults;
	private final Integer children;
	private final Integer infants;
	
	private final String ticketType;
	private final String cabinClass;

	private final Integer noRooms;
	
	private final Integer starRating;
	
	private final String boardType;
	private final String dateRange;

	private final String pickUpHour;
	private final String dropOffHour;
	private final Integer driverAge;

	private final String duration;
	private final String cruiseLine;
	private final Boolean fromUk;
	private final Boolean hotelStay;
	
	protected Search() {
		this.type = null;
		this.departure = null;
		this.departureId = null;
		this.destination = null;
		this.destinationId = null;
		this.departureDate = null;
		this.returnDate = null;
		this.adults = null;
		this.children = null;
		this.infants = null;
		this.ticketType = null;
		this.cabinClass = null;
		this.noRooms = null;
		this.starRating = null;
		this.boardType = null;
		this.dateRange = null;
		this.pickUpHour = null;
		this.dropOffHour = null;
		this.driverAge = null;
		this.duration = null;
		this.cruiseLine = null;
		this.fromUk = null;
		this.hotelStay = null;
	}

	public Search(String type, String departure, Integer departureId,
			String destination, Integer destinationId, String departureDate, String returnDate,
			Integer adults, Integer children, Integer infants,
			String ticketType, String cabinClass, Integer noRooms,
			Integer starRating, String boardType, String dateRange,
			String pickUpHour, String dropOffHour, Integer driverAge,
			String duration, String cruiseLine, Boolean fromUk,
			Boolean hotelStay) {
		this.type = type;
		this.departure = departure;
		this.departureId = departureId;
		this.destination = destination;
		this.destinationId = destinationId;
		this.departureDate = departureDate;
		this.returnDate = returnDate;
		this.adults = adults;
		this.children = children;
		this.infants = infants;
		this.ticketType = ticketType;
		this.cabinClass = cabinClass;
		this.noRooms = noRooms;
		this.starRating = starRating;
		this.boardType = boardType;
		this.dateRange = dateRange;
		this.pickUpHour = pickUpHour;
		this.dropOffHour = dropOffHour;
		this.driverAge = driverAge;
		this.duration = duration;
		this.cruiseLine = cruiseLine;
		this.fromUk = fromUk;
		this.hotelStay = hotelStay;
	}

	public String getType() {
		return type;
	}

	public String getDeparture() {
		return departure;
	}

	public Integer getDepartureId() {
		return departureId;
	}

	public String getDestination() {
		return destination;
	}

	public Integer getDestinationId() {
		return destinationId;
	}

	public String getDepartureDate() {
		return departureDate;
	}

	public String getReturnDate() {
		return returnDate;
	}

	public Integer getAdults() {
		return adults;
	}

	public Integer getChildren() {
		return children;
	}

	public Integer getInfants() {
		return infants;
	}

	public String getTicketType() {
		return ticketType;
	}

	public String getCabinClass() {
		return cabinClass;
	}

	public Integer getNoRooms() {
		return noRooms;
	}

	public Integer getStarRating() {
		return starRating;
	}

	public String getBoardType() {
		return boardType;
	}

	public String getDateRange() {
		return dateRange;
	}

	public String getPickUpHour() {
		return pickUpHour;
	}

	public String getDropOffHour() {
		return dropOffHour;
	}

	public Integer getDriverAge() {
		return driverAge;
	}

	public String getDuration() {
		return duration;
	}

	public String getCruiseLine() {
		return cruiseLine;
	}

	public Boolean getFromUk() {
		return fromUk;
	}

	public Boolean getHotelStay() {
		return hotelStay;
	}
	
	public static final class Builder {
		private String departure = null;
		private Integer departureId = null;
		private String destination = null;
		private Integer destinationId = null;
		private String departureDate = null;
		private String returnDate = null;
		private Integer adults = null;
		private Integer children = null;
		private Integer infants = null;
		private Integer starRating = null;
		private String boardType = null;
		private String dateRange = null;
		
		public Builder() {}
		
		public Builder withDeparture(String departure) {
			this.departure = departure;
			return this;
		}
		public Builder withDepartureId(Integer departureId) {
			this.departureId = departureId;
			return this;
		}
		
		public Builder withDestination(String destination) {
			this.destination = destination;
			return this;
		}
		
		public Builder withDestinationId(Integer destinationId) {
			this.destinationId = destinationId;
			return this;
		}
		
		public Builder withDepartureDate(String departureDate) {
			this.departureDate = departureDate;
			return this;
		}
		
		public Builder withReturnDate(String returnDate) {
			this.returnDate = returnDate;
			return this;
		}
		
		public Builder withAdults(Integer adults) {
			this.adults = adults;
			return this;
		}
		
		public Builder withChildren(Integer children) {
			this.children = children;
			return this;
		}
		
		public Builder withInfants(Integer infants) {
			this.infants = infants;
			return this;
		}
		
		public Builder withStarRating(Integer starRating) {
			this.starRating = starRating;
			return this;
		}
		
		public Builder withBoardType(String boardType) {
			this.boardType = boardType;
			return this;
		}

		public Builder withDateRange(String dateRange) {
			this.dateRange = dateRange;
			return this;
		}
		
		public Search build() {
			return new Search(null, this.departure, this.departureId, this.destination, this.destinationId, this.departureDate, this.returnDate, this.adults, this.children, this.infants, null, null, null, this.starRating, this.boardType, this.dateRange, null, null, null, null, null, null, null);
		}
	}
}
