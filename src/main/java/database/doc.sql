--Database structure, use this as reference when import or exporting data
--IMPORTANT NOTE: ALL ID FIELD WILL USE UUID_SHORT() TO GENERATE THEIR UNIQUE ID

--Personal User table, store logged in users in local environment
CREATE TABLE loggedUser (
    userId CHAR(36) NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) DEFAULT NULL,
    birthday DATE DEFAULT NULL,
    PRIMARY KEY (userId)
)

CREATE TABLE userBudget (
	budgetId CHAR(36) NOT NULL,
    ownUser CHAR(36) NOT NULL,
    isAvailable BIT DEFAULT TRUE,
    PRIMARY KEY (budgetId),
    FOREIGN KEY (ownUser) REFERENCES loggedUser(userId)
);

--Balance table store all Balance instances with their respective budgetId
CREATE TABLE balanceList (
    balanceId CHAR(36) NOT NULL,
    ownBudget CHAR(36) NOT NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(1024) DEFAULT NULL,
    currentValue DOUBLE NOT NULL,
    creationDate DATE NOT NULL,
    isAvailable BIT DEFAULT TRUE
    PRIMARY KEY (balanceId),
    FOREIGN KEY (ownBudget) REFERENCES userBudget(budgetId)
)

--Saving table, store all Saving instances with their respective budgetId
CREATE TABLE savingHistory (
    savingId CHAR(36) NOT NULL,
    ownBudget CHAR(36) NOT NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(1024) DEFAULT NULL,
    isActive INT DEFAULT 1,
    --0 for inactive, 1 for active
    creationDate DATE NOT NULL,
    activeTimeSpan INT DEFAULT 0,
    --count in days, 0 if this has no limit
    baseValue DOUBLE NOT NULL,
    currentValue DOUBLE NOT NULL,
    --amount of money that can be taken from this Saving instance (the saving become inactive if this value is 0?)
    interestRate DOUBLE DEFAULT 0.0,
    interestInterval ENUM('DAILY', 'WEEKLY', 'MONTHLY', 'QUARTERLY', 'YEARLY') DEFAULT 'MONTHLY',
    lastCheckedDate DATE NOT NULL,
    PRIMARY KEY (savingId),
    FOREIGN KEY (ownBudget) REFERENCES userBudget(budgetId)
)

--Loan table, store all Loan instances with their respective budgetId
CREATE TABLE loanHistory (
    loanId CHAR(36) NOT NULL,
    ownBudget CHAR(36) NOT NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(1024) DEFAULT NULL,
    isActive INT DEFAULT 1,
    --0 for inactive, 1 for active
    creationDate DATE NOT NULL,
    activeTimeSpan INT DEFAULT 0,
    --count in days, 0 if this has no limit
    baseValue DOUBLE NOT NULL,
    --initially loaned amount
    currentValue DOUBLE NOT NULL,
    --amount of money need to pay to instantly resolve the loan (the loan become inactive if this value is 0?)
    interestRate DOUBLE DEFAULT 0.0,
    interestInterval ENUM('DAILY', 'WEEKLY', 'MONTHLY', 'QUARTERLY', 'YEARLY', 'ONE_TIME') DEFAULT 'MONTHLY',
    paymentInterval ENUM('DAILY', 'WEEKLY', 'MONTHLY', 'QUARTERLY', 'YEARLY') DEFAULT 'MONTHLY',
    lastCheckedDate DATE NOT NULL,
    PRIMARY KEY (loanId),
    FOREIGN KEY (ownBudget) REFERENCES userBudget(budgetId)
)

--Transaction Category type table, store available transaction category
CREATE TABLE transCategory (
    transCategoryId CHAR(36) NOT NULL,
    transType INT NOT NULL,
    --1 for income, 2 for expense
    name VARCHAR(255) NOT NULL,
    description VARCHAR(1023) DEFAULT NULL,
    iconPath VARCHAR(1023) DEFAULT NULL,
    isAvailable BIT DEFAULT TRUE,
    PRIMARY KEY (transCategoryId)
)

--Transaction history, store all transaction (including incomes as well as expenses)
CREATE TABLE transHistory (
    transId CHAR(36) NOT NULL,
    applyBalance CHAR(36) NOT NULL,
    description VARCHAR(1023) DEFAULT NULL,
    value DOUBLE NOT NULL,
    transType INT NOT NULL,
    --1 for income, 2 for expense
    transCategoryId CHAR(36) NOT NULL,
    occurDate DATE NOT NULL,
    PRIMARY KEY (transId),
    FOREIGN KEY (applyBalance) REFERENCES balanceList(balanceId),
    FOREIGN KEY (transCategoryId) REFERENCES transCategory(transCategoryId)
)

--Financial goal, store all financial binded to certain budget
CREATE TABLE financialGoal (
    goalId CHAR(36) NOT NULL,
    ownBudget CHAR(36) NOT NULL,
    description VARCHAR(1023) DEFAULT NULL,
    --1 for income, 2 for expense, 3 for balance
    type INT DEFAULT 1,
    checkBalanceId CHAR(36) DEFAULT NULL,
    threshold DOUBLE NOT NULL,
    startDate DATE NOT NULL,
    expireDate DATE NOT NULL,
    isActive BIT DEFAULT TRUE,
    PRIMARY KEY (goalId),
    FOREIGN KEY (ownBudget) REFERENCES userBudget(budgetId),
    FOREIGN KEY (checkBalanceId) REFERENCES balanceList(balanceId)
)
