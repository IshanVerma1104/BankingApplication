const BASE_URL =
"http://localhost:9090/customers";

export const loginUser = async (loginData) => {

    const response = await fetch(
        `${BASE_URL}/login`,
        {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(loginData)
        }
    );

    const data = await response.json();

    if(!response.ok){

        throw new Error(
            data.message ||
            data.issueName ||
            "Login Failed"
        );
    }

    return data;
};

export const registerUser =
async(registrationData) => {

    const response =
    await fetch(

        `${BASE_URL}/register`,
        {
            method:"POST",

            headers:{
                "Content-Type":
                "application/json"
            },

            body:JSON.stringify(
                registrationData
            )
        }
    );

    const data =
    await response.json();

    if(!response.ok){

        throw new Error(
            data.issueName ||
            data.message ||
            "Registration Failed"
        );
    }

    return data;
};