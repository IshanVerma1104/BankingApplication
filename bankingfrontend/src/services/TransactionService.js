const BASE_URL = "http://localhost:9090";

export const transferMoney = async(data) => {

    const response = await fetch(
      `${BASE_URL}/transactions/transfer`,
      {
          method:"POST",
          headers:{
             "Content-Type":"application/json"
          },
          body:JSON.stringify(data)
      }
    );

    return response.json();
};