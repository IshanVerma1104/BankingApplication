import { useState } from "react";
import { useEffect } from "react";
import 'bootstrap/dist/css/bootstrap.min.css';
import authHeader from "../services/AuthHeader";
import {
deposit as depositService,
withdraw as withdrawService,
transferMoney,
createFD as createFDService,
getAccountsByCustomerId
}
from "../services/UserService";
function UserComponent() {

  const [amount, setAmount] = useState("");

  const [fromAccount, setFromAccount] = useState("");
  const [toAccount, setToAccount] = useState("");
  const [transferAmount, setTransferAmount] = useState("");
  const[accountNumberFD, setAccountNumberFD] = useState("");
  const[accountNumberView, setAccountNumberView] = useState("");
  
  const [accounts, setAccounts] = useState([]);
  const [selectedAccount, setSelectedAccount] = useState("");
  const [fds, setFds] = useState([]);
  const [fdAmount, setFdAmount] = useState("");
  const [tenureMonths, setTenureMonths] = useState("");
  const [interestRate, setInterestRate] = useState("");
  const [errorMessage,setErrorMessage] = useState("");
  const [successMessage,setSuccessMessage] = useState("");
  const loggedInUser =
            JSON.parse(
    localStorage.getItem("loggedInUser")
);

useEffect(() => {

    loadAccounts();

}, []);

const handleCreateFD = async() => {

    try{

        setErrorMessage("");
        setSuccessMessage("");

        await createFDService({

            accountNumber : accountNumberFD,
            fdAmount,
            tenureMonths,
            interestRate,
            status:"ACTIVE"
        });

        setSuccessMessage(
            "FD Created Successfully"
        );
        loadFDs(accountNumberFD);
        setFdAmount("");
        setInterestRate("");
        setTenureMonths("");
    }
    catch(error){

        setErrorMessage(
            error.message
        );
    }
};


const loadFDs = async(accountNumberView) => {

    try{

        setErrorMessage("");

        const response = await fetch(
            `http://localhost:9090/fd/account/${accountNumberView}`,
            {
                headers: authHeader
            }
        );

        const data =
            await response.json();

        if(!response.ok){

            throw new Error(
                data.errorMessage ||
                data.message
            );
        }

        setFds(data);

    }
    catch(error){

        setFds([]);

        setErrorMessage(
            error.message
        );
    }
};

const handleDeposit = async() => {

    try{

        setErrorMessage("");
        setSuccessMessage("");

        await depositService(
            selectedAccount,
            amount
        );
        loadAccounts();
        setSuccessMessage(
            "Deposit Successful"
        );

    }
    catch(error){

        setErrorMessage(
            error.message
        );
    }
};

const handleWithdraw = async() => {

    try{

        setErrorMessage("");
        setSuccessMessage("");

        await withdrawService(
            selectedAccount,
            amount
        );
        loadAccounts(); 
        setSuccessMessage(
            "Withdrawal Successful"
        );

    }
    catch(error){

        setErrorMessage(
            error.message
        );
    }
};

const handleTransferFunds = async() => {

    try{

        setErrorMessage("");
        setSuccessMessage("");

        await transferMoney({
            fromAccountNumber: fromAccount,
            toAccountNumber: toAccount,
            amount: transferAmount
        });

        await loadAccounts();
        setSuccessMessage(
            "Transfer Successful"
        );
        setFromAccount("");
        setToAccount("");
        setTransferAmount("");
    }
    catch(error){

        setErrorMessage(
            error.message
        );
    }
};

const loadAccounts = async() => {

    try{

        const data =
        await getAccountsByCustomerId(
            loggedInUser.customerId
        );

        setAccounts(data);

        if(data.length > 0){

            setSelectedAccount(
                data[0].accountNumber
            );

            setFromAccount(
                data[0].accountNumber
            );

            setAccountNumberFD(
                data[0].accountNumber
            );

            setAccountNumberView(
                data[0].accountNumber
            );
            loadFDs(data[0].accountNumber
            );
        }
    }
    catch(error){

        setErrorMessage(
            error.message
        );
    }
};


  return(

<div className="container-fluid mt-3">
    <h2 className="text-center">

Welcome,
{loggedInUser.customerName}
</h2>
<div className="card-body">
{
errorMessage &&

<div className="alert alert-danger mb-3">
    {errorMessage}
</div>
}

{
successMessage &&

<div className="alert alert-success mb-3">
    {successMessage}
</div>
}
</div>
    <div className="row">
<div className="text-end">

    <button
    className="btn btn-danger"
    onClick={() => {

        localStorage.removeItem(
            "loggedInUser"
        );

        window.location.reload();

    }}
>
    Logout
</button>

</div>
      <div className="col-md-4">

<div className="card shadow mb-3">

<div className="card shadow mb-3">

<div className="card-header bg-primary text-white">

Customer Information

</div>

<div className="card-body">

<p>
<b>Name:</b>
{loggedInUser.customerName}
</p>

<p>
<b>Email:</b>
{loggedInUser.email}
</p>

<p>
<b>Phone:</b>
{loggedInUser.phone}
</p>

<hr />

<h6>Your Accounts</h6>

{
accounts.map(account => (

<div
key={account.accountNumber}
className="border p-2 mb-2"
>

<b>
{account.accountNumber}
</b>

<br />

Balance:
₹ {account.balance}

</div>

))
}

</div>

</div>
</div>

<div className="card shadow">

<div className="card-header bg-success text-white">
Deposit / Withdraw
</div>

<div className="card-body">

<select
className="form-control mb-3"
value={selectedAccount}
onChange={(e)=>
setSelectedAccount(
e.target.value
)}
>

{
accounts.map(account => (

<option
key={account.accountNumber}
value={account.accountNumber}
>

{account.accountNumber}

</option>

))
}

</select>

<input
className="form-control mb-3"
placeholder="Amount"
value={amount}
onChange={(e)=>setAmount(e.target.value)}
/>

<button
className="btn btn-success me-2"
onClick={handleDeposit}
>
Deposit
</button>

<button
className="btn btn-danger"
onClick={handleWithdraw}
>
Withdraw
</button>

</div>

</div>

</div>

<div className="col-md-4">

<div className="card shadow mb-3">

<div className="card-header bg-warning">
Transfer Funds
</div>

<div className="card-body">

<select
className="form-control mb-3"
value={fromAccount}
onChange={(e)=>
setFromAccount(
e.target.value
)}
>

{
accounts.map(account => (

<option
key={account.accountNumber}
value={account.accountNumber}
>

{account.accountNumber}

</option>

))
}

</select>

<input
className="form-control mb-3"
placeholder="To Account Number"
value={toAccount}
onChange={(e)=>setToAccount(e.target.value)}
/>

<input
className="form-control mb-3"
placeholder="Amount"
value={transferAmount}
onChange={(e)=>setTransferAmount(e.target.value)}
/>

<button
className="btn btn-primary w-100"
onClick={handleTransferFunds}
>
Transfer
</button>

</div>

</div>

<div className="card shadow">

<div className="card-header bg-info text-white">
Create Fixed Deposit
</div>

<div className="card-body">

<select
className="form-control mb-3"
value={accountNumberFD}
onChange={(e)=>
setAccountNumberFD(
e.target.value
)}
>

{
accounts.map(account => (

<option
key={account.accountNumber}
value={account.accountNumber}
>

{account.accountNumber}

</option>

))
}

</select>

<input
className="form-control mb-3"
placeholder="FD Amount"
value={fdAmount}
onChange={(e)=>setFdAmount(e.target.value)}
/>

<input
className="form-control mb-3"
placeholder="Tenure Months"
value={tenureMonths}
onChange={(e)=>setTenureMonths(e.target.value)}
/>

<input
className="form-control mb-3"
placeholder="Interest Rate"
value={interestRate}
onChange={(e)=>setInterestRate(e.target.value)}
/>

<button
className="btn btn-success w-100"
onClick={handleCreateFD}
>
Create FD
</button>

</div>

</div>

</div>
<div className="col-md-4">

<div className="card shadow">

<div className="card-header bg-dark text-white">
View Fixed Deposits
</div>

<div className="card-body">

<select
className="form-control mb-3"
value={accountNumberView}
onChange={(e)=>{

    setAccountNumberView(
        e.target.value
    );

    loadFDs(
        e.target.value
    );
}}
>

{
accounts.map(account => (

<option
key={account.accountNumber}
value={account.accountNumber}
>

{account.accountNumber}

</option>

))
}

</select>

<div className="table-responsive">

<table className="table table-bordered table-striped">

<thead className="table-dark">

<tr>
<th>Account Number</th>
<th>FD Number</th>
<th>Amount</th>
<th>Tenure</th>
<th>Rate</th>
<th>Status</th>

</tr>

</thead>

<tbody>

{
fds.map((fd)=>(

<tr key={fd.fdId}>
<td>{fd.accountNumber}</td>
<td>{fd.fdNumber}</td>
<td>{fd.fdAmount}</td>
<td>{fd.tenureMonths}</td>
<td>{fd.interestRate}</td>
<td>{fd.status}</td>

</tr>

))
}

</tbody>

</table>

</div>

</div>

</div>

</div>

</div> {/* row */}

</div>
  );
}

export default UserComponent;