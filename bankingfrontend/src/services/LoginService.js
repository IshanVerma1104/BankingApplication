export const loginUser =
async(loginData) => {

    const response =
    await fetch(

    `${BASE_URL}/login`,
    {
        method:"POST",

        headers:{
            "Content-Type":
            "application/json"
        },

        body:JSON.stringify(
            loginData
        )
    });

    const data =
    await response.json();

    if(!response.ok){

        throw new Error(
            data.issueName ||
            data.message
        );
    }

    return data;
};
