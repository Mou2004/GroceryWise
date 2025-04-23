import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class HomePage extends JFrame implements ActionListener {
    private JPanel mainPanel;
    private JPanel sidebar;
    private JButton addItemButton;
    private JButton removeItemButton;
    private JButton findCheapestButton;
    private JButton refreshTable;
    private JTable itemTable;
    private JScrollPane tableScrollPane;

    public HomePage() {
        // Frame Settings
        setTitle("Grocery Price Tracker");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Sidebar (Left Navigation)
        sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(3, 1, 10, 10));
        sidebar.setPreferredSize(new Dimension(150, 600));
        sidebar.setBackground(new Color(230, 230, 230)); // Light Gray

        // Buttons with Icons
        addItemButton = new JButton("➕ Add Item");
        removeItemButton = new JButton("❌ Remove Item");
        findCheapestButton = new JButton("🔍 Find Cheapest");
        refreshTable = new JButton("Refresh Table");

        // Button Styling
        addItemButton.setBackground(new Color(0, 123, 255)); // Blue
        addItemButton.setForeground(Color.WHITE);
        removeItemButton.setBackground(new Color(255, 69, 58)); // Red
        removeItemButton.setForeground(Color.WHITE);
        findCheapestButton.setBackground(new Color(50, 205, 50)); // Green
        findCheapestButton.setForeground(Color.WHITE);
        refreshTable.setBackground(new Color(50, 150, 150)); // Green
        refreshTable.setForeground(Color.WHITE);

        // Add Buttons to Sidebar
        sidebar.add(addItemButton);
        sidebar.add(removeItemButton);
        sidebar.add(findCheapestButton);

        //add action listeners to buttons
        addItemButton.addActionListener(this);
        removeItemButton.addActionListener(this);
        findCheapestButton.addActionListener(this);
        refreshTable.addActionListener(this);

        // Table (Main Content Area)
        String[] columnNames = {"Item Name", "Price", "Category", "Store"};
        DefaultTableModel model = new DefaultTableModel(null, columnNames);
        displayTable(model);
        itemTable = new JTable(model);
        tableScrollPane = new JScrollPane(itemTable);

        // Table Styling
        itemTable.setBackground(Color.WHITE);
        itemTable.setGridColor(Color.LIGHT_GRAY);
        itemTable.setRowHeight(25);

        // Main Panel
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);
        mainPanel.add(refreshTable,BorderLayout.SOUTH);

        // Add components to Frame
        add(sidebar, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);

    }

    public static void main(String[] args) {
        new HomePage();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==addItemButton) {
            new AddItemPage();
        }
        if (e.getSource()==removeItemButton) {
            //implement logic to delete items from the database table items
            new RemoveItemPage();
        }
        if (e.getSource()==findCheapestButton) {
            //find cheapest from the item table-price column
            new FindCheapest();
        }
        if (e.getSource()==refreshTable){
            this.dispose();
            new HomePage();
        }

    }
    public static void displayTable(DefaultTableModel model) {

        String query = "Select * from items";
        ResultSet rs = null;
        try{
            Connect connect = new Connect();
            rs = connect.stmt.executeQuery(query);

            //get the column names
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            String[] columnNames = new String[columnCount];
            for (int i=1; i<=columnCount;i++){
                columnNames[i-1] = metaData.getColumnName(i);
            }
            model.setColumnIdentifiers(columnNames);

            //add rows to the table
            while (rs.next()){
                Object[] row = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++){
                    row[i - 1] = rs.getObject(i);
                }
                model.addRow(row);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            try{
                if (rs!=null){
                    rs.close();
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
