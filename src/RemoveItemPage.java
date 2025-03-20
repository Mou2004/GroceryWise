import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RemoveItemPage extends JFrame implements ActionListener {

    private JPanel mainPanel;
    private JLabel itemNameLabel, itemCategoryLabel, itemStoreLabel;
    private JTextField itemNameField;
    private JComboBox<String> categoryDropdown, storeDropdown;
    private JButton removeItemButton;
    private JButton cancelButton;

    public RemoveItemPage() {
        // Frame Settings
        setTitle("Remove Item");
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
        itemStoreLabel = new JLabel("Store:");

        // Fields
        itemNameField = new JTextField(15);


        // Dropdowns
        String[] categories = {"Vegetables", "Fruits", "Dairy", "Bakery", "Beverages"};
        categoryDropdown = new JComboBox<>(categories);

        String[] stores = {"Aldi", "Rewe", "Penny","Lidl"};
        storeDropdown = new JComboBox<>(stores);

        // Remove Button
        removeItemButton = new JButton("✅ Remove Item");
        removeItemButton.setBackground(new Color(250, 50, 50)); // Green
        removeItemButton.setForeground(Color.WHITE);
        removeItemButton.addActionListener(this);

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

        gbc.gridx = 0; gbc.gridy = 3; mainPanel.add(itemStoreLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 3; mainPanel.add(storeDropdown, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        mainPanel.add(removeItemButton, gbc);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        mainPanel.add(cancelButton, gbc);



        // Add Panel to Frame
        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==removeItemButton) {
            //improve the logic such that once the user enters the itemName, it scrapes all the items with the particular name and asks the user which one to delete
            String S_name = itemNameField.getText();
            String S_category = categoryDropdown.getSelectedItem().toString();
            String S_store = storeDropdown.getSelectedItem().toString();
            try {
                Connect conn = new Connect();
                String query ="DELETE FROM items WHERE item_name='"+S_name+"' AND item_category ='"+S_category+"' AND item_supermarket='"+S_store+"'";
                conn.stmt.executeUpdate(query);

            }
            catch (Exception e1) {
                System.out.println(e1);
            }

        }
        if (e.getSource()==cancelButton) {
            dispose();
        }
    }
    public static void main(String[] args) {
        new RemoveItemPage().setVisible(true);
    }
}
