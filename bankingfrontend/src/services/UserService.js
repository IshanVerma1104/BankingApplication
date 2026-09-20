import authHeader from "./AuthHeader";

const BASE_URL = "http://localhost:9090/account";
const BASE_URL_ACCOUNT = "http://localhost:9090/accounts";
// Deposit
export const deposit = async (accountId, amount) => {

    const response = await fetch(
        `${BASE_URL}/deposit/${accountId}/${amount}`,
        {
            method: "PUT",
            headers: authHeader
        }
    );

    const data = await response.json();

    if(!response.ok){

        throw new Error(
            data.errorMessage || data.message || "Deposit Failed ! Try again."
        );
    }

    return data;
};

// Withdraw
export const withdraw = async (accountId, amount) => {

    const response = await fetch(
        `${BASE_URL}/withdraw/${accountId}/${amount}`,
        {
            method: "PUT"
        }
    );

    const data = await response.json();

    if(!response.ok){

        throw new Error(
            data.errorMessage || data.message || "Withdraw Failed ! Try again."
        );
    }

    return data;
};

// Transfer Money
export const transferMoney = async (transferData) => {

    const response = await fetch(
        `${BASE_URL}/transactions/transfer`,
        {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(transferData)
        }
    );

    const data = await response.json();

    if(!response.ok){

        throw new Error(
            data.errorMessage || data.message || "Transfer Failed ! Try again."
        );
    }

    return data;
};

// Create FD
export const createFD = async (fdData) => {

    const response = await fetch(
        `${BASE_URL}/fd/create`,
        {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(fdData)
        }
    );

    const data = await response.json();

    if(!response.ok){

        throw new Error(
            data.errorMessage || data.message || "Creating the FD Failed ! Try again."
        );
    }

    return data;
};

// Get Account By Number
export const getAccount = async (accountNumberViewA) => {

    const response = await fetch(
        `${BASE_URL}/${accountNumberViewA}`
    );

    const data = await response.json();

    if(!response.ok){

        throw new Error(
            data.errorMessage || data.message || "Account doesn't exist ! Try again."
        );
    }

    return data;
};

// View Transactions
export const getTransactions = async (accountId) => {

    const response = await fetch(
        `${BASE_URL}/view/transactions/${accountId}`
    );

    return response.json();
};

// Search By Status
export const getByStatus = async (status) => {

    const response = await fetch(
        `${BASE_URL}/status/${status}`
    );

    return response.json();
};

// Search By Balance
export const getByBalance = async (balance) => {

    const response = await fetch(
        `${BASE_URL}/balanceGreater/${balance}`
    );

    return response.json();
};

// Get Balance
export const getBalance = async (accountId) => {

    const response = await fetch(
        `${BASE_URL}/balance/${accountId}`
    );

    return response.json();
};

// Search By Account Type
export const getByAccountType = async (accountType) => {

    const response = await fetch(
        `${BASE_URL}/accountType/${accountType}`
    );

    return response.json();
};
export const getAccountsByCustomerId = async (customerId) => {

    const response = await fetch(
        `${BASE_URL_ACCOUNT}/customer/${customerId}`,
        {
            headers: authHeader
        }
    );

    const data = await response.json();

    if(!response.ok){

        throw new Error(
            data.message ||
            "Unable to fetch accounts"
        );
    }

    return data;
};
// Search By Account Number
export const getByAccountNumber = async (accountNumber) => {

    const response = await fetch(
        `${BASE_URL}/accountNo/${accountNumber}`
    );

    return response.json();
};