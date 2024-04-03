import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.table.AbstractTableModel;

public class TravelTableModel extends AbstractTableModel{

    private static final long serialVersionUID = 1L;
    private Offer[] data;
    private String[] columns;
    private Locale locale;
    private ResourceBundle rb;

    public TravelTableModel(Offer[] data,Locale locale) {
        this.data=data;
        this.locale=locale;
        this.rb=ResourceBundle.getBundle("info",locale);

        String colNames[] = {
                "country",
                "startDate",
                "endDate",
                "place",
                "price",
                "currency"
        };
        this.columns= colNames;
    }


    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public int getRowCount() {
        return this.data.length;
    }

    @Override
    public Object getValueAt(int rowInd, int colInd) {
        String colName = this.columns[colInd];
        Offer row = this.data[rowInd];
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT,locale);
        NumberFormat numberFormat = NumberFormat.getInstance(locale);

        switch(colName) {
            case "country" : {
                return CountryTranslation.translate(row.getCountry(), Locale.ENGLISH, locale);
            }
            case "startDate" : {
                return dateFormat.format(row.getStart());
            }
            case "endDate" : {
                return dateFormat.format(row.getEnd());
            }
            case "place" : {
                return rb.getString(row.getPlaceType());
            }
            case "price" : {
                return numberFormat.format(row.getPrice());
            }
            case "currency" : {return row.getCurrency();}
            default : {return "";}
        }

    }


    public String getColumnName(int column) {
        if (this.columns[column] != null) {
            return this.rb.getString(this.columns[column]);
        }


        return "";
    }


}
