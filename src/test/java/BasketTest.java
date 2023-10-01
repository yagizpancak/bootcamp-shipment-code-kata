import com.trendyol.shipment.Basket;
import com.trendyol.shipment.Product;
import com.trendyol.shipment.ShipmentSize;
import com.trendyol.shipment.exception.BasketEmptyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class BasketTest {

    private Basket basket;

    @BeforeEach
    void setUp() {
        basket = new Basket();
    }

    @ParameterizedTest
    @MethodSource("shipmentSizeOfProductsAndBasketShipmentSize")
    void shouldGetOrderShipmentSizeAsExpected(List<ShipmentSize> shipmentSizesOfProducts, ShipmentSize orderShipmentSize) {
        final var products = shipmentSizesOfProducts.stream().map(Product::create).collect(Collectors.toList());

        basket.setProducts(products);

        assertThat(basket.getShipmentSize(), equalTo(orderShipmentSize));
    }

    private static Stream<Arguments> shipmentSizeOfProductsAndBasketShipmentSize() {
        return Stream.of(
                Arguments.of(Arrays.asList(ShipmentSize.SMALL, ShipmentSize.SMALL), ShipmentSize.SMALL),
                Arguments.of(Arrays.asList(ShipmentSize.SMALL, ShipmentSize.SMALL, ShipmentSize.SMALL), ShipmentSize.MEDIUM),
                Arguments.of(Arrays.asList(ShipmentSize.SMALL, ShipmentSize.SMALL, ShipmentSize.MEDIUM), ShipmentSize.MEDIUM),
                Arguments.of(Arrays.asList(ShipmentSize.SMALL, ShipmentSize.SMALL, ShipmentSize.LARGE), ShipmentSize.LARGE),
                Arguments.of(Arrays.asList(ShipmentSize.SMALL, ShipmentSize.SMALL, ShipmentSize.SMALL, ShipmentSize.SMALL), ShipmentSize.MEDIUM),
                Arguments.of(Arrays.asList(ShipmentSize.SMALL, ShipmentSize.SMALL, ShipmentSize.SMALL, ShipmentSize.LARGE), ShipmentSize.MEDIUM),
                Arguments.of(Arrays.asList(ShipmentSize.SMALL, ShipmentSize.SMALL, ShipmentSize.SMALL, ShipmentSize.SMALL, ShipmentSize.SMALL), ShipmentSize.MEDIUM),
                Arguments.of(Arrays.asList(ShipmentSize.SMALL, ShipmentSize.SMALL, ShipmentSize.MEDIUM, ShipmentSize.MEDIUM, ShipmentSize.LARGE), ShipmentSize.LARGE),
                Arguments.of(Arrays.asList(ShipmentSize.SMALL, ShipmentSize.MEDIUM), ShipmentSize.MEDIUM),
                Arguments.of(Arrays.asList(ShipmentSize.SMALL, ShipmentSize.MEDIUM, ShipmentSize.MEDIUM), ShipmentSize.MEDIUM),
                Arguments.of(Arrays.asList(ShipmentSize.MEDIUM, ShipmentSize.MEDIUM), ShipmentSize.MEDIUM),
                Arguments.of(Arrays.asList(ShipmentSize.MEDIUM, ShipmentSize.MEDIUM, ShipmentSize.MEDIUM), ShipmentSize.LARGE),
                Arguments.of(Arrays.asList(ShipmentSize.MEDIUM, ShipmentSize.MEDIUM, ShipmentSize.MEDIUM, ShipmentSize.MEDIUM), ShipmentSize.LARGE),
                Arguments.of(Arrays.asList(ShipmentSize.MEDIUM, ShipmentSize.LARGE), ShipmentSize.LARGE),
                Arguments.of(Arrays.asList(ShipmentSize.MEDIUM, ShipmentSize.MEDIUM, ShipmentSize.LARGE), ShipmentSize.LARGE),
                Arguments.of(Arrays.asList(ShipmentSize.MEDIUM, ShipmentSize.LARGE, ShipmentSize.LARGE), ShipmentSize.LARGE),
                Arguments.of(Arrays.asList(ShipmentSize.X_LARGE, ShipmentSize.X_LARGE, ShipmentSize.X_LARGE), ShipmentSize.X_LARGE),
                Arguments.of(Arrays.asList(ShipmentSize.LARGE, ShipmentSize.LARGE, ShipmentSize.LARGE), ShipmentSize.X_LARGE)
        );
    }

    @Test
    void shouldThrowExceptionWhenBasketEmpty() {
        Basket basket = new Basket();
        assertThrows(BasketEmptyException.class, basket::getShipmentSize);
    }
}
