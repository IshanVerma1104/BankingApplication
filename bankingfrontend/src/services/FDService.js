import authHeader from "./AuthHeader";

const BASE_URL = "http://localhost:9090/fd";

export const createFD = async (fd) => {

    const response = await fetch(
        `${BASE_URL}/create`,
        {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                ...authHeader
            },
            body: JSON.stringify(fd)
        }
    );

    const data = await response.json();

if(!response.ok){

    throw new Error(
        data.message || "FD Creation Failed, Kindly try again."
    );
}

return data;
};

export const getFD = async (fdNumber) => {

    const response = await fetch(
        `${BASE_URL}/${fdNumber}`,
        {
            headers: authHeader
        }
    );

    return response.json();
};


export const deactivateFD = async (fdNumber) => {

    const response = await fetch(
        `${BASE_URL}/deactivate/${fdNumber}`,
        {
            method: "PUT",
            headers: authHeader
        }
    );

    const data = await response.json();

if(!response.ok){

    throw new Error(
        data.message || "FD Deactivation Failed"
    );
}

return data;
};

export const getFDByAccountNumber = async (accountNumber) => {

    const response = await fetch(
        `${BASE_URL}/account/${accountNumber}`,
        {
            headers: authHeader
        }
    );

   const data = await response.json();

if(!response.ok){

    throw new Error(
        data.message || "FD Not Found"
    );
}

return data;
};