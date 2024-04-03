import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Currency;
import java.util.Locale;
import java.util.ResourceBundle;

public class Offer {

    private Locale locale;
    private String country;
    private Date start;
    private Date end;
    private String placeType;
    private float price;
    private Currency currency;



    public Offer(String localeStr,
                 String country,
                 String start,
                 String end,
                 String placeType,
                 String price,
                 String currency){


        Locale locale = Locale.forLanguageTag(localeStr.replace('_','-'));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        NumberFormat numberFormat = NumberFormat.getInstance(locale);

        try {
            this.locale=locale;
            this.country=CountryTranslation.translate(country, locale, Locale.ENGLISH);
            this.start=dateFormat.parse(start);
            this.end=dateFormat.parse(end);
            this.placeType=localePlaceType(placeType,locale);
            this.price=numberFormat.parse(price).floatValue();
            this.currency=Currency.getInstance(currency);

        }
        catch(ParseException e) {
            e.printStackTrace();
        }

    }


    public Offer(Locale locale,
                 String country,
                 Date start,
                 Date end,
                 String placeType,
                 float price,
                 Currency currency){
        this.locale=locale;
        this.country=country;
        this.start=start;
        this.end=end;
        this.placeType=placeType;
        this.price=price;
        this.currency=currency;

    }



    public String localize(Locale loc) {
        ResourceBundle info = ResourceBundle.getBundle("info",loc);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        NumberFormat numberFormat = NumberFormat.getInstance(loc);

        String country = localeCountry(loc);
        String startDate = dateFormat.format(this.start);
        String endDate = dateFormat.format(this.end);
        String place = info.getString(this.placeType);
        String price = numberFormat.format(this.price);

        return country + " " + startDate + " " + endDate + " " + place + " " + price + " " + this.currency;
    }




    public Locale getLocale() {
        return locale;
    }


    public String getCountry() {
        return country;
    }

    public String localeCountry(Locale locCountry) {
        return CountryTranslation.translate(this.country,Locale.ENGLISH,locCountry);
    }


    public Date getStart() {
        return start;
    }


    public Date getEnd() {
        return end;
    }


    public String getPlaceType() {
        return placeType;
    }

    public String localePlaceType(String placeType,Locale locPlace) {
        ResourceBundle info = ResourceBundle.getBundle("info",locPlace);
        String[] places = {"lake","sea","mountains"};
        for(String i : places) {
            if(placeType.equals(info.getString(i))){
                return i;
            }
        }
        return "";
    }


    public float getPrice() {
        return price;
    }


    public Currency getCurrency() {
        return currency;
    }


}
