package com.trendyol.shipment;

import com.trendyol.shipment.exception.BasketEmptyException;

import java.util.List;

public class Basket {

    private List<Product> products;

    public ShipmentSize getShipmentSize() {
        if (products.isEmpty()) {
            throw new BasketEmptyException();
        }
        return null;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
