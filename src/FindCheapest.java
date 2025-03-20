import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;


public class FindCheapest extends JFrame implements ActionListener {
    private JPanel mainPanel;
    private JLabel itemNameLabel, itemCategoryLabel;
    private JTextField itemNameField;
    private JComboBox<String> categoryDropdown;
    private JButton FindCheapestButton;
    private JButton cancelButton;
    private JLabel findCheapestLabel;
    public FindCheapest() {
        
        setTitle("Cheapest Item");
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
        itemCategoryLabel = new JLabel("Category:");
        findCheapestLabel = new JLabel("Cheapest Item is:");
      

        // Fields
        itemNameField = new JTextField(15);


        // Dropdowns
        String[] categories = {"Vegetables", "Fruits", "Dairy", "Bakery", "Beverages"};
        categoryDropdown = new JComboBox<>(categories);

      
      
        // Remove Button
        FindCheapestButton = new JButton("✅ Find Cheapest Item");
        FindCheapestButton.setBackground(new Color(250, 50, 50)); // Green
        FindCheapestButton.setForeground(Color.WHITE);
        FindCheapestButton.addActionListener(this);

        //cancel Button
        cancelButton = new JButton("Cancel");
        cancelButton.setBackground(new Color(250, 205, 50));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.addActionListener(this);




        // Layout Grid
        gbc.gridx = 0; gbc.gridy = 0; mainPanel.add(itemNameLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0; mainPanel.add(itemNameField, gbc);


        gbc.gridx = 0; gbc.gridy = 2; mainPanel.add(itemCategoryLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2; mainPanel.add(categoryDropdown, gbc);

        gbc.gridx = 0; gbc.gridy = 3; mainPanel.add(findCheapestLabel, gbc);


        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        mainPanel.add(FindCheapestButton, gbc);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        mainPanel.add(cancelButton, gbc);



        // Add Panel to Frame
        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
    }
    public static void main(String[] args) {new FindCheapest().setVisible(true);}

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == FindCheapestButton) {
            String itemName = itemNameField.getText();
            String category = categoryDropdown.getSelectedItem().toString();
            try {
                Connect conn = new Connect();
                //important -- a query inside a query i.e a subquery, here it first selects the min price from the items table and then returns the name and supermarket corresponding to it
                ResultSet result = conn.stmt.executeQuery(
                        "SELECT item_name, item_price, item_supermarket FROM items " +
                                "WHERE item_price = (SELECT MIN(item_price) FROM items" +
                                "WHERE item_name ='" + itemName + "' AND item_category ='" + category + "')"
                );
                while (result.next()) {
                    findCheapestLabel.setText("Cheapest " + itemName + " is from " + result.getString("item_supermarket") + " which is "
                            + result.getString("item_price"));

                }


            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            if (e.getSource() == cancelButton) {
                dispose();
            }
        }
    }
}
