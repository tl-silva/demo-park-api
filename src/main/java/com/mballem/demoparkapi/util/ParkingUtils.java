package com.mballem.demoparkapi.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParkingUtils {
	
    private static final double FIRST_15_MINUTES = 5.00;
    private static final double FIRST_60_MINUTES = 9.25;
    private static final double ADDITIONAL_15_MINUTES = 1.75;
    private static final double PERCENTAGE_DISCOUNT = 0.30;

    public static BigDecimal calculateCost(LocalDateTime entry, LocalDateTime exit) {
        long minutes = entry.until(exit, ChronoUnit.MINUTES);
        double total = 0.0;

        if (minutes <= 15) {
            total = FIRST_15_MINUTES;
        } else if (minutes <= 60) {
            total = FIRST_60_MINUTES;
        } else {
            long additionalMinutes = minutes - 60;
            Double totalParts = ((double) additionalMinutes / 15);
            if (totalParts > totalParts.intValue()) { // 4.66 > 4
                total += FIRST_60_MINUTES + (ADDITIONAL_15_MINUTES * (totalParts.intValue() + 1));
            } else { // 4.0
                total += FIRST_60_MINUTES + (ADDITIONAL_15_MINUTES * totalParts.intValue());
            }
        }

        return new BigDecimal(total).setScale(2, RoundingMode.HALF_EVEN);
    }

    public static BigDecimal calculateDiscount(BigDecimal cost, long timesNumber) {
        BigDecimal discount = ((timesNumber > 0) && (timesNumber % 10 == 0))
                ? cost.multiply(new BigDecimal(PERCENTAGE_DISCOUNT))
                : new BigDecimal(0);
        return discount.setScale(2, RoundingMode.HALF_EVEN);
    }
	
	public static String issueReceipt() {
		LocalDateTime date = LocalDateTime.now();
		String receipt = date.toString().substring(0,19);
		return receipt.replace("-", "")
				.replace(":", "")
				.replace("T", "-");
	}

}
