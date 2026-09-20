import authHeader from "./AuthHeader";

const BASE_URL = "http://localhost:9090/customers";

export const addCustomer = async (customer) => {

    const response = await fetch(
        `${BASE_URL}/add`,
        {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                ...authHeader
            },
            body: JSON.stringify(customer)
        }
    );

    const data = await response.json();

    if (!response.ok) {

        throw new Error(
            data.message || "Failed to add customer"
        );
    }

    return data;
};

export const getAllCustomers = async () => {

    const response = await fetch(
        `${BASE_URL}/all`,
        {
            headers: authHeader
        }
    );

    return response.json();
};

export const getCustomerById = async (id) => {

    const response = await fetch(
        `${BASE_URL}/${id}`,
        {
            headers: authHeader
        }
    );

    const data = await response.json();

    if (!response.ok) {

        throw new Error(
            data.message || "Customer not found"
        );
    }

    return data;
};

export const getCustomerByName = async (name) => {

    const response = await fetch(
        `${BASE_URL}/name/${name}`,
        {
            headers: authHeader
        }
    );

    return response.json();
};

export const getCustomerByEmail = async (email) => {

    const response = await fetch(
        `${BASE_URL}/email/${email}`,
        {
            headers: authHeader
        }
    );

    return response.json();
};

export const getCustomerByPhone = async (phone) => {

    const response = await fetch(
        `${BASE_URL}/phone/${phone}`,
        {
            headers: authHeader
        }
    );

    return response.json();
};

export const approveCustomer = async (customerId) => {

    const response = await fetch(
        `${BASE_URL}/approve/${customerId}`,
        {
            method: "PUT",
            headers: authHeader
        }
    );

    const data = await response.json();

    if(!response.ok){

        throw new Error(
            data.message ||
            "Approval Failed"
        );
    }

    return data;
};

export const getCustomerByCity = async (city) => {

    const response = await fetch(
        `${BASE_URL}/city/${city}`,
        {
            headers: authHeader
        }
    );

    return response.json();
};

export const getCustomerByStatus = async (status) => {

    const response = await fetch(
        `${BASE_URL}/status/${status}`,
        {
            headers: authHeader
        }
    );

    return response.json();
};

export const deleteCustomer = async (id) => {

    const response = await fetch(
        `${BASE_URL}/delete/${id}`,
        {
            method: "DELETE",
            headers: authHeader
        }
    );

    if (!response.ok) {

        const data = await response.json();

        throw new Error(
            data.message || "Delete failed"
        );
    }

    return true;
};

export const updateCustomer = async (id, customer) => {

    const response = await fetch(
        `${BASE_URL}/update/${id}`,
        {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                ...authHeader
            },
            body: JSON.stringify(customer)
        }
    );

    const data = await response.json();

    if (!response.ok) {

        throw new Error(
            data.message || "Customer update failed"
        );
    }

    return data;
};