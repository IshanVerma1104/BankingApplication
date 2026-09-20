import { useState, useEffect } from "react";
import authHeader from "../services/AuthHeader";
import 'bootstrap/dist/css/bootstrap.min.css';
import {
  createAccountAPI,
  getAllAccounts,
  getAccountByNumber,
  deposit as depositAPI,
  withdraw as withdrawAPI,
  transferFunds as transferFundsAPI,
  activateAccount,
  deactivateAccount,
  closeAccount
} from "../services/AccountService";
function AccountComponent() {

const [accountNumberTxn, setAccountNumberTxn] = useState("");
const [amount, setAmount] = useState("");
const [accounts,setAccounts] = useState([]);
const [accountId,setAccountId] = useState("");
const [accountNumber, setAccountNumber] = useState("");
const [customerId,setCustomerId] = useState("");
const [accountType,setAccountType] = useState("");
const [status,setStatus] = useState("ACTIVE");
const [balance,setBalance] = useState("");
const [fromAccount,setFromAccount] = useState("");
const [toAccount,setToAccount] = useState("");
const [searchMessage, setSearchMessage] = useState("");
const [transferAmount,setTransferAmount] =useState("");
const [editingId, setEditingId] = useState(null);
const [searchAccountNumber,setSearchAccountNumber] = useState("");
const [errorMessage, setErrorMessage] = useState("");
const [successMessage, setSuccessMessage] = useState("");

useEffect(() => {

    getAccounts();

}, []);


const createAccount = async () => {

    try{

        setErrorMessage("");
        setSuccessMessage("");

        if(
            !accountNumber ||
            !customerId ||
            !accountType ||
            !balance
        ){
            setErrorMessage("All fields are required");
            return;
        }

        await createAccountAPI({
            accountNumber,
            customerId,
            accountType,
            balance,
            status:"ACTIVE"
        });

        setSuccessMessage(
            "Account Created Successfully"
        );

        getAccounts();

        setAccountNumber("");
        setCustomerId("");
        setAccountType("");
        setBalance("");
        setStatus("ACTIVE");

    }
    catch(error){

        setErrorMessage(error.message);
    }
};

const searchAccountByNumber = async (accountNumber) => {

    if (!accountNumber) {

        setSearchMessage("");
        getAccounts();
        return;
    }

    try {

        const account =
              await getAccountByNumber(accountNumber);

        if (
            !account ||
            account.status === 404 ||
            account.error ||
            !account.accountId
        ) {

            setAccounts([]);

            setSearchMessage(
                `Account Number ${accountNumber} does not exist`
            );

            return;
        }

        setAccounts([account]);

        setSearchMessage("");

    } catch (error) {

        setAccounts([]);

        setSearchMessage(error.message);
    }
};

const saveInline = async(account) => {

    await fetch(

        `http://localhost:9090/accounts/updateAccountType/${account.accountId}/${account.accountType}`,

        {
            method:"PUT",
            headers:authHeader
        }

    );

    alert("Account Updated");

    setEditingId(null);

    getAccounts();
}


const handleTransferFunds = async() => {

    setErrorMessage("");
    setSuccessMessage("");

    if(
        !fromAccount ||
        !toAccount ||
        !transferAmount
    ){
        setErrorMessage(
            "From Account, To Account and Amount are required"
        );

        return;
    }

    try{

        await transferFundsAPI(
            fromAccount,
            toAccount,
            transferAmount
        );

        setSuccessMessage(
            "Transfer Successful"
        );

        getAccounts();

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


const getAccounts = async () => {

    const response = await fetch(
        "http://localhost:9090/accounts/all",
        {
            headers:authHeader
        }
    );

    const data = await getAllAccounts();

    setAccounts(data); 
};


const activateAccount = async(id)=>{

    await fetch(
        `http://localhost:9090/accounts/activate/${id}`,
        {
            method:"PUT",
            headers:authHeader
        }
    );

    alert("Account Activated");

    getAccounts();
};

const deactivateAccount = async(id)=>{

    await fetch(
        `http://localhost:9090/accounts/deactivate/${id}`,
        {
            method:"PUT",
            headers:authHeader
        }
    );

    alert("Account Deactivated");

    getAccounts();
};

const closeAccount = async(id)=>{

    await fetch(
        `http://localhost:9090/accounts/close/${id}`,
        {
            method:"PUT",
            headers:authHeader
        }
    );

    alert("Account Closed");

    getAccounts();
};


const handleDeposit = async() => {

    setErrorMessage("");
    setSuccessMessage("");

    if (!accountNumberTxn || !amount) {

        setErrorMessage(
            "Account Number and Amount are required"
        );

        return;
    }

    try{

        await depositAPI(
            accountNumberTxn,
            amount
        );

        setSuccessMessage(
            "Deposit Successful"
        );

        getAccounts();

    }
    catch(error){

        setErrorMessage(
            error.message
        );
    }
};

const handleWithdraw = async() => {

    setErrorMessage("");
    setSuccessMessage("");

    if (!accountNumberTxn || !amount) {

        setErrorMessage(
            "Account Number and Amount are required"
        );

        return;
    }

    try{

        await withdrawAPI(
            accountNumberTxn,
            amount
        );

        setSuccessMessage(
            "Withdraw Successful"
        );

        getAccounts();

    }
    catch(error){

        setErrorMessage(
            error.message
        );
    }
};

  return(

<div className="container-fluid mt-3">
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

    <div className="col-md-4">

    <div className="card shadow">

        <div className="card-header bg-primary text-white">

            Account Operations

        </div>

        <div className="card-body">
<input
required
className="form-control"
placeholder="Account Number"
value={accountNumber}
onChange={(e)=>setAccountNumber(e.target.value)}
/>
<br/><br/>

<input
required
type="number"
className="form-control"
placeholder="Customer Id"
value={customerId}
onChange={(e)=>setCustomerId(e.target.value)}
/>

<br/><br/>
<select
required
className="form-select"
value={accountType}
onChange={(e)=>setAccountType(e.target.value)}
>
    <option value="">Select Account Type</option>
    <option value="SAVINGS">Savings</option>
    <option value="CURRENT">Current</option>
    <option value="SALARY">Salary</option>
</select>

<br/><br/>
<input
required
type="number"
className="form-control"
placeholder="Initial Balance"
value={balance}
onChange={(e)=>setBalance(e.target.value)}
/>


<br/><br/>

<button
className="btn btn-success w-100"
onClick={createAccount}
>
Save Account
</button>

<hr/>

<h3>Deposit / Withdraw</h3>
<input
required
className="form-control"
placeholder="Account Number"
value={accountNumberTxn}
onChange={(e)=>setAccountNumberTxn(e.target.value)}
/>

<br/><br/>
<input
required
type="number"
className="form-control"
placeholder="Amount"
value={amount}
onChange={(e)=>setAmount(e.target.value)}
/>

<br/><br/>

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

<hr/>

<h3>Transfer Funds</h3>

<input
required
className="form-control"
placeholder="From Account"
value={fromAccount}
onChange={(e)=>setFromAccount(e.target.value)}
/>

<br/><br/>

<input
required
className="form-control"
placeholder="To Account"
value={toAccount}
onChange={(e)=>setToAccount(e.target.value)}
/>

<br/><br/>

<input
required
className="form-control"
placeholder="Amount"
value={transferAmount}
onChange={(e)=>setTransferAmount(e.target.value)}
/>

<br/><br/>

<button
className="btn btn-primary w-100"
onClick={handleTransferFunds}
>
Transfer
</button>

        </div> {/* card-body */}

    </div> {/* card */}

</div> {/* col-md-4 */}

<br></br>
<br></br>

<div className="col-md-8">

    <div className="card shadow">

        <div className="card-header bg-dark text-white">

            Account Records

        </div>

        <div className="card-body">

<input
className="form-control mb-3"
placeholder="Search Account Number"
value={searchAccountNumber}
onChange={(e)=>{

setSearchAccountNumber(e.target.value);

searchAccountByNumber(e.target.value);

}}
/>

<br/><br/>

{
searchMessage ? (

<div className="alert alert-danger text-center mt-3">
    {searchMessage}
</div>

) : (
<div className="table-responsive">

<table className="table table-bordered table-hover table-striped">

<thead className="table-dark">

<tr>

<th>Id</th>
<th>Account Number</th>
<th>Customer Id</th>
<th>Type</th>
<th>Balance</th>
<th>Status</th>

<th>Activate</th>
<th>Deactivate</th>
<th>Close</th>
<th>Edit</th>

</tr>

</thead>

<tbody>

{

accounts.map(account => (

<tr key={account.accountId}>

<td>{account.accountId}</td>

<td>{account.accountNumber}</td>

<td>{account.customerId}</td>

<td>

{
editingId === account.accountId ?

<input
value={account.accountType}
onChange={(e)=>{

const updated =
accounts.map(a=>

a.accountId === account.accountId

?

{
...a,
accountType:e.target.value
}

:

a

);

setAccounts(updated);

}}
/>

:

account.accountType
}

</td>

<td>{account.balance}</td>

<td>{account.status}</td>

<td>

{
account.status !== "ACTIVE" &&

<button
className="btn btn-success btn-sm"
onClick={() =>
activateAccount(account.accountId)
}
>
Activate
</button>
}

</td>

<td>

{
account.status === "ACTIVE" &&

<button
className="btn btn-warning btn-sm"
onClick={() =>
deactivateAccount(account.accountId)
}
>
Deactivate
</button>
}

</td>

<td>

{
account.status !== "CLOSED" &&

<button
className="btn btn-danger btn-sm"
onClick={() =>
closeAccount(account.accountId)
}
>
Close
</button>
}

</td>

<td>

{
editingId === account.accountId

?

<button
className="btn btn-success btn-sm"
onClick={()=>
saveInline(account)
}
>
Save
</button>

:

<button
className="btn btn-primary btn-sm"
onClick={() =>
setEditingId(account.accountId)
}
>
Edit
</button>

}

</td>

</tr>

))

}

</tbody>

</table>

<br></br>

</div>
  )
}
</div>
</div> {/* table-responsive */}
</div>
</div>
</div>
  );
}

export default AccountComponent;