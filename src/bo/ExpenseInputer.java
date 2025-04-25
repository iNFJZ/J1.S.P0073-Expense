/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo;

import entity.Expense;
import utils.ValidationAndNormalizingTextUtil;

/**
 *
 * @author iNJZ
 */
public class ExpenseInputer {

    private Expense expense;

    public Expense inputExpenseInformation() {
        expense = new Expense();
        expense.setDate(ValidationAndNormalizingTextUtil.getDateByString("Enter Expense Date: "));
        expense.setAmount(ValidationAndNormalizingTextUtil.getInt("Enter Expense Amount: ", "Enter a valid number", "Enter a valid number", 0, Integer.MAX_VALUE));
        expense.setContent(ValidationAndNormalizingTextUtil.getNonEmptyString("Enter Expense Content: "));
        return expense;
    }
}
