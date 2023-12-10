import org.example.graduationProject.Cart;
import org.example.graduationProject.Product;
import org.example.graduationProject.Shop;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class ShopTest {
    Shop shop;
    Cart cart;
    // Создаю набор продуктов для моего магазина
    public static List<Product> getStoreItems() {
        List<Product> products = new ArrayList<>();

        // Три массива Названия, Цены, Кол-во
        String[] productNames = {"Помидоры", "Огурцы", "Лук", "Картошка", "Капуста", "Морковка", "Яблока",
                "Банан", "Апельсин", "Мандарин", "Киви", "Мука"};
        Double[] productPrice = {100.00d, 70.00d, 50.00d, 40.00d, 35.00d, 15.00d, 90.00d,
                130.00d, 110.00d, 100.00d, 80.00d, 20.00d};
        Integer[] stock = {30, 30, 50, 60, 20, 10, 60,
                30, 10, 30, 10, 80};

        // Последовательно наполняю список продуктами
        for (int i = 0; i < productNames.length; i++) {
            products.add(new Product(i + 1, productNames[i], productPrice[i], stock[i]));
        }

        return products;
    }
    private ByteArrayOutputStream output = new ByteArrayOutputStream();
    /**
     * 2.1. Разработываю модульный тест для проверки, что общая стоимость корзины с разными
     * товарами корректно рассчитывается с ожидаемым результатом
     */
    @BeforeEach
    void setUp() {
        shop = new Shop(getStoreItems());
        cart = new Cart(shop);// Стоимость корзины посчиталась корректно
        }
    @Test
    void priceCartIsCorrectCalculated() {
        // Arrange (Первое действие подготовка, но он уже выполнен в верхнем методе)
        // Act (Второе выполнение)
        cart.addProductToCartByID(1); // 100 +
        cart.addProductToCartByID(2); // 70 +
        // Assert (Третье проверка утверждения)
        assertThat(cart.getTotalPrice()).isEqualTo(100 + 70);
    }
    /**
     * 2.2. Создаю модульный тест для проверки, что общая стоимость корзины с множественными
     * экземплярами одного и того же продукта корректно рассчитывается с ожидаемым результатом
     */
    @Test
    void priceCartProductsSameTypeIsCorrectCalculated() {
        // Первое действие подготовка - Arrange
        // Второе выполнение - Act
        cart.addProductToCartByID(1); // 100
        cart.addProductToCartByID(1); // 100
        cart.addProductToCartByID(1); // 100
        // Третье проверка утверждения - Assert
        assertThat(cart.getTotalPrice()).isEqualTo(100 * 3);
        //Стоимость корзины посчиталась корректно
    }

    /**
     * 2.3. Напишите модульный тест для проверки, что при удалении
     * товара из корзины происходит перерасчет общей стоимости корзины.
     * <br><b>Ожидаемый результат:</b>
     * Вызывается метод пересчета стоимости корзины, стоимость корзины меняется
     */
    @Test
    void whenChangingCartCostRecalculationIsCalled() {
        // Arrange
        // Act
        cart.addProductToCartByID(1);  // 170
        cart.addProductToCartByID(2);  // 250
        cart.addProductToCartByID(3);  // 200
        cart.removeProductByID(1); // -170
        // Assert
        assertThat(cart.getTotalPrice()).isEqualTo(170 + 250 + 200 - 170);
    }

    /**
     * 2.4. Разработайте модульный тест для проверки, что при добавлении определенного количества товара в корзину,
     * общее количество этого товара в магазине соответствующим образом уменьшается.
     * <br><b>Ожидаемый результат:</b>
     * Количество товара в магазине уменьшается на число продуктов в корзине пользователя
     */

    @Test
    void quantityProductsStoreChanging() {
        // Arrange
        // Act
        int startShopQty = shop.getProductsShop().get(0).getQuantity();
        int movedToCartQty = 4;
        for (int i = 0; i < movedToCartQty; i++) {
            cart.addProductToCartByID(1);
        }
        // Assert
        assertThat(shop.getProductsShop().get(0).getQuantity()).isEqualTo(startShopQty - movedToCartQty);
    }

    /**
     * 2.5. Создайте модульный тест для проверки, что если пользователь забирает все имеющиеся продукты
     * определенного типа из магазина, эти продукты больше не доступны для заказа.
     * <br><b>Ожидаемый результат:</b>
     * Больше такой продукт заказать нельзя, он не появляется на полке
     */

    @Test
    void lastProductsDisappearFromStore() {
        // Arrange
        // Act
        int movedToCartQty = 11;
        for (int i = 0; i < movedToCartQty; i++) {
            cart.addProductToCartByID(1);
        }
        // Assert
        assertTrue(shop.getProductsShop().get(0).getQuantity() >= 0);
        assertEquals(10, cart.cartItems.get(0).getQuantity());
    }

    /**
     * 2.6. Напишите модульный тест для проверки, что при удалении товара из корзины,
     * общее количество этого товара в магазине соответствующим образом увеличивается.
     * <br><b>Ожидаемый результат:</b>
     * Количество продуктов этого типа на складе увеличивается на число удаленных из корзины продуктов
     */

    @Test
    void deletedProductIsReturnedToShop() {
        // Arrange
        // Act
        int startShopQty = shop.getProductsShop().get(0).getQuantity();
        int movedToCartQty = 7;
        int movedFromCartQty = 2;
        for (int i = 0; i < movedToCartQty; i++) {
            cart.addProductToCartByID(1);
        }
        for (int i = 0; i < movedFromCartQty; i++) {
            cart.removeProductByID(1);
        }
        // Assert
        assertEquals(startShopQty - movedToCartQty + movedFromCartQty, shop.getProductsShop().get(0).getQuantity());
    }

    /**
     * 2.7. Разработайте параметризованный модульный тест для проверки,
     * что при вводе неверного идентификатора товара генерируется исключение RuntimeException.
     * <br><b>Ожидаемый результат:</b>
     * Исключение типа RuntimeException и сообщение Не найден продукт с id
     * *Сделать тест параметризованным
     */
    //
    @ParameterizedTest
    @ValueSource(ints = {0, 13})
    void incorrectProductSelectionCausesException(int param) {
        // Arrange
        // Act
//        cart.addProductToCartByID(param);
        // Assert
        assertThatThrownBy(() -> cart.addProductToCartByID(param)).isInstanceOf(RuntimeException.class)
                .hasMessage("Не найден продукт с id: " + param);
    }

    /**
     * 2.8.      * 2.8. Создайте модульный тест для проверки, что при попытке удалить из корзины больше товаров,
     * чем там есть, генерируется исключение RuntimeException .удаляет продукты до того, как их добавить)
     * <br><b>Ожидаемый результат:</b> Исключение типа NoSuchFieldError и сообщение "В корзине не найден продукт с id"
     */
    @Test
    void incorrectProductRemoveCausesException() {
        // Arrange
        RuntimeException ex = assertThrows(RuntimeException.class, () -> cart.removeProductByID(1));
        // Act
        // Assert
        assertThat(ex.getMessage()).isEqualTo("В корзине не найден продукт с id: " + 1);
        assertThatThrownBy(() -> cart.removeProductByID(1)).isInstanceOf(RuntimeException.class)
                .hasMessage("В корзине не найден продукт с id: " + 1);
    }

    /**
     * 2.9. Нужно восстановить тест
     */
    // boolean Сломанный-Тест() {
    //          // Assert (Проверка утверждения)
    //          assertThat(cart.getTotalPrice()).isEqualTo(cart.getTotalPrice());
    //          // Act (Выполнение)
    //          cart.addProductToCartByID(2); // 250
    //          cart.addProductToCartByID(2); // 250
    //          // Arrange (Подготовка)
    //          Shop shop = new Shop(getStoreItems());
    //          Cart cart = new Cart(shop);
    //      }
    @Test
    void testSUM() {
        // Arrange
        // @BeforeEach
        // Act
        cart.addProductToCartByID(2); // 250
        cart.addProductToCartByID(2); // 250
        // Assert
        assertThat(cart.getTotalPrice()).isEqualTo(250 + 250);
    }

    /*
     * 2.10. Нужно оптимизировать тестовый метод, согласно следующим условиям:
     * <br> 1. Отображаемое имя - "Advanced test for calculating TotalPrice"
     * <br> 2. Тест повторяется 10 раз
     * <br> 3. Установлен таймаут на выполнение теста 70 Миллисекунд (unit = TimeUnit.MILLISECONDS)
     * <br> 4. После проверки работоспособности теста, его нужно выключить
     */

    // ...
    @Disabled
    @DisplayName("Advanced test for calculating TotalPrice")
    @RepeatedTest(10)
    @Timeout(value = 70, unit = TimeUnit.MILLISECONDS)
    void advancedTestForCalculatingTotalPrice() {
        // Arrange
        // @BeforeEach
        // Act
        for (int i = 0; i < 3; i++) {
            cart.addProductToCartByID(1);  // 170
        }
        for (int i = 0; i < 5; i++) {
            cart.addProductToCartByID(2);  // 250
        }
        for (int i = 0; i < 7; i++) {
            cart.addProductToCartByID(3);  // 200
        }
        // Assert
        assertThat(cart.getTotalPrice()).isEqualTo(170 * 3 + 250 * 5 + 200 * 7);
    }

}
