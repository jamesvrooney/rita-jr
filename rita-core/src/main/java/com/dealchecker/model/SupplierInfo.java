package com.dealchecker.model;

public final class SupplierInfo {
	private final Integer supplierId;
	private final String supplierKey;
	
	protected SupplierInfo() {
		this.supplierId = null;
		this.supplierKey = null;
	}
	
	public SupplierInfo(Integer supplierId, String supplierKey) {
		this.supplierId = supplierId;
		this.supplierKey = supplierKey;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public String getSupplierKey() {
		return supplierKey;
	}
	
	static SupplierInfo dummySupplierInfo() {
		return new SupplierInfo(0, "");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((supplierId == null) ? 0 : supplierId.hashCode());
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
		SupplierInfo other = (SupplierInfo) obj;
		if (supplierId == null) {
			if (other.supplierId != null)
				return false;
		} else if (!supplierId.equals(other.supplierId))
			return false;
		return true;
	}
}
