--Database structure, use this as reference when import or exporting data

--Personal User table, store logged in users in local environment
CREATE TABLE loggedUser (
    userId CHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) DEFAULT NULL,
    birthday DATE DEFAULT NULL,
    PRIMARY KEY (userId)
)

CREATE TABLE userBudget (
	budgetId CHAR(255) NOT NULL,
    ownUser CHAR(255) NOT NULL,
    PRIMARY KEY (budgetId),
    FOREIGN KEY (ownUser) REFERENCES loggedUser(userId)
);

--Balance table store all Balance instances with their respective budgetId
CREATE TABLE balanceList (
    balanceId CHAR(255) NOT NULL,
    ownBudget CHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(1024) DEFAULT NULL,
    currentValue FLOAT NOT NULL,
    creationDate DATE NOT NULL,
    PRIMARY KEY (balanceId),
    FOREIGN KEY (ownBudget) REFERENCES userBudget(budgetId)
)

--Saving table, store all Saving instances with their respective budgetId
CREATE TABLE savingHistory (
    savingId CHAR(255) NOT NULL,
    ownBudget CHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(1024) DEFAULT NULL,
    isActive INT DEFAULT 1, --0 for inactive, 1 for active
    creationDate DATE NOT NULL,
    activeTimeSpan INT DEFAULT 0, --count in days, 0 if this has no limit
    baseValue FLOAT NOT NULL,
    currentValue FLOAT NOT NULL, --amount of money that can be taken from this Saving instance (the saving become inactive if this value is 0?)
    interestRate FLOAT DEFAULT 0.0,
    interestInterval ENUM('DAILY', 'WEEKLY', 'MONTHLY', 'QUARTERLY', 'YEARLY') DEFAULT 'MONTHLY',
    PRIMARY KEY (savingId),
    FOREIGN KEY (ownBudget) REFERENCES userBudget(budgetId),
)

--Loan table, store all Loan instances with their respective budgetId
CREATE TABLE loanHistory (
    loanId CHAR(255) NOT NULL,
    ownBudget CHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(1024) DEFAULT NULL,
    isActive INT DEFAULT 1, --0 for inactive, 1 for active
    creationDate DATE NOT NULL,
    activeTimeSpan INT DEFAULT 0, --count in days, 0 if this has no limit
    baseValue FLOAT NOT NULL, --initially loaned amount
    currentValue FLOAT NOT NULL, --amount of money need to pay to instantly resolve the loan (the loan become inactive if this value is 0?)
    interestRate FLOAT DEFAULT 0.0,
    interestInterval ENUM('DAILY', 'WEEKLY', 'MONTHLY', 'QUARTERLY', 'YEARLY', 'ONE-TIME') DEFAULT 'MONTHLY',
    paymentInterval ENUM('DAILY', 'WEEKLY', 'MONTHLY', 'QUARTERLY', 'YEARLY') DEFAULT 'MONTHLY',
    PRIMARY KEY (loanId),
    FOREIGN KEY (ownBudget) REFERENCES userBudget(budgetId),
)

--Transaction Category type table, store available transaction category
CREATE TABLE transCategory (
    transCategoryId CHAR(255) NOT NULL,
    transType INT NOT NULL, --1 for income, 2 for expense
    name VARCHAR(255) NOT NULL,
    description VARCHAR(1023) DEFAULT NULL,
    PRIMARY KEY (transCategoryId)
)

--Transaction history, store all transaction (including incomes as well as expenses)
CREATE TABLE transHistory (
    transId CHAR(255) NOT NULL,
    applyBalance CHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(1023) DEFAULT NULL,
    value FLOAT NOT NULL,
    transType INT NOT NULL, --1 for income, 2 for expense
    transCategoryId CHAR(255) NOT NULL,
    occurDate DATE NOT NULL,
    PRIMARY KEY (transId),
    FOREIGN KEY (applyBalance) REFERENCES balanceList(balanceId),
    FOREIGN KEY (transCategoryId) REFERENCES transCategory(transCategoryId),
)
