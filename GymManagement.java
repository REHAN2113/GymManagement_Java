import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.sql.*;
import java.io.FileInputStream;
import java.io.IOException;

public class GymManagement extends JFrame implements ActionListener {
    JButton logo, eyeOpenBtn, eyeCloseBtn, login, adminlogoicon;
    JLabel username, password, title;
    JTextField userField;
    JPasswordField passField;
    Connection con;
    Statement st;
    ResultSet rs;

    GymManagement() {

        Properties props = new Properties();
        try (FileInputStream input = new FileInputStream("config/config.properties")) {
            props.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String url = props.getProperty("db.url");
        String userName = props.getProperty("db.username");
        String passWord = props.getProperty("db.password");
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(url, userName, passWord);
            st = con.createStatement();
        } catch (Exception e) {
            System.out.println("error ;-" + e);
        }

        setSize(1200, 1000);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        setTitle("GYM MANAGEMENT");
        ImageIcon icon = new ImageIcon("D:\\GYM MANAGEMENT SYSTEM USING JAVA\\img\\logo.png");
        Image resizedImage = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        ImageIcon adminlogo = new ImageIcon("D:\\GYM MANAGEMENT SYSTEM USING JAVA\\img\\authorization.png");
        Image resizedadmin = adminlogo.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        ImageIcon resizedadminlogo = new ImageIcon(resizedadmin);
        ImageIcon eyeOpen = new ImageIcon("D:\\GYM MANAGEMENT SYSTEM USING JAVA\\img\\show.png");
        Image resizedImageOpen = eyeOpen.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH);
        ImageIcon resizedIconOpen = new ImageIcon(resizedImageOpen);
        ImageIcon eyeClose = new ImageIcon("D:\\GYM MANAGEMENT SYSTEM USING JAVA\\img\\hide.png");
        Image resizedImageClose = eyeClose.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH);
        ImageIcon resizedIconClose = new ImageIcon(resizedImageClose);
        Color c = new Color(189, 239, 255);
        Font font = new Font("SansSerif", Font.BOLD, 30);
        Font font1 = new Font("SansSerif", Font.BOLD, 35);
        Color c1 = new Color(60, 100, 159);
        JButton logo = new JButton();
        JPanel panel = new JPanel();

        panel.setBackground(c);
        panel.setLayout(null);
        panel.setBounds(0, 0, 250, 1000);

        add(panel);

        logo.setIcon(resizedIcon);
        logo.setBorder(null);
        logo.setFocusPainted(false);
        logo.setContentAreaFilled(false);
        logo.setBounds(30, 500 - 200, 200, 200);
        panel.add(logo);

        title = new JLabel("ADMIN LOGIN");
        title.setBounds(620, 10, 300, 100);
        title.setFont(font1);
        title.setForeground(new Color(11, 173, 222, 255));
        add(title);

        adminlogoicon = new JButton(resizedadminlogo);
        adminlogoicon.setFocusPainted(false);
        adminlogoicon.setContentAreaFilled(false);
        adminlogoicon.setBorder(null);
        adminlogoicon.setBounds(530, 30, 70, 70);
        add(adminlogoicon);

        username = new JLabel("USERNAME -");
        username.setBounds(400, 200, 200, 200);
        username.setFont(font);
        add(username);

        password = new JLabel("PASSWORD -");
        password.setBounds(400, 295, 200, 200);
        password.setFont(font);
        add(password);

        userField = new JTextField();
        userField.setBounds(650, 280, 350, 45);
        userField.setFont(font);
        add(userField);

        passField = new JPasswordField();
        passField.setBounds(650, 375, 350, 45);
        passField.setEchoChar('\u2022');
        passField.setFont(font);
        add(passField);

        eyeOpenBtn = new JButton();
        eyeOpenBtn.setIcon(resizedIconOpen);
        eyeOpenBtn.setBounds(1020, 375, 45, 45);
        eyeOpenBtn.setFocusPainted(true);
        eyeOpenBtn.setContentAreaFilled(true);
        eyeOpenBtn.setBorder(null);
        eyeOpenBtn.setBackground(Color.WHITE);
        eyeOpenBtn.setVisible(false);
        eyeOpenBtn.addActionListener(this);
        add(eyeOpenBtn);

        eyeCloseBtn = new JButton();
        eyeCloseBtn.setIcon(resizedIconClose);
        eyeCloseBtn.setBounds(1020, 375, 45, 45);
        eyeCloseBtn.setFocusPainted(true);
        eyeCloseBtn.setContentAreaFilled(true);
        eyeCloseBtn.setBorder(null);
        eyeCloseBtn.setBackground(Color.WHITE);
        eyeCloseBtn.setVisible(true);
        eyeCloseBtn.addActionListener(this);
        add(eyeCloseBtn);

        login = new JButton("LOG-IN");
        login.setBounds(600, 500, 160, 50);
        login.setFont(font);
        login.setBackground(c);
        login.setForeground(Color.BLACK);
        login.setBorder(null);
        login.setFocusPainted(false);
        login.addActionListener(this);
        add(login);

        setVisible(true);

    }

    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == eyeOpenBtn) {
            passField.setEchoChar('\u2022');
            eyeOpenBtn.setVisible(false);

            eyeCloseBtn.setVisible(true);

        }
        if (ae.getSource() == eyeCloseBtn) {
            eyeCloseBtn.setVisible(false);
            eyeOpenBtn.setVisible(true);
            passField.setEchoChar((char) 0);
        }
        if (ae.getSource() == login) {

            String name = userField.getText();
            char[] password = passField.getPassword();
            String pass = new String(password);
            try {
                rs = st.executeQuery("select * from admin where name ='" + name + "' and password ='" + pass + "'");
                if (rs.next()) {
                    dispose();
                    new Member();
                } else {
                    JOptionPane.showMessageDialog(this, "WRONG USERNAME OR PASSWORD.");
                }
            } catch (Exception e) {

            }

        }
    }

    public static void main(String[] args) {
        GymManagement ob = new GymManagement();
    }
}

class Member extends JFrame implements ActionListener {
    JLabel logo, member, logoutIcon, name, mobile, selectTrainer, MemberShip, SELECTED_TRAINER, membership,
            gender, paymentLabel, date;
    JButton trainer, payment, logout, add, delete, edit;
    JTextField nameTextField, mobileTextField, paymentTextField;
    JComboBox box, box1, day, month, year;
    JRadioButton male, female;
    ButtonGroup group;
    String gender1;
    JTable table;
    JScrollPane jsp;
    Connection con;
    Statement st;
    ResultSet rs;
    ResultSetMetaData rsm;
    Object[][] data;
    String[] columnNames = { "MEMBER", "MOBILE", "TRAINER", "MEMBERSHIP", "GENDER", "PAY-DATE", "PAYMENT", "ID" };
    String membershipArray[] = { "ONLY GYM", "ONLY CARDIO", "GYM + CARDIO" };
    ArrayList<String> arrayList = new ArrayList<>(100);
    DefaultTableModel model;
    int i = 0;
    Object[] TrainerArray;

    Member() {

        Properties props = new Properties();
        try (FileInputStream input = new FileInputStream("config/config.properties")) {
            props.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String url = props.getProperty("db.url");
        String userName = props.getProperty("db.username");
        String passWord = props.getProperty("db.password");
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(url, userName, passWord);
            st = con.createStatement();
        } catch (Exception e) {
            System.out.println("error ;-" + e);
        }

        setLayout(null);
        setSize(1300, 1000);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        JPanel panel = new JPanel();
        Color c = new Color(189, 239, 255);
        Color c1 = new Color(87, 188, 244);
        ImageIcon icon = new ImageIcon("D:\\GYM MANAGEMENT SYSTEM USING JAVA\\img\\group.png");
        Image ResizedIcon = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon iconResized = new ImageIcon(ResizedIcon);
        ImageIcon icon1 = new ImageIcon("D:\\GYM MANAGEMENT SYSTEM USING JAVA\\img\\log-out.png");
        Image ResizedIcon1 = icon1.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        ImageIcon iconResized1 = new ImageIcon(ResizedIcon1);
        Font font = new Font("arial", Font.BOLD, 25);
        Font font1 = new Font("arial", Font.BOLD, 20);
        Font font2 = new Font("arial", Font.BOLD, 20);
        Font font3 = new Font("arial", Font.BOLD, 18);
        Font f = new Font("arial", Font.BOLD, 16);
        LineBorder border = new LineBorder(Color.BLACK, 4);
        LineBorder border1 = new LineBorder(Color.BLACK, 1);
        LineBorder textFieldborder = new LineBorder(c, 2);
        String trainerArray[] = new String[100];

        try {
            rs = st.executeQuery("select name from trainer");
            rsm = rs.getMetaData();
            int rowCount = 0;
            while (rs.next()) {
                rowCount++;
            }
            arrayList.ensureCapacity(rowCount);
            rs = st.executeQuery("select name from trainer");
            while (rs.next()) {
                arrayList.add(rs.getString(1));
            }
            TrainerArray = arrayList.toArray();

        } catch (Exception e) {

        }

        panel.setLayout(null);
        panel.setBounds(0, 0, 250, 1000);
        panel.setBackground(c);
        logo = new JLabel();
        member = new JLabel("MEMBERS");
        trainer = new JButton("TRAINER");
        payment = new JButton("PAYMENT");
        logout = new JButton("LOG-OUT");
        logoutIcon = new JLabel();
        name = new JLabel("MEMBER NAME-");
        mobile = new JLabel("MOBILE -");
        nameTextField = new JTextField();
        mobileTextField = new JTextField();
        SELECTED_TRAINER = new JLabel("SELECT TRAINER -");
        membership = new JLabel("MEMBERSHIP -");
        box = new JComboBox<>(TrainerArray);
        box1 = new JComboBox<>(membershipArray);
        gender = new JLabel("GENDER -");
        male = new JRadioButton("MALE");
        female = new JRadioButton("FEMALE");

        group = new ButtonGroup();

        add = new JButton("ADD");
        edit = new JButton("EDIT");
        delete = new JButton("DELETE");
        paymentLabel = new JLabel("PAYMENT -");
        paymentTextField = new JTextField();
        date = new JLabel("PAY DATE -");

        String days[] = new String[31];
        String months[] = { "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December" };
        String years[] = { "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029",
                "2030", "2031", "2032", "2033", "2034", "2035", "2036", "2037", "2038", "2039", "2040" };
        for (int j = 0; j < 31; j++) {

            days[j] = String.valueOf(j + 1);
        }

        // Create the column names for the table

        data = new Object[0][columnNames.length];
        day = new JComboBox<>(days);
        month = new JComboBox<>(months);
        year = new JComboBox<>(years);
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        jsp = new JScrollPane(table);

        logoutIcon.setIcon(iconResized1);
        group.add(female);
        group.add(male);
        logo.setIcon(iconResized);

        member.setFont(font);
        trainer.setFont(font);
        male.setFont(font1);
        female.setFont(font1);
        name.setFont(font1);
        mobile.setFont(font1);
        nameTextField.setFont(font1);
        SELECTED_TRAINER.setFont(font1);
        box.setFont(font1);

        nameTextField.setBorder(textFieldborder);
        mobileTextField.setFont(font1);
        mobileTextField.setBorder(textFieldborder);

        trainer.setFocusPainted(false);
        trainer.setBackground(c);
        trainer.setForeground(Color.BLACK);
        trainer.setBorder(border);

        payment.setFont(font);
        payment.setFocusPainted(false);
        payment.setBackground(c);
        payment.setForeground(Color.BLACK);
        payment.setBorder(border);

        logout.setFont(font);
        logout.setFocusPainted(false);
        logout.setBackground(new Color(255, 90, 84, 255));
        logout.setBorder(new MatteBorder(0, 0, 0, 0, Color.WHITE));
        logout.setForeground(Color.WHITE);

        box.setBackground(new Color(240, 248, 255));
        box.setBorder(textFieldborder);
        box1.setBackground(new Color(240, 248, 255));
        box1.setBorder(textFieldborder);
        box1.setFont(font1);
        membership.setFont(font1);
        gender.setFont(font1);
        male.setBackground(Color.WHITE);
        male.setFont(font2);
        female.setBackground(Color.WHITE);
        female.setFont(font2);
        female.setFocusPainted(false);
        male.setFocusPainted(false);

        day.setFont(font1);
        day.setBackground(new Color(240, 248, 255));
        day.setBorder(textFieldborder);

        month.setFont(font1);
        month.setBackground(new Color(240, 248, 255));
        month.setBorder(textFieldborder);

        year.setFont(font1);
        year.setBackground(new Color(240, 248, 255));
        year.setBorder(textFieldborder);

        add.setFont(font);
        add.setFocusPainted(false);
        add.setBackground(new Color(233, 252, 233));
        add.setForeground(Color.BLACK);
        add.setBorder(null);

        edit.setFont(font);
        edit.setFocusPainted(false);
        edit.setBackground(new Color(245, 245, 245));
        edit.setForeground(Color.BLACK);
        edit.setBorder(null);

        delete.setFont(font);
        delete.setFocusPainted(false);
        delete.setBackground(new Color(255, 204, 204));
        delete.setForeground(Color.BLACK);
        delete.setBorder(null);

        paymentLabel.setFont(font1);
        paymentTextField.setFont(font1);
        paymentTextField.setBorder(textFieldborder);

        day.setFont(font2);
        date.setFont(font1);

        month.setFont(font2);
        year.setFont(font2);

        table.setRowHeight(46);
        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(header.getWidth(), 60));
        header.setBackground(Color.CYAN);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        table.setBackground(Color.WHITE);
        header.setBackground(new Color(189, 239, 255));
        header.setFont(new Font("arial", Font.BOLD, 20));

        table.setForeground(Color.BLACK);
        jsp.setForeground(Color.BLACK);
        header.setForeground(Color.BLACK);
        table.setFont(f);

        logo.setBounds(125 - 100 + 50, 10, 100, 100);
        member.setBounds(58, 60, 200, 100);
        trainer.setBounds(28, 330, 200, 50);
        payment.setBounds(28, 430, 200, 50);
        logout.setBounds(90, 850, 150, 50);
        logoutIcon.setBounds(16, 845, 65, 65);
        name.setBounds(270, 20, 250, 100);
        nameTextField.setBounds(450, 50, 250, 40);
        mobile.setBounds(770, 20, 250, 100);
        mobileTextField.setBounds(880, 50, 250, 40);
        SELECTED_TRAINER.setBounds(270, 170, 200, 50);
        box.setBounds(470, 172, 220, 40);
        membership.setBounds(750, 90, 250, 200);
        box1.setBounds(910, 170, 250, 40);
        gender.setBounds(270, 200, 250, 250);
        male.setBounds(400, 305, 100, 35);
        female.setBounds(520, 305, 140, 35);

        add.setBounds(490, 510, 120, 50);
        edit.setBounds(690, 510, 120, 50);
        delete.setBounds(890, 510, 120, 50);
        paymentLabel.setBounds(270, 410, 250, 50);
        paymentTextField.setBounds(390, 420, 250, 40);
        date.setBounds(750, 310, 250, 40);
        day.setBounds(900, 310, 60, 35);
        month.setBounds(975, 310, 130, 35);
        year.setBounds(1120, 310, 100, 35);
        jsp.setBounds(253, 600, 1026, 350);

        panel.add(logo);
        panel.add(member);
        panel.add(trainer);
        panel.add(payment);
        panel.add(logout);
        panel.add(logoutIcon);
        add(logo);
        add(member);
        add(trainer);
        add(payment);
        add(logoutIcon);
        add(logout);
        add(name);
        add(nameTextField);
        add(mobile);
        add(mobileTextField);
        add(panel);
        add(SELECTED_TRAINER);
        add(box);
        add(membership);
        add(box1);
        add(gender);
        add(male);
        add(female);
        add(add);
        add(edit);
        add(delete);
        add(paymentLabel);
        add(paymentTextField);
        add(date);
        add(day);
        add(month);
        add(year);
        add(jsp);

        logout.addActionListener(this);
        add.addActionListener(this);
        delete.addActionListener(this);
        edit.addActionListener(this);
        trainer.addActionListener(this);
        payment.addActionListener(this);
        loadtable2();

        int columnIndexToHide = 7; // Index of the column to hide (0-based index)

        // Get the column model from the table
        TableColumnModel columnModel = table.getColumnModel();

        // Get the TableColumn object for the column to hide
        TableColumn column = columnModel.getColumn(columnIndexToHide);

        // Set the width of the column to 0, effectively hiding it
        column.setMinWidth(0);
        column.setMaxWidth(0);
        column.setPreferredWidth(0);
        column.setWidth(0);

        // Update the column model
        columnModel.getColumn(columnIndexToHide).setResizable(false); // Optional: Disable resizing

        // Optionally, update the table UI
        table.updateUI();

        setVisible(true);

    }

    public void loadtable2() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    model.setRowCount(0);

                    rs = st.executeQuery("SELECT * FROM member ORDER BY sort_order");

                    rsm = rs.getMetaData();

                    int columnCount = rsm.getColumnCount();
                    ArrayList<Object[]> dataList = new ArrayList<>();
                    while (rs.next()) {
                        Object[] row = new Object[columnCount];
                        for (int i = 0; i < columnCount; i++) {
                            row[i] = rs.getObject(i + 1);
                        }
                        dataList.add(row);
                    }

                    data = dataList.toArray(new Object[dataList.size()][]);

                    // Add new rows to the model
                    for (Object[] row : data) {
                        model.addRow(row);

                    }

                    // Notify the table that the data has changed
                    model.fireTableDataChanged();
                    jsp.revalidate();
                    jsp.repaint();

                    // Revalidate and repaint the JScrollPane to ensure the UI updates

                } catch (Exception e) {

                }

            }
        });
    }

    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == logout) {
            dispose();
            new GymManagement();
        }

        if (ae.getSource() == add) {
            try {

                String member = nameTextField.getText();
                String mobile = mobileTextField.getText();
                String trainer = (String) box.getSelectedItem();
                String membership = (String) box1.getSelectedItem();
                String date;
                if (male.isSelected()) {
                    gender1 = "male";
                } else if (female.isSelected()) {
                    gender1 = "female";
                }
                String days = (String) day.getSelectedItem();
                String months = (String) month.getSelectedItem();
                String years = (String) year.getSelectedItem();
                date = days + " / " + months + " / " + years;
                String payment = paymentTextField.getText();
                if (member != null && mobile != null && trainer != null && membership != null
                        && gender1 != null
                        && date != null && payment != null) {

                    int k = st.executeUpdate("insert into member values('" + member + "','" + mobile +
                            "','" + trainer + "','"
                            + membership + "','" + gender1 + "','" + date + "','" + payment + "');");
                    if (k == 1) {
                        loadtable2();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "fill all the fields.");
                }

            } catch (Exception e) {
                System.out.println("error2:-" + e);
            }
        }
        if (ae.getSource() == delete) {

            if (table.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(this, "Please select a valid row to delete.");
            } else {
                int selectedRow = table.getSelectedRow();
                int memberToDel = (int) table.getValueAt(selectedRow, 7);
                System.out.println(memberToDel);
                System.out.println(table.getRowCount());
                try {
                    int z = st.executeUpdate("delete from member where sort_order =" + memberToDel);
                    if (selectedRow >= 0 && selectedRow <= model.getRowCount()) {
                        model.removeRow(selectedRow);
                        model.fireTableDataChanged();
                        jsp.revalidate();
                        jsp.repaint();
                    }

                } catch (Exception e) {

                }
            }

        }
        if (ae.getSource() == edit) {
            int selectedRow = table.getSelectedRow();
            int selectedColumn = table.getSelectedColumn();

            if (selectedRow == -1 && selectedColumn == -1) {
                JOptionPane.showMessageDialog(this, "Please select a valid row to edit.");
            }

            else {

                Object[] row = new Object[table.getColumnCount()];
                for (int column = 0; column < table.getColumnCount(); column++) {
                    row[column] = table.getValueAt(selectedRow, column);
                }
                for (int column = 0; column < table.getColumnCount(); column++) {
                    model.setValueAt(row[column], selectedRow, column);

                }

                table.setFont(new Font("arial", Font.BOLD, 16));
                table.getCellEditor().stopCellEditing();
                model.fireTableDataChanged();
                jsp.revalidate();
                jsp.repaint();
                table.clearSelection();

                String name = (String) table.getValueAt(selectedRow, 0);
                String mobile = (String) table.getValueAt(selectedRow, 1);
                String trainer = (String) table.getValueAt(selectedRow, 2);
                String membership = (String) table.getValueAt(selectedRow, 3);
                String gender = (String) table.getValueAt(selectedRow, 4);
                String paydate = (String) table.getValueAt(selectedRow, 5);
                String payment = (String) table.getValueAt(selectedRow, 6);

                int id = (int) table.getValueAt(selectedRow, 7);
                System.out.println(id);

                try {

                    String query = "UPDATE member SET member = '" + name + "',mobile ='" + mobile + "',trainer='"
                            + trainer
                            + "',membership='" + membership + "',gender='" + gender + "',paydate='" + paydate
                            + "',payment='" + payment + "' where sort_order=" + id;

                    System.out.println("Row " + (selectedRow) + " updated successfully.");
                    st.executeUpdate(query);
                } catch (Exception e) {
                    System.out.println(e);
                }

            }

        }

        if (ae.getSource() == trainer) {
            dispose();
            new Trainer();
        }
        if (ae.getSource() == payment) {
            dispose();
            new Payment();
        }

    }

}

class Trainer extends JFrame implements ActionListener {
    JLabel logo, member, logoutIcon, name, mobile, selectTrainer, MemberShip, SELECTED_TRAINER, membership,
            gender, paymentLabel, date;
    JButton memberbtn, payment, logout, add, delete, edit;
    JTextField nameTextField, mobileTextField, paymentTextField;
    JComboBox day, month, year;
    JRadioButton male, female;
    ButtonGroup group;
    String gender1;
    JTable table;
    JScrollPane jsp;
    Connection con;
    Statement st;
    ResultSet rs;
    ResultSetMetaData rsm;
    Object[][] data;
    String[] columnNames = { "TRAINER", "MOBILE", "GENDER", "DATE OF SALARY", "AMOUNT PAID", "ID" };
    DefaultTableModel model;

    Trainer() {

        Properties props = new Properties();
        try (FileInputStream input = new FileInputStream("config/config.properties")) {
            props.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String url = props.getProperty("db.url");
        String userName = props.getProperty("db.username");
        String passWord = props.getProperty("db.password");
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(url, userName, passWord);
            st = con.createStatement();
        } catch (Exception e) {
            System.out.println("error ;-" + e);
        }

        setLayout(null);
        setSize(1300, 1000);
        setLocationRelativeTo(null);

        getContentPane().setBackground(Color.WHITE);
        JPanel panel = new JPanel();
        Color c = new Color(189, 239, 255);
        Color c1 = new Color(87, 188, 244);
        ImageIcon icon = new ImageIcon("D:\\GYM MANAGEMENT SYSTEM USING JAVA\\img\\two.png");
        Image ResizedIcon = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        ImageIcon iconResized = new ImageIcon(ResizedIcon);
        ImageIcon icon1 = new ImageIcon("D:\\GYM MANAGEMENT SYSTEM USING JAVA\\img\\log-out.png");
        Image ResizedIcon1 = icon1.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        ImageIcon iconResized1 = new ImageIcon(ResizedIcon1);
        Font font = new Font("arial", Font.BOLD, 25);
        Font font1 = new Font("arial", Font.BOLD, 20);
        Font font2 = new Font("arial", Font.BOLD, 20);
        Font font3 = new Font("arial", Font.BOLD, 18);
        LineBorder border = new LineBorder(Color.BLACK, 4);
        LineBorder border1 = new LineBorder(Color.BLACK, 1);
        LineBorder textFieldborder = new LineBorder(c, 2);

        panel.setLayout(null);
        panel.setBounds(0, 0, 250, 1000);
        panel.setBackground(c);
        logo = new JLabel();
        member = new JLabel("TRAINERS");
        memberbtn = new JButton("MEMBER");
        payment = new JButton("PAYMENT");
        logout = new JButton("LOG-OUT");
        logoutIcon = new JLabel();
        name = new JLabel("TRAINER NAME-");
        mobile = new JLabel("MOBILE -");
        nameTextField = new JTextField();
        mobileTextField = new JTextField();

        gender = new JLabel("GENDER -");
        male = new JRadioButton("MALE");
        female = new JRadioButton("FEMALE");

        group = new ButtonGroup();

        add = new JButton("ADD");
        edit = new JButton("EDIT");
        delete = new JButton("DELETE");
        paymentLabel = new JLabel("AMOUNT PAID -");
        paymentTextField = new JTextField();
        date = new JLabel("SALARY PAID DATE -");

        String days[] = new String[31];
        String months[] = { "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December" };
        String years[] = { "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029",
                "2030", "2031", "2032", "2033", "2034", "2035", "2036", "2037", "2038", "2039", "2040" };
        for (int i = 0; i < 31; i++) {

            days[i] = String.valueOf(i + 1);
        }

        // Create the column names for the table

        data = new Object[0][columnNames.length];
        day = new JComboBox<>(days);
        month = new JComboBox<>(months);
        year = new JComboBox<>(years);
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        jsp = new JScrollPane(table);

        logoutIcon.setIcon(iconResized1);
        group.add(female);
        group.add(male);
        logo.setIcon(iconResized);

        member.setFont(font);

        memberbtn.setFont(font);

        male.setFont(font1);
        female.setFont(font1);
        name.setFont(font1);
        mobile.setFont(font1);
        nameTextField.setFont(font1);
        nameTextField.setBorder(textFieldborder);
        mobileTextField.setFont(font1);
        mobileTextField.setBorder(textFieldborder);

        memberbtn.setFocusPainted(false);
        memberbtn.setBackground(c);
        memberbtn.setForeground(Color.BLACK);
        memberbtn.setBorder(border);
        payment.setFont(font);
        payment.setFocusPainted(false);
        payment.setBackground(c);
        payment.setForeground(Color.BLACK);
        payment.setBorder(border);
        logout.setFont(font);
        logout.setFocusPainted(false);
        logout.setBackground(new Color(255, 90, 84, 255));
        logout.setBorder(new MatteBorder(0, 0, 0, 0, Color.WHITE));
        logout.setForeground(Color.WHITE);

        gender.setFont(font1);
        male.setBackground(Color.WHITE);
        male.setFont(font2);
        female.setBackground(Color.WHITE);
        female.setFont(font2);
        female.setFocusPainted(false);
        male.setFocusPainted(false);

        day.setFont(font1);
        day.setBackground(new Color(240, 248, 255));
        day.setBorder(textFieldborder);

        month.setFont(font1);
        month.setBackground(new Color(240, 248, 255));
        month.setBorder(textFieldborder);

        year.setFont(font1);
        year.setBackground(new Color(240, 248, 255));
        year.setBorder(textFieldborder);

        add.setFont(font);
        add.setFocusPainted(false);
        add.setBackground(new Color(233, 252, 233));
        add.setForeground(Color.BLACK);
        add.setBorder(null);

        edit.setFont(font);
        edit.setFocusPainted(false);
        edit.setBackground(new Color(245, 245, 245));
        edit.setForeground(Color.BLACK);
        edit.setBorder(null);

        delete.setFont(font);
        delete.setFocusPainted(false);
        delete.setBackground(new Color(255, 204, 204));
        delete.setForeground(Color.BLACK);
        delete.setBorder(null);

        paymentLabel.setFont(font1);
        paymentTextField.setFont(font1);
        paymentTextField.setBorder(textFieldborder);

        day.setFont(font2);
        date.setFont(font1);

        month.setFont(font2);
        year.setFont(font2);

        table.setRowHeight(46);
        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(header.getWidth(), 60));
        header.setBackground(Color.CYAN);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        table.setBackground(Color.WHITE);
        header.setBackground(new Color(189, 239, 255));
        header.setFont(new Font("arial", Font.BOLD, 20));
        Font f = new Font("arial", Font.BOLD, 16);
        table.setForeground(Color.BLACK);
        jsp.setForeground(Color.BLACK);
        header.setForeground(Color.BLACK);
        table.setFont(f);

        logo.setBounds(125 - 80 + 40, 10, 100, 100);
        member.setBounds(58, 80, 200, 100);
        memberbtn.setBounds(28, 330, 200, 50);
        payment.setBounds(28, 430, 200, 50);
        logout.setBounds(90, 850, 150, 50);
        logoutIcon.setBounds(16, 845, 65, 65);
        name.setBounds(270, 20, 250, 100);
        nameTextField.setBounds(450, 50, 250, 40);
        mobile.setBounds(770, 20, 250, 100);
        mobileTextField.setBounds(880, 50, 250, 40);

        gender.setBounds(270, 100, 250, 250);
        male.setBounds(400, 205, 100, 35);
        female.setBounds(520, 205, 140, 35);

        add.setBounds(490, 510, 120, 50);
        edit.setBounds(690, 510, 120, 50);
        delete.setBounds(890, 510, 120, 50);
        paymentLabel.setBounds(270, 340, 250, 50);
        paymentTextField.setBounds(430, 345, 250, 40);
        date.setBounds(720, 205, 250, 40);
        day.setBounds(940, 205, 60, 35);
        month.setBounds(1015, 205, 130, 35);
        year.setBounds(1160, 205, 100, 35);
        jsp.setBounds(253, 600, 1026, 350);

        panel.add(logo);
        panel.add(member);
        panel.add(memberbtn);
        panel.add(payment);
        panel.add(logout);
        panel.add(logoutIcon);
        add(logo);
        add(member);
        add(memberbtn);
        add(payment);
        add(logoutIcon);
        add(logout);
        add(name);
        add(nameTextField);
        add(mobile);
        add(mobileTextField);
        add(panel);

        add(gender);
        add(male);
        add(female);

        add(add);
        add(edit);
        add(delete);
        add(paymentLabel);
        add(paymentTextField);
        add(date);
        add(day);
        add(month);
        add(year);
        add(jsp);
        logout.addActionListener(this);
        add.addActionListener(this);
        delete.addActionListener(this);
        edit.addActionListener(this);
        memberbtn.addActionListener(this);
        payment.addActionListener(this);
        loadtable2();

        int columnIndexToHide = 5; // Index of the column to hide (0-based index)

        // Get the column model from the table
        TableColumnModel columnModel = table.getColumnModel();

        // Get the TableColumn object for the column to hide
        TableColumn column = columnModel.getColumn(columnIndexToHide);

        // Set the width of the column to 0, effectively hiding it
        column.setMinWidth(0);
        column.setMaxWidth(0);
        column.setPreferredWidth(0);
        column.setWidth(0);

        // Update the column model
        columnModel.getColumn(columnIndexToHide).setResizable(false); // Optional: Disable resizing

        // Optionally, update the table UI
        table.updateUI();

        setVisible(true);

    }

    public void loadtable2() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    model.setRowCount(0);

                    rs = st.executeQuery("SELECT * FROM trainer ORDER BY sort_order");

                    rsm = rs.getMetaData();

                    int columnCount = rsm.getColumnCount();
                    ArrayList<Object[]> dataList = new ArrayList<>();
                    while (rs.next()) {
                        Object[] row = new Object[columnCount];
                        for (int i = 0; i < columnCount; i++) {
                            row[i] = rs.getObject(i + 1);
                        }
                        dataList.add(row);
                    }

                    data = dataList.toArray(new Object[dataList.size()][]);

                    // Add new rows to the model
                    for (Object[] row : data) {
                        model.addRow(row);

                    }

                    // Notify the table that the data has changed
                    model.fireTableDataChanged();
                    jsp.revalidate();
                    jsp.repaint();

                    // Revalidate and repaint the JScrollPane to ensure the UI updates

                } catch (Exception e) {

                }

            }
        });
    }

    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == logout) {
            dispose();
            new GymManagement();
        }

        if (ae.getSource() == add) {
            try {

                String tname = nameTextField.getText();
                String mobile = mobileTextField.getText();

                String date;
                if (male.isSelected()) {
                    gender1 = "male";
                } else if (female.isSelected()) {
                    gender1 = "female";
                }
                String days = (String) day.getSelectedItem();
                String months = (String) month.getSelectedItem();
                String years = (String) year.getSelectedItem();
                date = days + " / " + months + " / " + years;
                String payment = paymentTextField.getText();
                if (tname != null && mobile != null
                        && gender1 != null
                        && date != null && payment != null) {

                    int k = st.executeUpdate("insert into trainer values('" + tname + "','" + mobile +
                            "','" + gender1 + "','"
                            + date + "','" + payment + "');");
                    if (k == 1) {
                        loadtable2();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "fill all the fields.");
                }

            } catch (Exception e) {
                System.out.println("error2:-" + e);
            }
        }
        if (ae.getSource() == delete) {

            if (table.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(this, "Please select a valid row to delete.");
            } else {
                int selectedRow = table.getSelectedRow();
                int memberToDel = (int) table.getValueAt(selectedRow, 5);
                System.out.println(memberToDel);
                System.out.println(table.getRowCount());
                try {

                    int z = st.executeUpdate("delete from trainer where sort_order =" + memberToDel);
                    if (selectedRow >= 0 && selectedRow <= model.getRowCount()) {
                        model.removeRow(selectedRow);
                        model.fireTableDataChanged();
                        jsp.revalidate();
                        jsp.repaint();
                    }

                } catch (Exception e) {

                }
            }

        }
        if (ae.getSource() == edit) {
            int selectedRow = table.getSelectedRow();
            int selectedColumn = table.getSelectedColumn();

            if (selectedRow == -1 && selectedColumn == -1) {
                JOptionPane.showMessageDialog(this, "Please select a valid row to edit.");
            }

            else {
                selectedRow = table.getSelectedRow();

                Object[] row = new Object[table.getColumnCount()];
                for (int column = 0; column < table.getColumnCount(); column++) {
                    row[column] = table.getValueAt(selectedRow, column);
                }
                for (int column = 0; column < table.getColumnCount(); column++) {
                    model.setValueAt(row[column], selectedRow, column);

                }

                table.setFont(new Font("arial", Font.BOLD, 16));
                table.getCellEditor().stopCellEditing();
                model.fireTableDataChanged();
                jsp.revalidate();
                jsp.repaint();
                table.clearSelection();

                String name = (String) table.getValueAt(selectedRow, 0);
                String mobile = (String) table.getValueAt(selectedRow, 1);
                String gender = (String) table.getValueAt(selectedRow, 2);
                String date = (String) table.getValueAt(selectedRow, 3);
                String payment = (String) table.getValueAt(selectedRow, 4);
                int id = (int) table.getValueAt(selectedRow, 5);
                try {

                    String query = "UPDATE trainer SET name = '" + name + "',mobile ='" + mobile + "',gender='"
                            + gender
                            + "',saldate='" + date + "',amount='" + payment + "' where sort_order=" + id;
                    st.executeUpdate(query);

                    System.out.println("Row " + (selectedRow) + " updated successfully.");

                } catch (Exception e) {
                    System.out.println(e);
                }

            }

        }
        if (ae.getSource() == memberbtn) {
            dispose();
            new Member();
        }
        if (ae.getSource() == payment) {
            dispose();
            new Payment();
        }

    }

}

class Payment extends JFrame implements ActionListener {
    JLabel logo, member, logoutIcon, name, selectTrainer, MemberShip, SELECTED_TRAINER, membership,
            paymentLabel, date;
    JButton memberbtn, payment, logout, add, delete, edit;
    JTextField paymentTextField;
    JComboBox day, month, year, memberBox;

    String gender1;
    JTable table;
    JScrollPane jsp;
    Connection con;
    Statement st;
    ResultSet rs;
    ResultSetMetaData rsm;
    Object[][] data;
    String[] columnNames = { "MEMBER NAME ", "AMOUNT RECEIVED", "PAY DATE", "ID" };
    DefaultTableModel model;
    ArrayList<String> arrayList = new ArrayList<>(100);
    Object MemberArray[];

    Payment() {

        Properties props = new Properties();
        try (FileInputStream input = new FileInputStream("config/config.properties")) {
            props.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String url = props.getProperty("db.url");
        String userName = props.getProperty("db.username");
        String passWord = props.getProperty("db.password");
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(url, userName, passWord);
            st = con.createStatement();
        } catch (Exception e) {
            System.out.println("error ;-" + e);
        }

        setLayout(null);
        setSize(1300, 1000);
        setLocationRelativeTo(null);

        getContentPane().setBackground(Color.WHITE);
        JPanel panel = new JPanel();
        Color c = new Color(189, 239, 255);
        Color c1 = new Color(87, 188, 244);
        ImageIcon icon = new ImageIcon("D:\\GYM MANAGEMENT SYSTEM USING JAVA\\img\\money.png");
        Image ResizedIcon = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        ImageIcon iconResized = new ImageIcon(ResizedIcon);
        ImageIcon icon1 = new ImageIcon("D:\\GYM MANAGEMENT SYSTEM USING JAVA\\img\\log-out.png");
        Image ResizedIcon1 = icon1.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        ImageIcon iconResized1 = new ImageIcon(ResizedIcon1);
        Font font = new Font("arial", Font.BOLD, 28);
        Font font1 = new Font("arial", Font.BOLD, 20);
        Font font2 = new Font("arial", Font.BOLD, 20);
        Font font3 = new Font("arial", Font.BOLD, 18);
        LineBorder border = new LineBorder(Color.BLACK, 4);
        LineBorder border1 = new LineBorder(Color.BLACK, 1);
        LineBorder textFieldborder = new LineBorder(c, 2);

        panel.setLayout(null);
        panel.setBounds(0, 0, 250, 1000);
        panel.setBackground(c);
        logo = new JLabel();
        member = new JLabel("PAYMENT");
        memberbtn = new JButton("MEMBER");
        payment = new JButton("PAYMENT");
        logout = new JButton("LOG-OUT");
        logoutIcon = new JLabel();
        name = new JLabel("MEMBER NAME-");

        add = new JButton("ADD");
        edit = new JButton("EDIT");
        delete = new JButton("DELETE");
        paymentLabel = new JLabel("AMOUNT PAID -");
        paymentTextField = new JTextField();
        date = new JLabel("AMOUNT PAID DATE ");

        String days[] = new String[31];
        String months[] = { "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December" };
        String years[] = { "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029",
                "2030", "2031", "2032", "2033", "2034", "2035", "2036", "2037", "2038", "2039", "2040" };
        for (int i = 0; i < 31; i++) {

            days[i] = String.valueOf(i + 1);
        }

        try {
            rs = st.executeQuery("select member from member");
            rsm = rs.getMetaData();
            int rowCount = 0;
            while (rs.next()) {
                rowCount++;
            }
            arrayList.ensureCapacity(rowCount);
            rs = st.executeQuery("select member from member");
            while (rs.next()) {
                arrayList.add(rs.getString(1));
            }
            MemberArray = arrayList.toArray();

        } catch (Exception e) {

        }

        memberBox = new JComboBox<>(MemberArray);
        // Create the column names for the table

        data = new Object[0][columnNames.length];
        day = new JComboBox<>(days);
        month = new JComboBox<>(months);
        year = new JComboBox<>(years);
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        jsp = new JScrollPane(table);

        logoutIcon.setIcon(iconResized1);

        logo.setIcon(iconResized);

        member.setFont(font);

        memberbtn.setFont(font);

        name.setFont(font1);

        memberbtn.setFocusPainted(false);
        memberbtn.setBackground(c);
        memberbtn.setForeground(Color.BLACK);
        memberbtn.setBorder(border);
        payment.setFont(font);
        payment.setFocusPainted(false);
        payment.setBackground(c);
        payment.setForeground(Color.BLACK);
        payment.setBorder(border);
        logout.setFont(font);
        logout.setFocusPainted(false);
        logout.setBackground(new Color(255, 90, 84, 255));
        logout.setBorder(new MatteBorder(0, 0, 0, 0, Color.WHITE));
        logout.setForeground(Color.WHITE);

        day.setFont(font1);
        day.setBackground(new Color(240, 248, 255));
        day.setBorder(textFieldborder);

        month.setFont(font1);
        month.setBackground(new Color(240, 248, 255));
        month.setBorder(textFieldborder);

        year.setFont(font1);
        year.setBackground(new Color(240, 248, 255));
        year.setBorder(textFieldborder);

        add.setFont(font);
        add.setFocusPainted(false);
        add.setBackground(new Color(233, 252, 233));
        add.setForeground(Color.BLACK);
        add.setBorder(null);

        edit.setFont(font);
        edit.setFocusPainted(false);
        edit.setBackground(new Color(245, 245, 245));
        edit.setForeground(Color.BLACK);
        edit.setBorder(null);

        delete.setFont(font);
        delete.setFocusPainted(false);
        delete.setBackground(new Color(255, 204, 204));
        delete.setForeground(Color.BLACK);
        delete.setBorder(null);

        paymentLabel.setFont(font1);
        paymentTextField.setFont(font1);
        paymentTextField.setBorder(textFieldborder);

        day.setFont(font2);
        date.setFont(font1);

        month.setFont(font2);
        year.setFont(font2);

        memberBox.setFont(font1);

        memberBox.setBackground(new Color(240, 248, 255));
        memberBox.setBorder(textFieldborder);

        table.setRowHeight(46);
        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(header.getWidth(), 60));
        header.setBackground(Color.CYAN);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        table.setBackground(Color.WHITE);
        header.setBackground(new Color(189, 239, 255));
        header.setFont(new Font("arial", Font.BOLD, 20));
        Font f = new Font("arial", Font.BOLD, 16);
        table.setForeground(Color.BLACK);
        jsp.setForeground(Color.BLACK);
        header.setForeground(Color.BLACK);
        table.setFont(f);

        logo.setBounds(125 - 120 + 60, 10, 100, 100);
        member.setBounds(58, 80, 200, 100);
        memberbtn.setBounds(28, 330, 200, 50);
        payment.setBounds(28, 430, 200, 50);
        logout.setBounds(90, 850, 150, 50);
        logoutIcon.setBounds(16, 845, 65, 65);
        name.setBounds(270, 20, 250, 100);

        add.setBounds(490, 510, 120, 50);
        edit.setBounds(690, 510, 120, 50);
        delete.setBounds(890, 510, 120, 50);
        paymentLabel.setBounds(800, 50, 250, 50);
        paymentTextField.setBounds(990, 55, 250, 40);
        date.setBounds(635, 255, 250, 40);
        day.setBounds(570, 325, 60, 35);
        month.setBounds(660, 325, 130, 35);
        year.setBounds(820, 325, 100, 35);
        jsp.setBounds(253, 600, 1026, 350);
        memberBox.setBounds(450, 50, 250, 40);

        panel.add(logo);
        panel.add(member);
        panel.add(memberbtn);
        panel.add(payment);
        panel.add(logout);
        panel.add(logoutIcon);
        add(logo);
        add(member);
        add(memberbtn);
        add(payment);
        add(logoutIcon);
        add(logout);
        add(name);
        add(memberBox);

        add(panel);

        add(add);
        add(edit);
        add(delete);
        add(paymentLabel);
        add(paymentTextField);
        add(date);
        add(day);
        add(month);
        add(year);
        add(jsp);
        logout.addActionListener(this);
        add.addActionListener(this);
        delete.addActionListener(this);
        edit.addActionListener(this);
        memberbtn.addActionListener(this);
        loadtable2();

        int columnIndexToHide = 3; // Index of the column to hide (0-based index)

        // Get the column model from the table
        TableColumnModel columnModel = table.getColumnModel();

        // Get the TableColumn object for the column to hide
        TableColumn column = columnModel.getColumn(columnIndexToHide);

        // Set the width of the column to 0, effectively hiding it
        column.setMinWidth(0);
        column.setMaxWidth(0);
        column.setPreferredWidth(0);
        column.setWidth(0);

        // Update the column model
        columnModel.getColumn(columnIndexToHide).setResizable(false); // Optional: Disable resizing

        // Optionally, update the table UI
        table.updateUI();

        setVisible(true);

    }

    public void loadtable2() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    model.setRowCount(0);

                    rs = st.executeQuery("SELECT * FROM payment ORDER BY sort_order");

                    rsm = rs.getMetaData();

                    int columnCount = rsm.getColumnCount();
                    ArrayList<Object[]> dataList = new ArrayList<>();
                    while (rs.next()) {
                        Object[] row = new Object[columnCount];
                        for (int i = 0; i < columnCount; i++) {
                            row[i] = rs.getObject(i + 1);
                        }
                        dataList.add(row);
                    }

                    data = dataList.toArray(new Object[dataList.size()][]);

                    // Add new rows to the model
                    for (Object[] row : data) {
                        model.addRow(row);

                    }

                    // Notify the table that the data has changed
                    model.fireTableDataChanged();
                    jsp.revalidate();
                    jsp.repaint();

                    // Revalidate and repaint the JScrollPane to ensure the UI updates

                } catch (Exception e) {

                }

            }
        });
    }

    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == logout) {
            dispose();
            new GymManagement();
        }

        if (ae.getSource() == add) {
            try {

                String date;
                String mname = (String) memberBox.getSelectedItem();
                String days = (String) day.getSelectedItem();
                String months = (String) month.getSelectedItem();
                String years = (String) year.getSelectedItem();
                date = days + " / " + months + " / " + years;
                String payment = paymentTextField.getText();
                if (date != null && payment != null) {

                    int k = st.executeUpdate("insert into payment values('" + mname + "','"
                            + payment + "','" + date + "');");
                    if (k == 1) {
                        loadtable2();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "fill all the fields.");
                }

            } catch (Exception e) {
                System.out.println("error2:-" + e);
            }
        }
        if (ae.getSource() == delete) {

            if (table.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(this, "Please select a valid row to delete.");
            } else {
                int selectedRow = table.getSelectedRow();
                int memberToDel = (int) table.getValueAt(selectedRow, 3);
                System.out.println(memberToDel);
                System.out.println(table.getRowCount());
                try {
                    int z = st.executeUpdate("delete from payment where sort_order =" + memberToDel);
                    if (selectedRow >= 0 && selectedRow <= model.getRowCount()) {
                        model.removeRow(selectedRow);
                        model.fireTableDataChanged();
                        jsp.revalidate();
                        jsp.repaint();
                    }

                } catch (Exception e) {

                }
            }

        }
        if (ae.getSource() == edit) {
            int selectedRow = table.getSelectedRow();
            int selectedColumn = table.getSelectedColumn();

            if (selectedRow == -1 && selectedColumn == -1) {
                JOptionPane.showMessageDialog(this, "Please select a valid row to edit.");
            }

            else {
                selectedRow = table.getSelectedRow();

                Object[] row = new Object[table.getColumnCount()];
                for (int column = 0; column < table.getColumnCount(); column++) {
                    row[column] = table.getValueAt(selectedRow, column);
                }
                for (int column = 0; column < table.getColumnCount(); column++) {
                    model.setValueAt(row[column], selectedRow, column);

                }

                table.setFont(new Font("arial", Font.BOLD, 16));
                table.getCellEditor().stopCellEditing();
                model.fireTableDataChanged();
                jsp.revalidate();
                jsp.repaint();
                table.clearSelection();

                String name = (String) table.getValueAt(selectedRow, 0);
                String payment = (String) table.getValueAt(selectedRow, 1);
                String date = (String) table.getValueAt(selectedRow, 2);
                int id = (int) table.getValueAt(selectedRow, 3);

                try {

                    String query = "UPDATE payment SET name = '" + name + "',amount='" + payment + "',paydate='" + date
                            + "' where sort_order=" + id;
                    st.executeUpdate(query);
                    System.out.println("Row " + (selectedRow) + " updated successfully.");

                } catch (Exception e) {
                    System.out.println(e);
                }

            }

        }
        if (ae.getSource() == memberbtn) {
            dispose();
            new Member();
        }

    }

}