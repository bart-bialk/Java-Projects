import java.util.Locale;

public class CountryTranslation {

    public static String translate(String country,Locale localeFrom,Locale localTo) {

        for(Locale l : Locale.getAvailableLocales()) {
            if(l.getDisplayCountry(localeFrom).equals(country)) {
                return l.getDisplayCountry(localTo);
            }
        }
        return "";
    }

}
