package kata.ex01;

import kata.ex01.model.Driver;
import kata.ex01.model.HighwayDrive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static kata.ex01.model.RouteType.RURAL;
import static kata.ex01.model.VehicleFamily.STANDARD;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author kawasima
 */
public class DiscountServiceTest {
    DiscountService discountService;
    private Driver driver(int usingCount) {
        Driver driver = new Driver();
        driver.setCountPerMonth(usingCount);
        return driver;
    }

    @BeforeEach
    void setUp() {
        discountService = new DiscountServiceImpl();
    }

    @Test
    public void test平日朝夕で乗車回数が10回以上のときに50パーセント割引される() {
        HighwayDrive drive = new HighwayDrive();
        drive.setEnteredAt(LocalDateTime.of(2016, 3, 31, 23, 0));
        drive.setExitedAt(LocalDateTime.of(2016, 4, 1, 6, 30));
        drive.setDriver(driver(10));
        drive.setVehicleFamily(STANDARD);
        drive.setRouteType(RURAL);

        assertThat(discountService.calc(drive)).isEqualTo(50);
    }

    @Test
    public void test平日朝夕で乗車回数が5から9回のときに30パーセント割引される() {
        HighwayDrive drive = new HighwayDrive();
        drive.setEnteredAt(LocalDateTime.of(2016, 3, 31, 23, 0));
        drive.setExitedAt(LocalDateTime.of(2016, 4, 1, 6, 30));
        drive.setDriver(driver(9));
        drive.setVehicleFamily(STANDARD);
        drive.setRouteType(RURAL);

        assertThat(discountService.calc(drive)).isEqualTo(30);
    }

    @Test
    public void test何も割引条件がないとき割引率ゼロ() {
        HighwayDrive drive = new HighwayDrive();
        drive.setEnteredAt(LocalDateTime.of(2019, 9, 25, 16, 0));
        drive.setExitedAt(LocalDateTime.of(2019, 9, 25, 16, 30));
        drive.setDriver(driver(9));
        drive.setVehicleFamily(STANDARD);
        drive.setRouteType(RURAL);

        assertThat(discountService.calc(drive)).isEqualTo(30);
    }

    @Test
    public void test休日朝夕は休日割が適用される() {
        HighwayDrive drive = new HighwayDrive();
        drive.setEnteredAt(LocalDateTime.of(2016, 4, 1, 23, 0));
        drive.setExitedAt(LocalDateTime.of(2016, 4, 2, 6, 30));
        drive.setDriver(driver(10));
        drive.setVehicleFamily(STANDARD);
        drive.setRouteType(RURAL);

        assertThat(discountService.calc(drive)).isEqualTo(30);
    }

}
