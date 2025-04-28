/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ui;

import controller.ExpenseManagerController;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import utils.ValidationAndNormalizingTextUtil;

/**
 *
 * @author iNJZ
 */
public class ExpenseManagement {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ParseException {
        ExpenseManagerController controller = new ExpenseManagerController();
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text and CSV files", "txt", "csv");
        fileChooser.setFileFilter(filter);
        String menu = """
                      
                      Handy Expense Program
                      1. Add an expense
                      2. Display all expenses
                      3. Delete an expense
                      4. Import expense
                      5. Export expense
                      6. Quit
                      
                      (Please choose 1 to Create, 2 to Display, 3 to Delete, 4 to Import, 5 to Export and 6 to Exit program).
                      Your choice: """;
        while (true) {
            int choice = ValidationAndNormalizingTextUtil.getInt(menu, "Must input an integer number!", "Must input an integer in range [1,6]", 1, 6);
            switch (choice) {
                case 1:
                    try {
                        controller.addExpense();
                        System.out.println("Add success!");
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;
                case 3:
                    try {
                        controller.deleteExpense();
                        System.out.println("Delete success!");
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;

                case 2:
                    try {
                        double total = controller.totalExpense();
                        System.out.println("List of expense");
                        System.out.println(controller.displayInformationExpense());
                        System.out.printf("%-15s\n", "Total");
                        System.out.printf("%-15s\n", total);

                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;

                case 4:
                    try {
                        String importPath = controller.performImport();
                        if (importPath != null) {
                            System.out.println("Imported from " + importPath);
                        } else {
                            System.out.println("Import cancelled.");
                        }
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;
                case 5:
                    try {
                        String exportPath = controller.performExport();
                        if (exportPath != null) {
                            System.out.println("Exported to " + exportPath);
                        } else {
                            System.out.println("Export cancelled.");
                        }
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;
                case 6: {
                    return;
                }
            }

        }
    }
}
