package vehicle.rental.system;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class VehicleRentalSystem extends JFrame {
    private final ArrayList<Vehicle> vehicles;
    private final ArrayList<Rental> rentalHistory;
    private final ArrayList<Customer> customers;
    private JTable vehicleTable;
    private JTable rentalTable;
    private DefaultTableModel vehicleModel;
    private DefaultTableModel rentalModel;
    private JTabbedPane tabbedPane;
    private JTextField searchField;
    private JComboBox<String> filterCombo;
    private JComboBox<String> locationCombo;
    private final Color primaryColor = new Color(0, 102, 204);
    private final Color accentColor;
    
    public VehicleRentalSystem() {
        this.accentColor = new Color(255, 153, 0);
        vehicles = new ArrayList<>();
        rentalHistory = new ArrayList<>();
        customers = new ArrayList<>();
        
        initializeSampleData();
        setupModernUI();
        
        setTitle("Premium Vehicle Rental System");
        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void initializeSampleData() {
        // Premium fleet
        vehicles.add(new Vehicle("V001", "Toyota Camry", "Sedan", 2023, 59.99, true, "Automatic", 5, "New York", "economy_sedan.jpg", 25000, "Gasoline"));
        vehicles.add(new Vehicle("V002", "Honda CR-V", "SUV", 2023, 79.99, true, "Automatic", 5, "New York", "suv.jpg", 18000, "Gasoline"));
        vehicles.add(new Vehicle("V003", "Ford Mustang", "Sports", 2024, 119.99, true, "Manual", 4, "Los Angeles", "mustang.jpg", 5000, "Gasoline"));
        vehicles.add(new Vehicle("V004", "Chevrolet Tahoe", "SUV", 2023, 99.99, false, "Automatic", 7, "Chicago", "tahoe.jpg", 32000, "Gasoline"));
        vehicles.add(new Vehicle("V005", "Tesla Model 3", "Electric", 2024, 129.99, true, "Automatic", 5, "San Francisco", "tesla.jpg", 8000, "Electric"));
        vehicles.add(new Vehicle("V006", "BMW X5", "Luxury SUV", 2024, 149.99, true, "Automatic", 5, "New York", "bmw_x5.jpg", 12000, "Gasoline"));
        vehicles.add(new Vehicle("V007", "Mercedes-Benz E-Class", "Luxury Sedan", 2024, 159.99, true, "Automatic", 5, "Miami", "mercedes.jpg", 8500, "Gasoline"));
        vehicles.add(new Vehicle("V008", "Jeep Wrangler", "SUV", 2023, 89.99, true, "Automatic", 4, "Denver", "jeep.jpg", 22000, "Gasoline"));
        
        customers.add(new Customer("C001", "John Smith", "john.smith@email.com", "+1-555-0101", "DL12345678"));
        customers.add(new Customer("C002", "Sarah Johnson", "sarah.j@email.com", "+1-555-0102", "DL98765432"));
        customers.add(new Customer("C003", "Michael Brown", "m.brown@email.com", "+1-555-0103", "DL55544433"));
        
        rentalHistory.add(new Rental("R001", "V004", "C001", "John Smith", "2024-10-15", "2024-10-20", 499.95, "New York", "Chicago", "Completed", "Paid"));
        rentalHistory.add(new Rental("R002", "V001", "C002", "Sarah Johnson", "2024-10-20", "2024-10-25", 299.95, "New York", "New York", "Completed", "Paid"));
        rentalHistory.add(new Rental("R003", "V005", "C003", "Michael Brown", "2024-11-01", "2024-11-05", 649.95, "San Francisco", "San Francisco", "Active", "Paid"));
    }

    private void setupModernUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        tabbedPane.addTab("🚗 Browse Vehicles", createModernVehiclePanel());
        tabbedPane.addTab("📋 My Reservations", createRentalHistoryPanel());
        tabbedPane.addTab("🎫 New Booking", createModernRentalPanel());
        tabbedPane.addTab("👥 Customers", createCustomerPanel());
        tabbedPane.addTab("📊 Analytics", createAnalyticsPanel());
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        add(mainPanel);
    }

    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(primaryColor);
        header.setPreferredSize(new Dimension(0, 80));
        header.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel logo = new JLabel("🚘 PREMIUM RENTALS");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        logo.setForeground(Color.WHITE);
        header.add(logo, BorderLayout.WEST);
        
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 5));
        rightPanel.setOpaque(false);
        
        JLabel dateLabel = new JLabel("📅 " + LocalDate.now().toString());
        dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dateLabel.setForeground(Color.WHITE);
        
        JLabel userLabel = new JLabel("👤 Admin");
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        userLabel.setForeground(Color.WHITE);
        
        rightPanel.add(dateLabel);
        rightPanel.add(userLabel);
        header.add(rightPanel, BorderLayout.EAST);
        
        return header;
    }

    private JPanel createModernVehiclePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBackground(new Color(245, 245, 245));
        
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        searchField = new JTextField(20);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JButton searchBtn = createStyledButton("🔍 Search", primaryColor);
        
        filterCombo = new JComboBox<>(new String[]{"All Types", "Sedan", "SUV", "Sports", "Luxury", "Electric"});
        filterCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        locationCombo = new JComboBox<>(new String[]{"All Locations", "New York", "Los Angeles", "Chicago", "San Francisco", "Miami", "Denver"});
        locationCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        JCheckBox availableOnly = new JCheckBox("Available Only", true);
        availableOnly.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        availableOnly.setBackground(Color.WHITE);
        
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);
        searchPanel.add(new JLabel("  Type:"));
        searchPanel.add(filterCombo);
        searchPanel.add(new JLabel("  Location:"));
        searchPanel.add(locationCombo);
        searchPanel.add(availableOnly);
        
        panel.add(searchPanel, BorderLayout.NORTH);
        
        String[] columns = {"ID", "Model", "Type", "Year", "Rate/Day", "Transmission", "Seats", "Location", "Mileage", "Fuel", "Status"};
        vehicleModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        vehicleTable = new JTable(vehicleModel);
        vehicleTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        vehicleTable.setRowHeight(30);
        vehicleTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        vehicleTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        vehicleTable.getTableHeader().setBackground(new Color(240, 240, 240));
        
        refreshVehicleTable("", "All Types", "All Locations", true);
        
        JScrollPane scrollPane = new JScrollPane(vehicleTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        panel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(245, 245, 245));
        
        JButton addBtn = createStyledButton("➕ Add Vehicle", new Color(40, 167, 69));
        JButton viewBtn = createStyledButton("👁 View Details", primaryColor);
        JButton toggleBtn = createStyledButton("🔄 Toggle Status", accentColor);
        JButton refreshBtn = createStyledButton("🔄 Refresh", new Color(108, 117, 125));
        
        searchBtn.addActionListener(e -> refreshVehicleTable(
            searchField.getText(),
            (String) filterCombo.getSelectedItem(),
            (String) locationCombo.getSelectedItem(),
            availableOnly.isSelected()
        ));
        
        filterCombo.addActionListener(e -> refreshVehicleTable(
            searchField.getText(),
            (String) filterCombo.getSelectedItem(),
            (String) locationCombo.getSelectedItem(),
            availableOnly.isSelected()
        ));
        
        locationCombo.addActionListener(e -> refreshVehicleTable(
            searchField.getText(),
            (String) filterCombo.getSelectedItem(),
            (String) locationCombo.getSelectedItem(),
            availableOnly.isSelected()
        ));
        
        availableOnly.addActionListener(e -> refreshVehicleTable(
            searchField.getText(),
            (String) filterCombo.getSelectedItem(),
            (String) locationCombo.getSelectedItem(),
            availableOnly.isSelected()
        ));
        
        addBtn.addActionListener(e -> addVehicle());
        viewBtn.addActionListener(e -> viewVehicleDetails());
        toggleBtn.addActionListener(e -> toggleAvailability());
        refreshBtn.addActionListener(e -> refreshVehicleTable("", "All Types", "All Locations", true));
        
        buttonPanel.add(addBtn);
        buttonPanel.add(viewBtn);
        buttonPanel.add(toggleBtn);
        buttonPanel.add(refreshBtn);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }

    private JPanel createRentalHistoryPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBackground(new Color(245, 245, 245));
        
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        filterPanel.setBackground(Color.WHITE);
        filterPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        JComboBox<String> statusFilter = new JComboBox<>(new String[]{"All Status", "Active", "Completed", "Cancelled"});
        statusFilter.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        filterPanel.add(new JLabel("Filter by Status:"));
        filterPanel.add(statusFilter);
        
        panel.add(filterPanel, BorderLayout.NORTH);
        
        String[] columns = {"Rental ID", "Vehicle", "Customer", "Start Date", "End Date", "Pickup", "Dropoff", "Total Cost", "Status", "Payment"};
        rentalModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        rentalTable = new JTable(rentalModel);
        rentalTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        rentalTable.setRowHeight(30);
        rentalTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        rentalTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        rentalTable.getTableHeader().setBackground(new Color(240, 240, 240));
        
        refreshRentalTable();
        
        JScrollPane scrollPane = new JScrollPane(rentalTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        panel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(245, 245, 245));
        
        JButton viewBtn = createStyledButton("👁 View Details", primaryColor);
        JButton cancelBtn = createStyledButton("❌ Cancel Booking", new Color(220, 53, 69));
        JButton receiptBtn = createStyledButton("🧾 Generate Receipt", new Color(40, 167, 69));
        JButton refreshBtn = createStyledButton("🔄 Refresh", new Color(108, 117, 125));
        
        statusFilter.addActionListener(e -> {
            String status = (String) statusFilter.getSelectedItem();
            refreshRentalTable();
        });
        
        viewBtn.addActionListener(e -> viewRentalDetails());
        cancelBtn.addActionListener(e -> cancelRental());
        receiptBtn.addActionListener(e -> generateReceipt());
        refreshBtn.addActionListener(e -> refreshRentalTable());
        
        buttonPanel.add(viewBtn);
        buttonPanel.add(cancelBtn);
        buttonPanel.add(receiptBtn);
        buttonPanel.add(refreshBtn);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }

    private JPanel createModernRentalPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(245, 245, 245));
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel title = new JLabel("New Reservation");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(primaryColor);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        formPanel.add(title, gbc);
        
        gbc.gridwidth = 1;
        
        // Customer Selection
        addFormLabel(formPanel, "Customer:", 1, gbc);
        JComboBox<String> customerCombo = new JComboBox<>();
        updateCustomerCombo(customerCombo);
        styleComboBox(customerCombo);
        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(customerCombo, gbc);
        
        // Vehicle Selection
        addFormLabel(formPanel, "Vehicle:", 2, gbc);
        JComboBox<String> vehicleCombo = new JComboBox<>();
        updateVehicleCombo(vehicleCombo);
        styleComboBox(vehicleCombo);
        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(vehicleCombo, gbc);
        
        // Pickup Location
        addFormLabel(formPanel, "Pickup Location:", 3, gbc);
        JComboBox<String> pickupCombo = new JComboBox<>(new String[]{"New York", "Los Angeles", "Chicago", "San Francisco", "Miami", "Denver"});
        styleComboBox(pickupCombo);
        gbc.gridx = 1;
        gbc.gridy = 3;
        formPanel.add(pickupCombo, gbc);
        
        // Dropoff Location
        addFormLabel(formPanel, "Dropoff Location:", 4, gbc);
        JComboBox<String> dropoffCombo = new JComboBox<>(new String[]{"New York", "Los Angeles", "Chicago", "San Francisco", "Miami", "Denver"});
        styleComboBox(dropoffCombo);
        gbc.gridx = 1;
        gbc.gridy = 4;
        formPanel.add(dropoffCombo, gbc);
        
        // Start Date
        addFormLabel(formPanel, "Pickup Date:", 5, gbc);
        JTextField startField = new JTextField(20);
        startField.setText(LocalDate.now().toString());
        styleTextField(startField);
        gbc.gridx = 1;
        gbc.gridy = 5;
        formPanel.add(startField, gbc);
        
        // End Date
        addFormLabel(formPanel, "Return Date:", 6, gbc);
        JTextField endField = new JTextField(20);
        endField.setText(LocalDate.now().plusDays(3).toString());
        styleTextField(endField);
        gbc.gridx = 1;
        gbc.gridy = 6;
        formPanel.add(endField, gbc);
        
        // Insurance
        addFormLabel(formPanel, "Insurance:", 7, gbc);
        JCheckBox insuranceBox = new JCheckBox("Add Full Coverage ($15/day)");
        insuranceBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        insuranceBox.setBackground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 7;
        formPanel.add(insuranceBox, gbc);
        
        // GPS
        JCheckBox gpsBox = new JCheckBox("Add GPS Navigation ($10/day)");
        gpsBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gpsBox.setBackground(Color.WHITE);
        gbc.gridy = 8;
        formPanel.add(gpsBox, gbc);
        
        // Cost Preview
        JPanel costPanel = new JPanel(new BorderLayout());
        costPanel.setBackground(new Color(240, 248, 255));
        costPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(primaryColor, 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel costLabel = new JLabel("Estimated Total: $0.00");
        costLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        costLabel.setForeground(primaryColor);
        costPanel.add(costLabel, BorderLayout.CENTER);
        
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 8, 8, 8);
        formPanel.add(costPanel, gbc);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton calculateBtn = createStyledButton("💰 Calculate Cost", accentColor);
        JButton rentBtn = createStyledButton("✅ Confirm Booking", new Color(40, 167, 69));
        JButton clearBtn = createStyledButton("🔄 Clear Form", new Color(108, 117, 125));
        
        calculateBtn.addActionListener(e -> {
            try {
                String vehicleInfo = (String) vehicleCombo.getSelectedItem();
                if (vehicleInfo == null) return;
                
                String vehicleId = vehicleInfo.split(" - ")[0];
                Vehicle vehicle = getVehicleById(vehicleId);
                
                LocalDate start = LocalDate.parse(startField.getText());
                LocalDate end = LocalDate.parse(endField.getText());
                long days = ChronoUnit.DAYS.between(start, end);
                
                if (days <= 0) {
                    JOptionPane.showMessageDialog(this, "Return date must be after pickup date", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                double baseCost = days * vehicle.getDailyRate();
                double insuranceCost = insuranceBox.isSelected() ? days * 15 : 0;
                double gpsCost = gpsBox.isSelected() ? days * 10 : 0;
                double totalCost = baseCost + insuranceCost + gpsCost;
                
                costLabel.setText(String.format("Estimated Total: $%.2f (%d days)", totalCost, days));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Please check all fields", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        rentBtn.addActionListener(e -> {
            String customerInfo = (String) customerCombo.getSelectedItem();
            String vehicleInfo = (String) vehicleCombo.getSelectedItem();
            
            if (customerInfo == null || vehicleInfo == null) {
                JOptionPane.showMessageDialog(this, "Please select customer and vehicle", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String customerId = customerInfo.split(" - ")[0];
            String vehicleId = vehicleInfo.split(" - ")[0];
            
            rentVehicle(vehicleId, customerId, startField.getText(), endField.getText(), 
                       (String)pickupCombo.getSelectedItem(), (String)dropoffCombo.getSelectedItem(),
                       insuranceBox.isSelected(), gpsBox.isSelected());
            
            startField.setText(LocalDate.now().toString());
            endField.setText(LocalDate.now().plusDays(3).toString());
            insuranceBox.setSelected(false);
            gpsBox.setSelected(false);
            costLabel.setText("Estimated Total: $0.00");
            updateVehicleCombo(vehicleCombo);
        });
        
        clearBtn.addActionListener(e -> {
            startField.setText(LocalDate.now().toString());
            endField.setText(LocalDate.now().plusDays(3).toString());
            insuranceBox.setSelected(false);
            gpsBox.setSelected(false);
            costLabel.setText("Estimated Total: $0.00");
        });
        
        buttonPanel.add(calculateBtn);
        buttonPanel.add(rentBtn);
        buttonPanel.add(clearBtn);
        
        gbc.gridy = 10;
        gbc.insets = new Insets(15, 8, 8, 8);
        formPanel.add(buttonPanel, gbc);
        
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        return mainPanel;
    }

    private JPanel createCustomerPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBackground(new Color(245, 245, 245));
        
        String[] columns = {"Customer ID", "Name", "Email", "Phone", "Driver's License", "Rentals"};
        DefaultTableModel customerModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable customerTable = new JTable(customerModel);
        customerTable.setRowHeight(30);
        customerTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        customerTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        
        for (Customer c : customers) {
            int rentalCount = (int) rentalHistory.stream().filter(r -> r.getCustomerId().equals(c.getId())).count();
            customerModel.addRow(new Object[]{c.getId(), c.getName(), c.getEmail(), c.getPhone(), c.getLicenseNumber(), rentalCount});
        }
        
        JScrollPane scrollPane = new JScrollPane(customerTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        panel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(245, 245, 245));
        
        JButton addBtn = createStyledButton("➕ Add Customer", new Color(40, 167, 69));
        JButton viewBtn = createStyledButton("👁 View History", primaryColor);
        
        addBtn.addActionListener(e -> addCustomer(customerModel));
        
        buttonPanel.add(addBtn);
        buttonPanel.add(viewBtn);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }

    private JPanel createAnalyticsPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBackground(new Color(245, 245, 245));
        
        // Statistics cards
        int totalVehicles = vehicles.size();
        int availableVehicles = (int) vehicles.stream().filter(Vehicle::isAvailable).count();
        int activeRentals = (int) rentalHistory.stream().filter(r -> r.getStatus().equals("Active")).count();
        double totalRevenue = rentalHistory.stream().mapToDouble(Rental::getTotalCost).sum();
        
        panel.add(createStatCard("Total Vehicles", String.valueOf(totalVehicles), "🚗", new Color(52, 152, 219)));
        panel.add(createStatCard("Available Now", String.valueOf(availableVehicles), "✅", new Color(46, 204, 113)));
        panel.add(createStatCard("Active Rentals", String.valueOf(activeRentals), "📋", new Color(241, 196, 15)));
        panel.add(createStatCard("Total Revenue", String.format("$%.2f", totalRevenue), "💰", new Color(155, 89, 182)));
        
        return panel;
    }

    private JPanel createStatCard(String title, String value, String icon, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel iconLabel = new JLabel(icon, SwingConstants.CENTER);
        iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 48));
        iconLabel.setForeground(color);
        
        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setBackground(Color.WHITE);
        
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        titleLabel.setForeground(new Color(108, 117, 125));
        
        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        valueLabel.setForeground(color);
        
        textPanel.add(titleLabel);
        textPanel.add(valueLabel);
        
        card.add(iconLabel, BorderLayout.NORTH);
        card.add(textPanel, BorderLayout.CENTER);
        
        return card;
    }

    private void addFormLabel(JPanel panel, String text, int row, GridBagConstraints gbc) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(label, gbc);
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(160, 35));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }

    private void styleTextField(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
    }

    private void styleComboBox(JComboBox<String> combo) {
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        combo.setPreferredSize(new Dimension(300, 35));
    }

    private void refreshVehicleTable(String searchText, String typeFilter, String locationFilter, boolean availableOnly) {
        vehicleModel.setRowCount(0);
        for (Vehicle v : vehicles) {
            boolean matchesSearch = searchText.isEmpty() || 
                v.getModel().toLowerCase().contains(searchText.toLowerCase()) ||
                v.getId().toLowerCase().contains(searchText.toLowerCase());
            
            boolean matchesType = typeFilter.equals("All Types") || v.getType().contains(typeFilter);
            boolean matchesLocation = locationFilter.equals("All Locations") || v.getLocation().equals(locationFilter);
            boolean matchesAvailability = !availableOnly || v.isAvailable();
            
            if (matchesSearch && matchesType && matchesLocation && matchesAvailability) {
                vehicleModel.addRow(new Object[]{
                    v.getId(),
                    v.getModel(),
                    v.getType(),
                    v.getYear(),
                    "$" + String.format("%.2f", v.getDailyRate()),
                    v.getTransmission(),
                    v.getSeats(),
                    v.getLocation(),
                    v.getMileage() + " mi",
                    v.getFuelType(),
                    v.isAvailable() ? "✅ Available" : "❌ Rented"
                });
            }
        }
    }

    private void refreshRentalTable() {
        rentalModel.setRowCount(0);
        for (Rental r : rentalHistory) {
            rentalModel.addRow(new Object[]{
                r.getRentalId(),
                r.getVehicleId(),
                r.getCustomerName(),
                r.getStartDate(),
                r.getEndDate(),
                r.getPickupLocation(),
                r.getDropoffLocation(),
                "$" + String.format("%.2f", r.getTotalCost()),
                r.getStatus(),
                r.getPaymentStatus()
            });
        }
    }

    private void updateVehicleCombo(JComboBox<String> combo) {
        combo.removeAllItems();
        for (Vehicle v : vehicles) {
            if (v.isAvailable()) {
                combo.addItem(v.getId() + " - " + v.getModel() + " (" + v.getType() + ") - $" + v.getDailyRate() + "/day");
            }
        }
    }

    private void updateCustomerCombo(JComboBox<String> combo) {
        combo.removeAllItems();
        for (Customer c : customers) {
            combo.addItem(c.getId() + " - " + c.getName() + " (" + c.getEmail() + ")");
        }
    }

    private Vehicle getVehicleById(String id) {
        for (Vehicle v : vehicles) {
            if (v.getId().equals(id)) return v;
        }
        return null;
    }

    private Customer getCustomerById(String id) {
        for (Customer c : customers) {
            if (c.getId().equals(id)) return c;
        }
        return null;
    }

    private void addVehicle() {
        JTextField idField = new JTextField();
        JTextField modelField = new JTextField();
        JTextField typeField = new JTextField();
        JTextField yearField = new JTextField();
        JTextField rateField = new JTextField();
        JComboBox<String> transmissionCombo = new JComboBox<>(new String[]{"Automatic", "Manual"});
        JTextField seatsField = new JTextField();
        var locationCombo = new JComboBox<String>(new String[]{"Johannesburg", "Limpopo", "Pretoria", "", "North West", "Mpumalanga"});
        JTextField mileageField = new JTextField();
        JComboBox<String> fuelCombo = new JComboBox<>(new String[]{"Petrol", "Diesel", "Electric", "Hybrid"});
        
        Object[] fields = {
            "Vehicle ID:", idField,
            "Model:", modelField,
            "Type:", typeField,
            "Year:", yearField,
            "Daily Rate:", rateField,
            "Transmission:", transmissionCombo,
            "Seats:", seatsField,
            "Location:", locationCombo,
            "Mileage:", mileageField,
            "Fuel Type:", fuelCombo
        };
        
        int result = JOptionPane.showConfirmDialog(this, fields, "Add New Vehicle", JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                String id = idField.getText();
                String model = modelField.getText();
                String type = typeField.getText();
                int year = Integer.parseInt(yearField.getText());
                double rate = Double.parseDouble(rateField.getText());
                String transmission = (String) transmissionCombo.getSelectedItem();
                int seats = Integer.parseInt(seatsField.getText());
                String location = (String) locationCombo.getSelectedItem();
                int mileage = Integer.parseInt(mileageField.getText());
                String fuel = (String) fuelCombo.getSelectedItem();
                
                vehicles.add(new Vehicle(id, model, type, year, rate, true, transmission, seats, location, "default.jpg", mileage, fuel));
                refreshVehicleTable("", "All Types", "All Locations", true);
                JOptionPane.showMessageDialog(this, "Vehicle added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input format", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void addCustomer(DefaultTableModel model) {
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField licenseField = new JTextField();
        
        Object[] fields = {
            "Full Name:", nameField,
            "Email:", emailField,
            "Phone:", phoneField,
            "Driver's License:", licenseField
        };
        
        int result = JOptionPane.showConfirmDialog(this, fields, "Add New Customer", JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) {
            String id = "C" + String.format("%03d", customers.size() + 1);
            Customer customer = new Customer(id, nameField.getText(), emailField.getText(), phoneField.getText(), licenseField.getText());
            customers.add(customer);
            model.addRow(new Object[]{id, customer.getName(), customer.getEmail(), customer.getPhone(), customer.getLicenseNumber(), 0});
            JOptionPane.showMessageDialog(this, "Customer added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void toggleAvailability() {
        int selectedRow = vehicleTable.getSelectedRow();
        if (selectedRow >= 0) {
            String vehicleId = (String) vehicleModel.getValueAt(selectedRow, 0);
            Vehicle vehicle = getVehicleById(vehicleId);
            if (vehicle != null) {
                vehicle.setAvailable(!vehicle.isAvailable());
                refreshVehicleTable("", "All Types", "All Locations", true);
                JOptionPane.showMessageDialog(this, "Vehicle status updated!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a vehicle", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewVehicleDetails() {
        int selectedRow = vehicleTable.getSelectedRow();
        if (selectedRow >= 0) {
            String vehicleId = (String) vehicleModel.getValueAt(selectedRow, 0);
            Vehicle vehicle = getVehicleById(vehicleId);
            
            if (vehicle != null) {
                var details = String.format("""
                                            Vehicle Details
                                            
                                            ID: %s
                                            Model: %s
                                            Type: %s
                                            Year: %d
                                            Daily Rate: $%.2f
                                            Transmission: %s
                                            Seats: %d
                                            Location: %s
                                            Mileage: %d mi
                                            Fuel Type: %s
                                            Status: %s""",
                    vehicle.getId(),
                    vehicle.getModel(),
                    vehicle.getType(),
                    vehicle.getYear(),
                    vehicle.getDailyRate(),
                    vehicle.getTransmission(),
                    vehicle.getSeats(),
                    vehicle.getLocation(),
                    vehicle.getMileage(),
                    vehicle.getFuelType(),
                    vehicle.isAvailable() ? "Available" : "Rented"
                );
                
                JOptionPane.showMessageDialog(this, details, "Vehicle Details", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a vehicle", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewRentalDetails() {
        int selectedRow = rentalTable.getSelectedRow();
        if (selectedRow >= 0) {
            Rental rental = rentalHistory.get(selectedRow);
            
            String details;
            details = String.format(
                    "Rental Details\n\n" +
                            "Rental ID: %s\n" +
                            "Vehicle: %s\n" +
                            "Customer: %s\n" +
                            "Pickup Date: %s\n" +
                            "Return Date: %s\n" +
                            "Pickup Location: %s\n" +
                            "Dropoff Location: %s\n" +
                            "Total Cost: $%.2f\n" +
                            "Status: %s\n" +
                            "Payment: %s",
                    rental.getRentalId(),
                    rental.getVehicleId(),
                    rental.getCustomerName(),
                    rental.getStartDate(),
                    rental.getEndDate(),
                    rental.getPickupLocation(),
                    rental.getDropoffLocation(),
                    rental.getTotalCost(),
                    rental.getStatus(),
                    rental.getPaymentStatus()
            );
            
            JOptionPane.showMessageDialog(this, details, "Rental Details", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a rental", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancelRental() {
        int selectedRow = rentalTable.getSelectedRow();
        if (selectedRow >= 0) {
            Rental rental = rentalHistory.get(selectedRow);
            
            if (rental.getStatus().equals("Cancelled")) {
                JOptionPane.showMessageDialog(this, "This rental is already cancelled", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to cancel this rental?", 
                "Confirm Cancellation", 
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                rental.setStatus("Cancelled");
                Vehicle vehicle = getVehicleById(rental.getVehicleId());
                if (vehicle != null) {
                    vehicle.setAvailable(true);
                }
                refreshRentalTable();
                refreshVehicleTable("", "All Types", "All Locations", true);
                JOptionPane.showMessageDialog(this, "Rental cancelled successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a rental", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void generateReceipt() {
        int selectedRow = rentalTable.getSelectedRow();
        if (selectedRow >= 0) {
            Rental rental = rentalHistory.get(selectedRow);
            
            String receipt;
            receipt = String.format(
                    "═══════════════════════════════════\n" +
                            "        PREMIUM RENTALS\n" +
                            "         RENTAL RECEIPT\n" +
                            "═══════════════════════════════════\n\n" +
                            "Receipt #: %s\n" +
                            "Date: %s\n\n" +
                            "Customer: %s\n" +
                            "Vehicle: %s\n\n" +
                            "Pickup Date: %s\n" +
                            "Return Date: %s\n" +
                            "Pickup Location: %s\n" +
                            "Dropoff Location: %s\n\n" +
                            "───────────────────────────────────\n" +
                            "Total Amount: $%.2f\n" +
                            "Payment Status: %s\n" +
                            "───────────────────────────────────\n\n" +
                            "Thank you for choosing Premium Rentals!\n" +
                            "═══════════════════════════════════",
                    rental.getRentalId(),
                    LocalDate.now().toString(),
                    rental.getCustomerName(),
                    rental.getVehicleId(),
                    rental.getStartDate(),
                    rental.getEndDate(),
                    rental.getPickupLocation(),
                    rental.getDropoffLocation(),
                    rental.getTotalCost(),
                    rental.getPaymentStatus()
            );
            
            JTextArea textArea = new JTextArea(receipt);
            textArea.setEditable(false);
            textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
            
            JOptionPane.showMessageDialog(this, new JScrollPane(textArea), "Receipt", JOptionPane.PLAIN_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a rental", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void rentVehicle(String vehicleId, String customerId, String startDate, String endDate, 
                            String pickup, String dropoff, boolean insurance, boolean gps) {
        Vehicle vehicle = getVehicleById(vehicleId);
        Customer customer = getCustomerById(customerId);
        
        if (vehicle != null && customer != null && vehicle.isAvailable()) {
            try {
                LocalDate start = LocalDate.parse(startDate);
                LocalDate end = LocalDate.parse(endDate);
                long days = ChronoUnit.DAYS.between(start, end);
                
                if (days <= 0) {
                    JOptionPane.showMessageDialog(this, "Return date must be after pickup date", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                double baseCost = days * vehicle.getDailyRate();
                double insuranceCost = insurance ? days * 15 : 0;
                double gpsCost = gps ? days * 10 : 0;
                double totalCost = baseCost + insuranceCost + gpsCost;
                
                String rentalId = "R" + String.format("%03d", rentalHistory.size() + 1);
                rentalHistory.add(new Rental(rentalId, vehicleId, customerId, customer.getName(), 
                                            startDate, endDate, totalCost, pickup, dropoff, "Active", "Paid"));
                
                vehicle.setAvailable(false);
                refreshVehicleTable("", "All Types", "All Locations", true);
                refreshRentalTable();
                
                String message;
                message = String.format("""
                                        Booking Confirmed!
                                         ID: %s
                                        Vehicle: %s
                                        Duration: %d days
                                        Base Cost: $%.2f
                                        Insurance: $%.2f
                                        GPS: $%.2f
                                        \u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500
                                        Total Cost: $%.2f
                                        : %s
                                        Dropoff: %s
                                        you for your business!""",
                        rentalId, vehicle.getModel(), days,
                        baseCost, insuranceCost, gpsCost, totalCost,
                        pickup, dropoff
                );
                
                JOptionPane.showMessageDialog(this, message, "Booking Confirmed", JOptionPane.INFORMATION_MESSAGE);
            } catch (HeadlessException ex) {
                JOptionPane.showMessageDialog(this, "Invalid date format", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vehicle not available or customer not found", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VehicleRentalSystem().setVisible(true);
        });
    }
}

class Vehicle {
    private String id, model, type, transmission, location, image, fuelType;
    private int year, seats, mileage;
    private final double dailyRate;
    private boolean available;

    public Vehicle(String id, String model, String type, int year, double dailyRate, 
                   boolean available, String transmission, int seats, String location, 
                   String image, int mileage, String fuelType) {
        this.id = id;
        this.model = model;
        this.type = type;
        this.year = year;
        this.dailyRate = dailyRate;
        this.available = available;
        this.transmission = transmission;
        this.seats = seats;
        this.location = location;
        this.image = image;
        this.mileage = mileage;
        this.fuelType = fuelType;
    }

    public String getId() { return id; }
    public String getModel() { return model; }
    public String getType() { return type; }
    public int getYear() { return year; }
    public double getDailyRate() { return dailyRate; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
    public String getTransmission() { return transmission; }
    public int getSeats() { return seats; }
    public String getLocation() { return location; }
    public String getImage() { return image; }
    public int getMileage() { return mileage; }
    public String getFuelType() { return fuelType; }
}

class Rental {

    private final String rentalId;
    private final String vehicleId;
    private final String customerId;
    private final String customerName;
    private final String startDate;
    private final String endDate;
    private final String pickupLocation;
    private final String dropoffLocation;
    private final String status;
    private final String paymentStatus;


    private final double totalCost;

    public Rental(String rentalId, String vehicleId, String customerId, String customerName,
                  String startDate, String endDate, double totalCost, String pickupLocation,
                  String dropoffLocation, String status, String paymentStatus) {
        this.rentalId = rentalId;
        this.vehicleId = vehicleId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalCost = totalCost;
        this.pickupLocation = pickupLocation;
        this.dropoffLocation = dropoffLocation;
        this.status = status;
        this.paymentStatus = paymentStatus;
    }

    public String getRentalId() { return rentalId; }
    public String getVehicleId() { return vehicleId; }
    public String getCustomerId() { return customerId; }
    public String getCustomerName() { return customerName; }
    public String getStartDate() { return startDate; }
    public String getEndDate() { return endDate; }
    public double getTotalCost() { return totalCost; }
    public String getPickupLocation() { return pickupLocation; }
    public String getDropoffLocation() { return dropoffLocation; }
    public String getStatus() { return status; }
    public String getPaymentStatus() { return paymentStatus; }

    void setStatus(String cancelled) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

class Customer {

    private final String id;
    private final String name;
    private final String email;
    private final String phone;
    private final String licenseNumber;

    public Customer(String id, String name, String email, String phone, String licenseNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.licenseNumber = licenseNumber;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getLicenseNumber() { return licenseNumber; }
}
