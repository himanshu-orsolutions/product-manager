package com.manage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;

import org.bson.Document;

import com.manage.mapper.IELTSMapper;
import com.manage.mapper.SchoolMapper;
import com.manage.model.IELTS;
import com.manage.model.School;
import com.manage.parser.IELTSSheetParser;
import com.manage.parser.SchoolsSheetParser;
import com.manage.util.BarCodeGenerator;

/**
 * The Product Manager.
 */
public class Manager extends javax.swing.JFrame {

	private static final long serialVersionUID = 123456789L;

	/**
	 * Creates new form Manager
	 */
	public Manager() {
		init();
		initComponents();
	}

	/**
	 * The School map
	 */
	private static HashMap<String, School> schoolMap = new HashMap<>();

	/**
	 * The IELTS map
	 */
	private static HashMap<String, IELTS> ieltsMap = new HashMap<>();

	/**
	 * The countries set
	 */
	private static TreeSet<String> countries = new TreeSet<>();

	/**
	 * The names set
	 */
	private static TreeSet<String> names = new TreeSet<>();

	/**
	 * Shows error message
	 */
	private void showErrorMessage(String message) {
		JOptionPane.showMessageDialog(this, message, "Failure", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Maps row IETLS data into models
	 * 
	 * @param ieltsInfo The row IELTS info
	 * @return The list of prepared IETLS
	 */
	@SuppressWarnings("unchecked")
	private List<IELTS> mapRowIELTS(Document ieltsInfo) {

		List<IELTS> ieltsRecords = new ArrayList<>();
		List<Document> data = (List<Document>) ieltsInfo.get("data");

		if (data != null && !data.isEmpty()) {
			for (Document ieltsDocument : data) {
				ieltsRecords.add(IELTSMapper.map(ieltsDocument));
			}
		}
		return ieltsRecords;
	}

	/**
	 * Maps row School data into models
	 * 
	 * @param schoolInfo The row School info
	 * @return The list of prepared School
	 */
	@SuppressWarnings("unchecked")
	private List<School> mapRowSchool(Document schoolInfo) {

		List<School> schoolRecords = new ArrayList<>();
		List<Document> data = (List<Document>) schoolInfo.get("data");

		if (data != null && !data.isEmpty()) {
			for (Document schoolDocument : data) {
				schoolRecords.add(SchoolMapper.map(schoolDocument));
			}
		}
		return schoolRecords;
	}

	/**
	 * Initializes the application
	 */
	private void init() {

		// Parsing the data
		Document ieltsInfo = IELTSSheetParser.parse("ORS.xlsx");
		Document schoolInfo = SchoolsSheetParser.parse("schools.xlsx");

		// Mapping row data to models
		List<IELTS> ieltsRecords = mapRowIELTS(ieltsInfo);
		List<School> schoolRecords = mapRowSchool(schoolInfo);

		// Preparing UI data sources
		// The data maps
		ieltsRecords.forEach(record -> ieltsMap.put(record.toString(), record));
		schoolRecords.forEach(record -> schoolMap.put(record.toString(), record));

		// The countries list
		ieltsRecords.forEach(record -> countries.add(record.getCountry()));
		schoolRecords.forEach(record -> countries.add(record.getCountry()));

		// The candidates list
		ieltsRecords.forEach(record -> names.add(record.getCandidateName()));
		schoolRecords.forEach(record -> names.add(record.getCandidateName()));
		names.remove("");
	}

	/**
	 * Gets the information of product
	 * 
	 * @param productType   The product type
	 * @param country       The country
	 * @param candidateName the candidate name
	 * @param referenceId   The reference ID
	 * @return The product information
	 */
	private String getInformation(String productType, String country, String candidateName, String referenceId) {

		StringBuilder stringBuilder = new StringBuilder();
		if (productType.equals("IELTS")) {
			IELTS ielts = ieltsMap.get(country + " " + candidateName + " " + referenceId);
			stringBuilder.append("Location: " + ielts.getLocation());
			stringBuilder.append("\n");
			stringBuilder.append("Exam Format: " + ielts.getExamFormat());
			stringBuilder.append("\n");
			stringBuilder.append("Registration Date: " + ielts.getRegistrationDate());
			stringBuilder.append("\n");
			stringBuilder.append("Test Date: " + ielts.getTestDate());
			stringBuilder.append("\n");
			stringBuilder.append("Payment Ref: " + ielts.getPaymentRef());
			stringBuilder.append("\n");
			stringBuilder.append("Payment Type: " + ielts.getPaymentType());
			stringBuilder.append("\n");
			stringBuilder.append("Total: " + ielts.getTotal());
		} else {
			School school = schoolMap.get(country + " " + candidateName + " " + referenceId);
			stringBuilder.append("Centre Name: " + school.getCentreName());
			stringBuilder.append("\n");
			stringBuilder.append("Total Local Fee($): " + school.getTotalLocalFee());
			stringBuilder.append("\n");
			stringBuilder.append("Number Of Exams: " + school.getNumberOfExams());
			stringBuilder.append("\n");
			stringBuilder.append("Payment Reference: " + school.getPaymentReference());
			stringBuilder.append("\n");
		}
		return stringBuilder.toString();
	}

	/**
	 * Generates the barcode
	 * 
	 * @param data The data
	 */
	private void generateBarCode(String data) {

		// Creating popup frame
		JFrame popup = new JFrame();
		popup.setResizable(false);
		popup.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		popup.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		popup.setLocationRelativeTo(this);

		// Image Icon
		byte[] imageBytes = BarCodeGenerator.generateBarCode(data);
		ImageIcon image = new ImageIcon(imageBytes);

		// Creating the image label
		JLabel imageLabel = new JLabel(image);

		// Adding the image component
		popup.getContentPane().add(imageLabel);
		popup.setSize(image.getIconWidth(), image.getIconHeight() + 10);
		popup.setVisible(true);
	}

	private void initComponents() {

		headerPane = new javax.swing.JLayeredPane();
		logo = new javax.swing.JLabel();
		userOptionsPane = new javax.swing.JLayeredPane();
		countryLabel = new javax.swing.JLabel();
		candidateNameLabel = new javax.swing.JLabel();
		referenceIdLabel = new javax.swing.JLabel();
		productTypeLabel = new javax.swing.JLabel();
		productTypeDropdown = new javax.swing.JComboBox<>();
		countryDropDown = new javax.swing.JComboBox<>();
		candidateNameField = new javax.swing.JTextField();
		referenceIdField = new javax.swing.JTextField();
		searchButton = new javax.swing.JButton();
		resultPane = new javax.swing.JLayeredPane();
		avatar = new javax.swing.JLabel();
		informationPane = new javax.swing.JScrollPane();
		informationArea = new javax.swing.JTextArea();
		generateBarcodeButton = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setBackground(new java.awt.Color(254, 254, 254));
		setMaximumSize(new java.awt.Dimension(630, 440));
		setMinimumSize(new java.awt.Dimension(630, 440));
		setPreferredSize(new java.awt.Dimension(630, 440));
		setResizable(false);
		getContentPane().setLayout(null);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		setLocationRelativeTo(null);


		headerPane.setBackground(new java.awt.Color(254, 254, 254));
		headerPane.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		headerPane.setForeground(new java.awt.Color(254, 254, 254));

		logo.setBackground(new java.awt.Color(254, 254, 254));
		logo.setForeground(new java.awt.Color(254, 254, 254));
		logo.setIcon(new javax.swing.ImageIcon("Header.jpg")); // NOI18N
		logo.setBorder(null);
		logo.setOpaque(true);

		headerPane.setLayer(logo, javax.swing.JLayeredPane.DEFAULT_LAYER);

		javax.swing.GroupLayout headerPaneLayout = new javax.swing.GroupLayout(headerPane);
		headerPane.setLayout(headerPaneLayout);
		headerPaneLayout.setHorizontalGroup(
				headerPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(logo,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		headerPaneLayout
				.setVerticalGroup(headerPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(logo, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE));

		userOptionsPane.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

		countryLabel.setText("Country");

		candidateNameLabel.setText("Candidate Name");

		referenceIdLabel.setText("Reference ID");

		productTypeLabel.setText("Product Type");

		productTypeDropdown.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "IELTS", "School" }));

		countryDropDown.setModel(new javax.swing.DefaultComboBoxModel<>(countries.toArray()));

		searchButton.setText("Search");
		searchButton.addActionListener(event -> {
			String productType = (String) productTypeDropdown.getSelectedItem();
			String country = (String) countryDropDown.getSelectedItem();
			String candidateName = candidateNameField.getText();
			String referenceId = referenceIdField.getText();

			// Searching for candidate
			String searchResult = getInformation(productType, country, candidateName, referenceId);
			if (searchResult.equals("")) {
				showErrorMessage("No information found!!");
			} else {
				informationArea.setText(searchResult);
			}
		});

		generateBarcodeButton.addActionListener(event -> {
			if (informationArea.getText().equals("")) {
				showErrorMessage("No candidate selected!!");
			} else {
				generateBarCode(referenceIdField.getText());
			}
		});

		userOptionsPane.setLayer(countryLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
		userOptionsPane.setLayer(candidateNameLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
		userOptionsPane.setLayer(referenceIdLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
		userOptionsPane.setLayer(productTypeLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
		userOptionsPane.setLayer(productTypeDropdown, javax.swing.JLayeredPane.DEFAULT_LAYER);
		userOptionsPane.setLayer(countryDropDown, javax.swing.JLayeredPane.DEFAULT_LAYER);
		userOptionsPane.setLayer(candidateNameField, javax.swing.JLayeredPane.DEFAULT_LAYER);
		userOptionsPane.setLayer(referenceIdField, javax.swing.JLayeredPane.DEFAULT_LAYER);
		userOptionsPane.setLayer(searchButton, javax.swing.JLayeredPane.DEFAULT_LAYER);

		javax.swing.GroupLayout userOptionsPaneLayout = new javax.swing.GroupLayout(userOptionsPane);
		userOptionsPane.setLayout(userOptionsPaneLayout);
		userOptionsPaneLayout.setHorizontalGroup(userOptionsPaneLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(userOptionsPaneLayout.createSequentialGroup().addContainerGap()
						.addGroup(userOptionsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(productTypeLabel).addComponent(countryLabel)
								.addComponent(candidateNameLabel).addComponent(referenceIdLabel))
						.addGap(48, 48, 48)
						.addGroup(userOptionsPaneLayout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
								.addComponent(productTypeDropdown, 0, 366, Short.MAX_VALUE)
								.addComponent(countryDropDown, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(candidateNameField).addComponent(referenceIdField))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(searchButton, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addContainerGap()));
		userOptionsPaneLayout.setVerticalGroup(userOptionsPaneLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(userOptionsPaneLayout.createSequentialGroup().addContainerGap().addGroup(userOptionsPaneLayout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(userOptionsPaneLayout.createSequentialGroup().addGroup(userOptionsPaneLayout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(productTypeLabel).addComponent(productTypeDropdown,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
								.addGroup(userOptionsPaneLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(countryLabel).addComponent(countryDropDown,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(userOptionsPaneLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(candidateNameLabel).addComponent(candidateNameField,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(userOptionsPaneLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(referenceIdLabel).addComponent(referenceIdField,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addComponent(searchButton, javax.swing.GroupLayout.Alignment.TRAILING,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE))
						.addContainerGap()));

		resultPane.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

		avatar.setIcon(new javax.swing.ImageIcon("noavatar.png")); // NOI18N
		avatar.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

		informationPane.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

		informationArea.setColumns(20);
		informationArea.setRows(5);
		informationPane.setViewportView(informationArea);

		generateBarcodeButton.setText("Generate Barcode");

		resultPane.setLayer(avatar, javax.swing.JLayeredPane.DEFAULT_LAYER);
		resultPane.setLayer(informationPane, javax.swing.JLayeredPane.DEFAULT_LAYER);
		resultPane.setLayer(generateBarcodeButton, javax.swing.JLayeredPane.DEFAULT_LAYER);

		javax.swing.GroupLayout resultPaneLayout = new javax.swing.GroupLayout(resultPane);
		resultPane.setLayout(resultPaneLayout);
		resultPaneLayout.setHorizontalGroup(resultPaneLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(resultPaneLayout.createSequentialGroup().addContainerGap().addComponent(avatar)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(resultPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(informationPane, javax.swing.GroupLayout.DEFAULT_SIZE, 461,
										Short.MAX_VALUE)
								.addGroup(resultPaneLayout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE)
										.addComponent(generateBarcodeButton, javax.swing.GroupLayout.PREFERRED_SIZE,
												201, javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addContainerGap()));
		resultPaneLayout
				.setVerticalGroup(resultPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(resultPaneLayout.createSequentialGroup().addContainerGap()
								.addGroup(resultPaneLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
										.addComponent(avatar, javax.swing.GroupLayout.DEFAULT_SIZE, 136,
												Short.MAX_VALUE)
										.addComponent(informationPane))
								.addGap(8, 8, 8).addComponent(generateBarcodeButton)
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(headerPane).addComponent(userOptionsPane).addComponent(resultPane));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addComponent(headerPane, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(userOptionsPane, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(resultPane)));

		pack();
	}

	/**
	 * Execution starts from here
	 * 
	 * @param args The command line arguments
	 */
	public static void main(String args[]) {

		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(Manager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(Manager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(Manager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(Manager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}

		java.awt.EventQueue.invokeLater(() -> new Manager().setVisible(true));
	}

	private javax.swing.JLabel avatar;
	private javax.swing.JTextField candidateNameField;
	private javax.swing.JLabel candidateNameLabel;
	private javax.swing.JComboBox<Object> countryDropDown;
	private javax.swing.JLabel countryLabel;
	private javax.swing.JButton generateBarcodeButton;
	private javax.swing.JLayeredPane headerPane;
	private javax.swing.JTextArea informationArea;
	private javax.swing.JScrollPane informationPane;
	private javax.swing.JLabel logo;
	private javax.swing.JComboBox<String> productTypeDropdown;
	private javax.swing.JLabel productTypeLabel;
	private javax.swing.JTextField referenceIdField;
	private javax.swing.JLabel referenceIdLabel;
	private javax.swing.JLayeredPane resultPane;
	private javax.swing.JButton searchButton;
	private javax.swing.JLayeredPane userOptionsPane;
}
