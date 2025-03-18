package sample.cafekiosk.unit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sample.cafekiosk.unit.beverage.Americano;
import sample.cafekiosk.unit.beverage.Latte;
import sample.cafekiosk.unit.order.Order;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CafekioskTest {

    @Test
    void add_manual_test() {
        Cafekiosk cafekiosk = new Cafekiosk();
        cafekiosk.add(new Americano());

        System.out.println(">>> 담긴 음료 수 : " + cafekiosk.getBeverages().size());
        System.out.println(">>> 담긴 음료 : " + cafekiosk.getBeverages().get(0).getName());
    }

    //  @DisplayName("음료 1개 추가 테스트")
    @DisplayName("음료 1개를 추가하면 주문 목록에 담긴다.")
    @Test
    void add() {
        Cafekiosk cafekiosk = new Cafekiosk();
        cafekiosk.add(new Americano());

        // assertThat(cafekiosk.getBeverages().size()).isEqualTo(1);
        assertThat(cafekiosk.getBeverages()).hasSize(1);
        assertThat(cafekiosk.getBeverages().get(0).getName()).isEqualTo("아메리카노");
    }

    @Test
    void add_several_beverages_test() {
        Cafekiosk cafekiosk = new Cafekiosk();
        Americano americano = new Americano();

        cafekiosk.add(americano, 2);

        assertThat(cafekiosk.getBeverages().get(0)).isEqualTo(americano);
        assertThat(cafekiosk.getBeverages().get(1)).isEqualTo(americano);
    }

    @Test
    void add_zero_beverages_test() {
        Cafekiosk cafekiosk = new Cafekiosk();
        Americano americano = new Americano();

        assertThatThrownBy(() -> cafekiosk.add(americano, 0))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("음료는 한 잔 이상 주문해야 합니다.");
    }

    @Test
    void remove() {
        Cafekiosk cafekiosk = new Cafekiosk();
        Americano americano = new Americano();

        cafekiosk.add(americano);
        assertThat(cafekiosk.getBeverages()).hasSize(1);

        cafekiosk.remove(americano);
        assertThat(cafekiosk.getBeverages()).isEmpty();
    }

    @Test
    void clear() {
        Cafekiosk cafekiosk = new Cafekiosk();
        Americano americano = new Americano();
        Latte latte = new Latte();

        cafekiosk.add(americano);
        cafekiosk.add(latte);
        assertThat(cafekiosk.getBeverages()).hasSize(2);

        cafekiosk.clear();
        assertThat(cafekiosk.getBeverages()).isEmpty();
    }

    @DisplayName("주문 목록에 담긴 상품들의 총 금액을 계산할 수 있다.")
    @Test
    void calculateTotalPrice() {
        // Given
        Cafekiosk cafekiosk = new Cafekiosk();
        Americano americano = new Americano();
        Latte latte = new Latte();

        cafekiosk.add(americano);
        cafekiosk.add(latte);

        // When
        int totalPrice = cafekiosk.calculateTotalPrice();

        // Then
        assertThat(totalPrice).isEqualTo(8500);
    }

    @Test
    void createOrder() {
        Cafekiosk cafekiosk = new Cafekiosk();
        Americano americano = new Americano();

        cafekiosk.add(americano);

        Order order = cafekiosk.createOrder();
        assertThat(order.getBeverages()).hasSize(1);
        assertThat(order.getBeverages().get(0).getName()).isEqualTo("아메리카노");
    }

    @Test
    void createOrderInTime() {
        Cafekiosk cafekiosk = new Cafekiosk();
        Americano americano = new Americano();
        cafekiosk.add(americano);

        Order order = cafekiosk.createOrder(LocalDateTime.of(2025, 3, 3, 21, 11));

        assertThat(order.getBeverages()).hasSize(1);
        assertThat(order.getBeverages().get(0).getName()).isEqualTo("아메리카노");
    }

    @Test
    void createOrderOutsideOpenTime() {
        Cafekiosk cafekiosk = new Cafekiosk();
        Americano americano = new Americano();
        cafekiosk.add(americano);

        assertThatThrownBy(() -> cafekiosk.createOrder(LocalDateTime.of(2025, 3, 3, 23, 1)))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("주문 시간이 아닙니다. 관리자에게 문의하세요.");
    }

}
