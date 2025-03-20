import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddItemPage extends JFrame implements ActionListener {

    private JPanel mainPanel;
    private JLabel itemNameLabel, itemPriceLabel, itemCategoryLabel, itemStoreLabel;
    private JTextField itemNameField, itemPriceField;
    private JComboBox<String> categoryDropdown, storeDropdown;
    private JButton addItemButton;
    private JButton cancelButton;
    private JButton backButton;
    int id=1;
    int Day =0;

    public AddItemPage() {
        // Frame Settings
        setTitle("Add New Item");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Main Panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Labels
        itemNameLabel = new JLabel("Item Name:");
        itemPriceLabel = new JLabel("Price:");
        itemCategoryLabel = new JLabel("Category:");
        itemStoreLabel = new JLabel("Store:");

        // Fields
        itemNameField = new JTextField(15);
        itemPriceField = new JTextField(15);

        // Dropdowns
        String[] categories = {"Vegetables", "Fruits", "Dairy", "Bakery", "Beverages"};
        categoryDropdown = new JComboBox<>(categories);

        String[] stores = {"Aldi", "Rewe", "Penny","Lidl"};
        storeDropdown = new JComboBox<>(stores);

        // Add Button
        addItemButton = new JButton("✅ Add Item");
        addItemButton.setBackground(new Color(50, 205, 50)); // Green
        addItemButton.setForeground(Color.WHITE);
        addItemButton.addActionListener(this);

        //cancel Button
        cancelButton = new JButton("Cancel");
        cancelButton.setBackground(new Color(250, 50, 50));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.addActionListener(this);

        //back button
        backButton = new JButton("Back");
        backButton.setBackground(new Color(50, 50, 150));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(this);



        // Layout Grid
        gbc.gridx = 0; gbc.gridy = 0; mainPanel.add(itemNameLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0; mainPanel.add(itemNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; mainPanel.add(itemPriceLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; mainPanel.add(itemPriceField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; mainPanel.add(itemCategoryLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2; mainPanel.add(categoryDropdown, gbc);

        gbc.gridx = 0; gbc.gridy = 3; mainPanel.add(itemStoreLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 3; mainPanel.add(storeDropdown, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        mainPanel.add(addItemButton, gbc);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        mainPanel.add(cancelButton, gbc);

        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        mainPanel.add(backButton, gbc);

        // Add Panel to Frame
        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
        Day++;//problem-- its not channging with dispose() i.e after back button???? find logic to count the number of instances--current fix--day++ in back button
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==addItemButton) {
            String S_day = String.valueOf(Day);
            String S_id = String.valueOf(Day)+String.valueOf(id);//creating unique id in the table which goes-instance no.+index no.in that instance

            String itemName = itemNameField.getText();
            String itemPrice = itemPriceField.getText();
            String itemcategory = categoryDropdown.getSelectedItem().toString();
            String itemstore = storeDropdown.getSelectedItem().toString();
            //connecting the add item button to the database page
            try{
                Connect connect = new Connect();
                String query = "insert into items values('"+S_day+"','"+S_id+"','"+itemName+"','"+itemPrice+"','"+itemcategory+"','"+itemstore+"')";
                connect.stmt.executeUpdate(query);//dml command to run the query in mySQL
                //TO_DO-write logic to check if the item already exists then don't add it to the items table
                id++;

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

        }
        if (e.getSource()==cancelButton) {
            itemNameField.setText("");
            itemPriceField.setText("");
            categoryDropdown.setSelectedIndex(0);
            storeDropdown.setSelectedIndex(0);
        }
        if (e.getSource()==backButton) {
            Day=Day+1;//find better logic to increase day cause this is not working, it disposes before increasing the variable

            dispose();

        }
    }

    public static void main(String[] args) {
        new AddItemPage();
    }
}
