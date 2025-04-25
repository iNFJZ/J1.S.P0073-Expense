/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import bo.ExpenseInputer;
import bo.ExpenseManager;
import entity.Expense;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import utils.ValidationAndNormalizingTextUtil;

/**
 *
 * @author iNJZ
 */
public class ExpenseManagerController {

    private ExpenseInputer expenseInputer;
    private ExpenseManager expenseManager;

    public ExpenseManagerController() {
        expenseInputer = new ExpenseInputer();
        expenseManager = new ExpenseManager();
    }

    public Expense addExpense() throws Exception {
        Expense expense = expenseInputer.inputExpenseInformation();
        if (expenseManager.addExpense(expense)) {
            return expense;
        }
        throw new Exception("Cant add");
    }

    public Expense deleteExpense() throws Exception {
        int id = ValidationAndNormalizingTextUtil.getInt("Enter expense id to delete: ", "Enter a valid number", "Enter a valid number", 0, Integer.MAX_VALUE);
        return expenseManager.deleteExpense(id);
    }

    public String displayInformationExpense() throws ParseException {
        return expenseManager.displayInformationExpense();
    }

    public double totalExpense() {
        return expenseManager.totalExpense();
    }

    public void exportToFile(File file) throws IOException {
        expenseManager.exportToFile(file);
    }

    public void importFromFile(File file) throws IOException, ParseException {
        expenseManager.importFromFile(file);
    }

    /**
     * Performs the import operation by selecting a file and importing its
     * contents.
     *
     * @return The path of the imported file, or null if cancelled
     * @throws IOException If an I/O error occurs
     * @throws ParseException If the date format is invalid
     */
    public String performImport() throws IOException, ParseException {
        File importFile = expenseManager.selectImportFile();
        if (importFile != null) {
            importFromFile(importFile);
            return importFile.getAbsolutePath();
        }
        return null;
    }

    /**
     * Performs the export operation by selecting a file and exporting expenses
     * to it.
     *
     * @return The path of the exported file, or null if cancelled
     * @throws IOException If an I/O error occurs
     */
    public String performExport() throws IOException {
        File exportFile = expenseManager.selectExportFile();
        if (exportFile != null) {
            exportToFile(exportFile);
            return exportFile.getAbsolutePath();
        }
        return null;
    }
}
