package com.dealchecker.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Objects;

public final class Deal {

	private final Integer supplierId;
	private final String supplierName;
	
	private final String departure;
	private final Integer departureId;

	private final String destination;
	private final Integer destinationId;
	
	private final Date departureDate;
	private final Date returnDate;
	
	private final BigDecimal price;
	private final String currency;
	private final String deeplink;
	
	private final Map<String, Object> additionalAttributes;
	
	public Deal() {
		this.supplierId = null;
		this.supplierName = null;
		this.departure = null;
		this.departureId = null;
		this.destination = null;
		this.destinationId = null;
		this.departureDate = null;
		this.returnDate = null;
		this.price = null;
		this.currency = null;
		this.deeplink = null;
		this.additionalAttributes = null;
	}

	private Deal(Integer supplierId, String supplierName, String departure, Integer departureId,
			String destination, Integer destinationId, Date departureDate, Date returnDate,
			BigDecimal price, String currency, String deeplink,
			Map<String, Object> additionalAttributes) {
		this.supplierId = supplierId;
		this.supplierName = supplierName;
		this.departure = departure;
		this.departureId = departureId;
		this.destination = destination;
		this.destinationId = destinationId;
		this.departureDate = departureDate;
		this.returnDate = returnDate;
		this.price = price;
		this.currency = currency;
		this.deeplink = deeplink;
		this.additionalAttributes = additionalAttributes;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public String getDeparture() {
		return departure;
	}

	public String getDestination() {
		return destination;
	}

	public Date getDepartureDate() {
		return departureDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public String getCurrency() {
		return currency;
	}

	public String getDeeplink() {
		return deeplink;
	}

	public Map<String, Object> getAdditionalAttributes() {
		return additionalAttributes;
	}
	
	@Override
	public String toString() {
		return Objects.toStringHelper(this.getClass())
				.add("supplierId", supplierId)
				.add("supplierName", supplierName)
				.add("departure", departure)
				.add("departureId", departureId)
				.add("destination", destination)
				.add("destinationId", destinationId)
				.add("departureDate", departureDate)
				.add("returnDate", returnDate)
				.add("price", price)
				.add("currency", currency)
				.add("deeplink", deeplink)
				.add("additionalAttributes", additionalAttributes)
				.toString();
	}
	
	public static final class Builder {
		private Integer supplierId;
		private String supplierName;
		
		private String departure;
		private Integer departureId;

		private String destination;
		private Integer destinationId;
		
		private Date departureDate;
		private Date returnDate;
		
		private BigDecimal price;
		private String currency;
		private String deeplink;
		
		private Map<String, Object> additionalAttributes;
		
		public Builder() {
			additionalAttributes = new HashMap<>();
		}
		
		public Builder withSupplierId(Integer supplierId) {
			this.supplierId = supplierId;
			return this;
		}

		public Builder withSupplierName(String supplierName) {
			this.supplierName = supplierName;
			return this;
		}
		
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

		public Builder withDepartureDate(Date departureDate) {
			this.departureDate = departureDate;
			return this;
		}
		
		public Builder withReturnDate(Date returnDate) {
			this.returnDate= returnDate;
			return this;
		}
		
		public Builder withPrice(BigDecimal price) {
			this.price= price;
			return this;
		}
		
		public Builder withCurrency(String currency) {
			this.currency= currency;
			return this;
		}
		
		public Builder withDeeplink(String deeplink) {
			this.deeplink= deeplink;
			return this;
		}
		
		public Builder withAdditionalAttribute(String attributeName, Object attribute) {
			this.additionalAttributes.put(attributeName, attribute);
			return this;
		}
		
		public Builder withAdditionalAttributes(Map<String, Object> attributes) {
			this.additionalAttributes.putAll(attributes);
			return this;
		}

		public Deal build() {
			return new Deal(supplierId, 
					supplierName, 
					departure,
					departureId,
					destination, 
					destinationId,
					departureDate, 
					returnDate, 
					price,
					currency,
					deeplink, 
					additionalAttributes);
		}
	}
	
}
