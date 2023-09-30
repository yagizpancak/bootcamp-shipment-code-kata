package com.trendyol.shipment;

import com.trendyol.shipment.exception.BasketEmptyException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class ShipmentHandler {

	private static final int SIZE_THRESHOLD = 3;
	private List<Product> products;

	public ShipmentHandler(Basket basket) {
		this.products = basket.getProducts();
	}

	public ShipmentSize getShipmentSize() {
		if (products == null || products.isEmpty()) {
			throw new BasketEmptyException();
		}

		Optional<ShipmentSize> sameSizeExceedThreshold = getSameSizeExceedThreshold();

		if (sameSizeExceedThreshold.isPresent()){
			return sameSizeExceedThreshold.get().getNextSize();
		}

		return findLargestSizeProduct();
	}

	private Optional<ShipmentSize> getSameSizeExceedThreshold() {
		return products.stream()
				.collect(groupingBy(Product::getSize, counting()))
				.entrySet()
				.stream()
				.filter(sizeEntry -> sizeEntry.getValue() >= SIZE_THRESHOLD)
				.map(Map.Entry::getKey)
				.findFirst();
	}

	private ShipmentSize findLargestSizeProduct() {
		return products.stream()
				.map(Product::getSize)
				.max(ShipmentSize::compareTo)
				.orElse(null);
	}
}
