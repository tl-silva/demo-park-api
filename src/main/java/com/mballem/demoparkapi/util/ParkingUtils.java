package com.mballem.demoparkapi.util;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParkingUtils {
	
	public static String issueReceipt() {
		LocalDateTime date = LocalDateTime.now();
		String receipt = date.toString().substring(0,19);
		return receipt.replace("-", "")
				.replace(":", "")
				.replace("T", "-");
	}

}
