package process;

import database.DatabaseBudget;
import database.DatabaseLoan;
import database.DatabaseSaving;
import exception.DatabaseException;
import exception.ProcessExeption;
import helper.IntervalEnum;
import model.Loan;
import model.Saving;

import java.time.LocalDate;
import java.util.ArrayList;

public class ProcessLoan {
    public static String addLoan(String Name, String desc, double interset, int activeTime, IntervalEnum.INTERVAL interestInterval, IntervalEnum.INTERVAL paymenInterVal) throws ProcessExeption {
        if (Name.length() >= 255) {
            throw new ProcessExeption(1);
        }
        if (desc.length() >= 1023) {
            throw new ProcessExeption(3);
        }
        if (activeTime <= 0) {
            throw new ProcessExeption();
        }
        if (interset < 0) {
            throw new ProcessExeption();
        }
        if (interestInterval == null) {
            throw new ProcessExeption();
        }
        if (paymenInterVal == null) {
            throw new ProcessExeption();
        }

        String id = null;
        try {
            Loan loan = new Loan(Name, desc, interset, LocalDate.now(), activeTime, interestInterval, paymenInterVal, 0, 0);
            id = DatabaseLoan.addLoan(loan, singletonBudget.getInstance().getBudget());
            loan.setId(id);
            singletonBudget.getInstance().getBudget().getActiveLoanList().add(loan);
        } catch (DatabaseException de) {
            de.printStackTrace();
            throw new ProcessExeption();
        }
        return id;
    }

    public static void editLoan(Loan loan, String name, String desc) throws ProcessExeption {
        if (name.length() >= 255) {
            throw new ProcessExeption(1);
        }
        if (desc.length() >= 1023) {
            throw new ProcessExeption(3);
        }
        loan.setName(name);
        loan.setDescription(desc);

        try {
            DatabaseLoan.updateLoan(loan);
            singletonBudget.getInstance().setBudget(DatabaseBudget.getBudget(singletonUser.getInstance().getUser()));
        } catch (DatabaseException de) {
            de.printStackTrace();
        }
    }
    public static void editLoan(Loan loan) throws ProcessExeption {
        try {
            DatabaseLoan.updateLoan(loan);
            singletonBudget.getInstance().setBudget(DatabaseBudget.getBudget(singletonUser.getInstance().getUser()));
        } catch (DatabaseException de) {
            de.printStackTrace();
        }
    }
    public static ArrayList<Loan> getLoan() throws ProcessExeption {
        try {
            return DatabaseLoan.getActiveLoan(singletonBudget.getInstance().getBudget().getId());
        } catch (DatabaseException de) {
            de.printStackTrace();
        }
        return null;
    }

    public static Loan getLoan(String id) throws ProcessExeption {
        ArrayList<Loan> loans = getLoan();
        for (Loan loan : loans) {
            if (loan.getId().equals(id)) return loan;
        }
        return null;
    }

    public static boolean deactivate(Loan loan) throws ProcessExeption {
        if (loan == null) throw new ProcessExeption();
        try {
            return DatabaseLoan.deactivateLoan(loan);
        } catch (DatabaseException de) {
            de.printStackTrace();
        }
        return false;
    }

    public static boolean deleteLoan(Loan loan) throws ProcessExeption {
        if (loan == null) throw new ProcessExeption();
        try {
            return DatabaseLoan.removeLoan(loan);
        } catch (DatabaseException de) {
            de.printStackTrace();
        }
        return false;
    }
    public static void applyLoan(String id,Double value) throws  ProcessExeption{
        Loan loan=getLoan(id);
        loan.setCurrentValue(value);
            editLoan(loan);

    }
    public static boolean paymentLoan(String id,Double value) throws ProcessExeption {
       Loan loan=getLoan(id);
       if(loan.getCurrentValue()<value) return false;
       loan.setCurrentValue(loan.getCurrentValue()-value);
       editLoan(loan);
       return true;
    }



    public static ArrayList<String> loanUpdating()throws ProcessExeption{
        ArrayList<String > result=new ArrayList<>();
        ArrayList<Loan> loans=getLoan();
        for(Loan loan:loans) {
            LocalDate now=LocalDate.now();
            if (loan.getCurrentValue() == 0) {
                deactivate(loan);
                continue;
            }
            double interest = loan.getInterestRate();
            double current = loan.getCurrentValue();
            LocalDate creationDay = loan.getCreationDate();
            int interval = loan.getInterestInterval().intervalToDays();
            int paymentInterval = loan.getPaymentInterval().intervalToDays();
            LocalDate lastCheck = loan.getLastCheckedDate();
            LocalDate expireDay = creationDay.plusDays(loan.getActiveTimeSpan());

            if (paymentInterval == 0) {
                if (expireDay.isBefore(now)) {
                    String expired = "Loan: " + loan.getName() + " is expired and need payment " + (now.toEpochDay() - expireDay.toEpochDay()) + " days ago\n";
                    result.add(expired);
                    loan.setLastCheckedDate(LocalDate.now());
                }
            } else {
                if (expireDay.isBefore(now)) {
                    String expired = " expire " + (now.toEpochDay() - expireDay.toEpochDay()) + " days ago\n";
                    result.add("Loan: " + loan.getName() + expired);
                    now = expireDay;
                }

                int numberOfInterval = (int) (now.toEpochDay() - creationDay.toEpochDay()) / interval - (int) (lastCheck.toEpochDay() - creationDay.toEpochDay()) / interval;
                for (int i = 0; i < numberOfInterval; i++) {
                    current = current * (1 + interest / 100);
                }
                int numberOfPayMentInterval = (int) (now.toEpochDay() - creationDay.toEpochDay()) / paymentInterval - (int) (lastCheck.toEpochDay() - creationDay.toEpochDay()) / paymentInterval;
                loan.setCurrentValue(current);
                loan.setLastCheckedDate(now);
                editLoan(loan);
                if (numberOfPayMentInterval > 0)
                    result.add("Loan: " + loan.getName() + " had gone through " + numberOfPayMentInterval + " unpaid interval\n");
            }
        }
        return result;
    }

}

