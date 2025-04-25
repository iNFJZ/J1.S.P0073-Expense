/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo;

import entity.Expense;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author iNJZ
 */
public class ExpenseManager {

    private List<Expense> expenses;
    private int lastId;

    public ExpenseManager() {
        expenses = new ArrayList<>();
        lastId = 0;
    }

    private int searchById(int id) {
        for (int i = 0; i < expenses.size(); i++) {
            if (expenses.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    public boolean addExpense(Expense expense) {
        expense.setId(++lastId);
        return expenses.add(expense);
    }

    public Expense deleteExpense(int id) throws Exception {
        int index = searchById(id);
        {
            if (index != -1) {
                return expenses.remove(index);
            }
            throw new Exception("Cant delete");
        }
    }

    public String displayInformationExpense() throws ParseException {
        String header = String.format("%-5s %-15s %-10s %-15s\n", "ID", "Date", "Amount", "Content");
        String format = "";
        SimpleDateFormat oldDate = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat displayDate = new SimpleDateFormat("dd-MMMM-yyyy");
        for (Expense expense : expenses) {
            Date parseDate = oldDate.parse(expense.getDate());
            format += String.format("%-5s %-15s %-10s %-15s\n",
                    expense.getId(),
                    displayDate.format(parseDate),
                    expense.getAmount(),
                    expense.getContent());
        }
        return header + format;
    }

    public double totalExpense() {
        double total = 0;
        for (Expense expense : expenses) {
            total += expense.getAmount();
        }
        return total;
    }

    public void exportToFile(File file) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            // Write header
            writer.write("ID,Date,Amount,Content");
            writer.newLine();

            // Write each expense
            for (Expense expense : expenses) {
                String content = expense.getContent().replace("\"", "\"\""); // Escape quotes
                String line = String.format("%d,%s,%.2f,\"%s\"",
                        expense.getId(),
                        expense.getDate(),
                        expense.getAmount(),
                        content);
                writer.write(line);
                writer.newLine();
            }
        }
    }

    /**
     * Imports expenses from a file and adds them to the expenses list.
     *
     * @param file The input file
     * @throws IOException If an I/O error occurs
     * @throws ParseException If the date format is invalid
     * @throws NumberFormatException If the amount is not a valid number
     */
    public void importFromFile(File file) throws IOException, ParseException, NumberFormatException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.setLenient(false);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine(); // Skip header
            if (line == null || !line.startsWith("ID,Date,Amount,Content")) {
                throw new IOException("Invalid file format: Missing or incorrect header");
            }

            while ((line = reader.readLine()) != null) {
                // Split on commas, but respect quoted content
                String[] parts = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                if (parts.length != 4) {
                    System.err.println("Skipping invalid line: " + line);
                    continue;
                }

                try {
                    int id = Integer.parseInt(parts[0].trim());
                    String date = parts[1].trim();
                    double amount = Double.parseDouble(parts[2].trim());
                    String content = parts[3].trim().replaceAll("^\"|\"$", "").replace("\"\"", "\""); // Remove quotes, unescape

                    // Validate date
                    dateFormat.parse(date); // Throws ParseException if invalid

                    // Create and add expense
                    Expense expense = new Expense(id, date, amount, content);
                    expenses.add(expense);

                    // Update lastId if necessary
                    if (id > lastId) {
                        lastId = id;
                    }
                } catch (NumberFormatException | ParseException e) {
                    System.err.println("Skipping invalid line: " + line + " (" + e.getMessage() + ")");
                }
            }
        }
    }

    /**
     * Opens a file chooser dialog to select a file for importing expenses.
     *
     * @return The selected file, or null if the user cancels
     */
    public File selectImportFile() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text and CSV files", "txt", "csv");
        fileChooser.setFileFilter(filter);
        fileChooser.setDialogTitle("Select Import File");
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        return null;
    }

    /**
     * Opens a file chooser dialog to select a file for exporting expenses.
     *
     * @return The selected file, or null if the user cancels
     */
    public File selectExportFile() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text and CSV files", "txt", "csv");
        fileChooser.setFileFilter(filter);
        fileChooser.setDialogTitle("Select Export File");
        fileChooser.setSelectedFile(new File("expenses.csv"));
        int result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        return null;
    }

}
