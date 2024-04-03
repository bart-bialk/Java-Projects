import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class Database {

    private Connection con;
    private TravelData traveldata;


    public Database(String url,TravelData traveldata) {

        this.traveldata=traveldata;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.con= DriverManager.getConnection(url,"root","root");

        }catch(Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

    public void create() {
        String drop = "drop table if exists offer";
        String creat = "create table offer\n"
                +"("
                + "offerId int auto_increment,\n"
                + "locale varchar(16) null,\n"
                + "country varchar(64) null,\n"
                + "startDate date null,\n"
                + "endDate date null,\n"
                + "place varchar(16),\n"
                + "price float null,\n"
                + "currency char(3) null,\n"
                + "constraint offer_pk primary key(offerId)\n"
                + ");\n";
        try {
            Statement stateCreate = this.con.createStatement();
            stateCreate.executeUpdate(drop);
            stateCreate.executeUpdate(creat);
            stateCreate.close();

        }catch(Exception e) {
            e.printStackTrace();
        }


        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
            Statement stateInsert = this.con.createStatement();

            for(Offer offer : this.traveldata.getList()) {

                Locale locale = offer.getLocale();
                String country = offer.getCountry();
                String startDate = dateFormat.format(offer.getStart());
                String endDate = dateFormat.format(offer.getEnd());
                String place = offer.getPlaceType();
                double price = offer.getPrice();
                Currency currency = offer.getCurrency();

                String insert = "insert into offer\n"
                        + "(locale,country,startDate,endDate,place,price,currency)\n"
                        + "values(\n"
                        + "'" + locale + "',\n"
                        + "'" + country + "',\n"
                        + "'" + startDate + "',\n"
                        + "'" + endDate + "',\n"
                        + "'" + place + "',\n"
                        + "'" + price + "',\n"
                        + "'" + currency + "'\n"
                        +");";

                stateInsert.executeUpdate(insert);

            }

            stateInsert.close();
        }catch(Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }

    }


    private Offer[] getData() {
        String select = "select locale,country,startDate,endDate,place,price,currency\n"
                +"from offer;\n";

        try {
            Statement selStatement=this.con.createStatement();
            ResultSet resultSet = selStatement.executeQuery(select);
            List<Offer> offers = new ArrayList<Offer>();

            while(resultSet.next()) {

                offers.add(new Offer(
                        Locale.forLanguageTag(resultSet.getString("locale")),
                        resultSet.getString("country"),
                        resultSet.getDate("startDate"),
                        resultSet.getDate("endDate"),
                        resultSet.getString("place"),
                        resultSet.getFloat("price"),
                        Currency.getInstance(resultSet.getString("currency"))
                ));
            }

            selStatement.close();
            Offer[] data = new Offer[offers.size()];
            offers.toArray(data);

            return data;

        }catch(Exception e) {
            e.printStackTrace();
        }
        Offer[] data= {};
        return data;
    }



    public void showGui() {
        Offer[] data = this.getData();

        new Gui(data);
    }


}
