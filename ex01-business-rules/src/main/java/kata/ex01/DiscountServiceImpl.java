package kata.ex01;

import kata.ex01.model.HighwayDrive;

import java.util.ArrayList;
import java.util.Collections;

import static kata.ex01.util.HolidayUtils.isHoliday;

/**
 * @author kawasima
 */
public class DiscountServiceImpl implements DiscountService {
    @Override
    public long calc(HighwayDrive drive) {

        int enteredHour = drive.getEnteredAt().getHour();
        int exitedHour = drive.getEnteredAt().getHour();
        int count = drive.getDriver().getCountPerMonth();
        boolean isEnteredDateHoriday = isHoliday(drive.getEnteredAt().toLocalDate());
        boolean isExitedDateHoriday = isHoliday(drive.getExitedAt().toLocalDate());
        boolean canAsawari = (enteredHour >= 6 || exitedHour <= 9);
        boolean canYuwari = (enteredHour >= 17 || exitedHour <= 20);
        boolean canShinyawari = (enteredHour >= 0 || exitedHour <= 4); // ここ何時だっけ？

        ArrayList<Integer> waribikiArray = new ArrayList<Integer>();
        waribikiArray.add(0);

        // 平日朝夕割
        if (!isEnteredDateHoriday && !isExitedDateHoriday && (canAsawari || canYuwari)) {
            if (count >= 10) {
                waribikiArray.add(50);
            } else if (count >= 5) {
                waribikiArray.add(30);
            }
        }

        // 休日割
        if (isEnteredDateHoriday || isExitedDateHoriday) {
            waribikiArray.add(30);
        }

        // 深夜割
        if (canShinyawari) {
            waribikiArray.add(30);
        }

        return Collections.max(waribikiArray);
    }
}
