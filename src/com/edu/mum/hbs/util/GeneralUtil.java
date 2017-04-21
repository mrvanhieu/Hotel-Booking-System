package com.edu.mum.hbs.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.apache.http.client.config.RequestConfig;
import org.glassfish.jersey.apache.connector.ApacheClientProperties;
import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;

public class GeneralUtil {

    private static final double NA = -999.99;

    private static final long WORD_MASK = 0x00000000000fffffL;
    private static final int WORD_SIZE = 20;


    public static String getUserInput(String prompt) {
        System.out.println(prompt);
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        scanner.close();
        return line;

    }

    public static boolean validateEmail(String addr) {
        if (addr == null || addr.length() == 0) {
            return false;
        }
        return Pattern.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$", addr);
    }

    public static boolean isNumber(String arg) {
        return arg.matches("^[0-9]+([.][0-9]+)?$");
    }

    public static double roundTwoPlaces(double n) {
        return roundDecimalPlaces(n, 2);
    }

    public static double roundThreePlaces(double n) {
        return roundDecimalPlaces(n, 3);
    }

    private static double roundDecimalPlaces(double value, int decimalPoint) {
        if (Double.isInfinite(value) || Double.isNaN(value)) {
            return 0;
        }
        BigDecimal bigDecimal = new BigDecimal(value);
        bigDecimal = bigDecimal.setScale(decimalPoint, RoundingMode.HALF_EVEN);
        return bigDecimal.doubleValue();
    }
    
    /**
	 * Gets the configured client for Rest API
	 *
	 * @return the configured client
	 */
	public static Client getConfiguredClient() {
		ClientConfig clientConfig = new ClientConfig(); // jersey specific
		clientConfig.connectorProvider(new ApacheConnectorProvider()); // jersey specific
		RequestConfig reqConfig = RequestConfig.custom() // apache HttpClient
															// specific
				.setConnectTimeout(3000).setSocketTimeout(3000).setConnectionRequestTimeout(3000).build();
		clientConfig.property(ApacheClientProperties.REQUEST_CONFIG, reqConfig); // jersey
																					// specific
		return ClientBuilder.newClient(clientConfig);
	}

}

