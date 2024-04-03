import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class Gui extends JFrame {


    private static final long serialVersionUID = 1L;
    private JTable jtable;
    private Offer[] data;
    private Locale locale=Locale.getDefault();
    private JButton plButton;
    private JButton enButton;


    public Gui(Offer[] data) {
        this.data=data;
        this.jtable=new JTable();

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        this.getContentPane().add(panel);

        JScrollPane jsp = new JScrollPane(this.jtable);
        JPanel scrollPanel = new JPanel();
        scrollPanel.add(jsp);
        panel.add(scrollPanel,BorderLayout.CENTER);


        JPanel buttonsPanel = new JPanel();

        plButton = new JButton();
        plButton.setActionCommand("pl_PL");
        plButton.addActionListener(locChanger);

        enButton = new JButton();
        enButton.setActionCommand("en_GB");
        enButton.addActionListener(locChanger);

        buttonsPanel.add(plButton);
        buttonsPanel.add(enButton);
        panel.add(buttonsPanel,BorderLayout.NORTH);


        this.setTitle("Travel Offers");
        this.setVisible(true);
        this.pack();
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        updateTable(locale);
    }



    public void updateTable(Locale locale){
        ResourceBundle rb = ResourceBundle.getBundle("info",locale);
        TravelTableModel tableModel = new TravelTableModel(data,locale);
        this.jtable.setModel(tableModel);
        plButton.setText(rb.getString("polish"));
        enButton.setText(rb.getString("english"));
    }


    ActionListener locChanger = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String symloc = e.getActionCommand();
            String[] locArg = symloc.split("_");
            updateTable(new Locale(locArg[0],locArg[1]));
        }
    };

}
