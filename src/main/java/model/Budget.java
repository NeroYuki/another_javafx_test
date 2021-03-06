package model;

import java.util.ArrayList;

public class Budget {
    private String id = "";
    private ArrayList<Balance> balanceList;
    //should we only fetch active saving and loan instance?
    private ArrayList<Saving> activeSavingList;
    private ArrayList<Loan> activeLoanList;
    private ArrayList<FinancialGoal> activeFinancialGoalList;

    public Budget(ArrayList<Balance> balanceList, ArrayList<Saving> savingList, ArrayList<Loan> loanList, ArrayList<FinancialGoal> goalList) {
        setBalanceList(balanceList);
        setActiveSavingList(savingList);
        setActiveLoanList(loanList);
        setActiveFinancialGoalList(goalList);
    }

    public Budget(String id, ArrayList<Balance> balanceList, ArrayList<Saving> savingList, ArrayList<Loan> loanList, ArrayList<FinancialGoal> goalList) {
        this(balanceList, savingList, loanList, goalList);
        setId(id);
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    void setActiveLoanList(ArrayList<Loan> activeLoanList) {
        this.activeLoanList = activeLoanList;
    }

    public ArrayList<Loan> getActiveLoanList() {
        return activeLoanList;
    }

    void setActiveSavingList(ArrayList<Saving> activeSavingList) {
        this.activeSavingList = activeSavingList;
    }

    public ArrayList<Saving> getActiveSavingList() {
        return activeSavingList;
    }

    void setBalanceList(ArrayList<Balance> balanceList) {
        this.balanceList = balanceList;
    }

    public ArrayList<Balance> getBalanceList() {
        return balanceList;
    }

    public void setActiveFinancialGoalList(ArrayList<FinancialGoal> activeFinancialGoalList) {
        this.activeFinancialGoalList = activeFinancialGoalList;
    }

    public ArrayList<FinancialGoal> getActiveFinancialGoalList() {
        return activeFinancialGoalList;
    }
}
