import authHeader from "./AuthHeader";

const BASE_URL = "http://localhost:9090/accounts";

export const createAccountAPI = async (account) => {

    const response = await fetch(
        `${BASE_URL}/create`,
        {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                ...authHeader
            },
            body: JSON.stringify(account)
        }
    );

    const data = await response.text();

if(!response.ok){
    throw new Error(
        data.message || "Account Creation Failed"
    );
}

return data;
};

export const getAllAccounts = async () => {

    const response = await fetch(
        `${BASE_URL}/all`,
        {
            headers: authHeader
        }
    );

    return response.json();
};

export const getAccountById = async (id) => {

    const response = await fetch(
        `${BASE_URL}/${id}`,
        {
            headers: authHeader
        }
    );

    return response.json();
};

export const getAccountByCustomerId = async (customerId) => {

    const response = await fetch(
        `${BASE_URL}/customer/${customerId}`,
        {
            headers: authHeader
        }
    );

    return response.json();
};

export const getAccountByType = async (type) => {

    const response = await fetch(
        `${BASE_URL}/type/${type}`,
        {
            headers: authHeader
        }
    );

    return response.json();
};

export const getAccountByStatus = async (status) => {

    const response = await fetch(
        `${BASE_URL}/status/${status}`,
        {
            headers: authHeader
        }
    );

    return response.json();
};

export const getActiveAccounts = async () => {

    const response = await fetch(
        `${BASE_URL}/active`,
        {
            headers: authHeader
        }
    );

    return response.json();
};

export const getInactiveAccounts = async () => {

    const response = await fetch(
        `${BASE_URL}/inactive`,
        {
            headers: authHeader
        }
    );

    return response.json();
};

export const deposit = async (accountNumber, amount) => {

    const response = await fetch(
        `${BASE_URL}/deposit/${accountNumber}/${amount}`,
        {
            method: "PUT",
            headers: authHeader
        }
    );

    const data = await response.json();

    if (!response.ok) {

        throw new Error(
            data.message || "Deposit Failed"
        );
    }

    return data;
};

export const withdraw = async (accountNumber, amount) => {

    const response = await fetch(
        `${BASE_URL}/withdraw/${accountNumber}/${amount}`,
        {
            method: "PUT",
            headers: authHeader
        }
    );

    const data = await response.json();

    if (!response.ok) {

        throw new Error(
            data.message || "Withdraw Failed"
        );
    }

    return data;
};

export const transferFunds = async (
    fromAccount,
    toAccount,
    amount
) => {

    const response = await fetch(
        `${BASE_URL}/transfer?fromAccount=${fromAccount}&toAccount=${toAccount}&amount=${amount}`,
        {
            method: "PUT",
            headers: authHeader
        }
    );

    const data = await response.json();

    if(!response.ok){

        throw new Error(
            data.message || "Transfer Failed"
        );
    }

    return data;
};

export const activateAccount = async (id) => {

    const response = await fetch(
        `${BASE_URL}/activate/${id}`,
        {
            method: "PUT",
            headers: authHeader
        }
    );

    const data = await response.json();

if(!response.ok){
    throw new Error(
        data.message || "Activation Failed"
    );
}

return data;
};

export const deactivateAccount = async (id) => {

    const response = await fetch(
        `${BASE_URL}/deactivate/${id}`,
        {
            method: "PUT",
            headers: authHeader
        }
    );

    const data = await response.json();

if(!response.ok){
    throw new Error(
        data.message || "Deactivation Failed"
    );
}

return data;
};

export const getAccountByNumber = async (accountNumber) => {

    const response = await fetch(
        `${BASE_URL}/number/${accountNumber}`,
        {
            headers: authHeader
        }
    );

    const data = await response.json();

if(!response.ok){
    throw new Error(
        data.message || "Account Not Found"
    );
}

return data;
};

export const closeAccount = async (id) => {

    const response = await fetch(
        `${BASE_URL}/close/${id}`,
        {
            method: "PUT",
            headers: authHeader
        }
    );

    const data = await response.json();

if(!response.ok){
    throw new Error(
        data.message || "Close Account Failed"
    );
}

return data;
};