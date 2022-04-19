package beer.hoppyhour.api.helper;

public class ConversionHelper {
    
    public static Double fahrenheitToCelsius(Double f) {
        return (f - 32) * (5 / 9);
    }
}
