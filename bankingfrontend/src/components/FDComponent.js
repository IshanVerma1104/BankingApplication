import { useState} from "react";
import authHeader from "../services/AuthHeader";
import 'bootstrap/dist/css/bootstrap.min.css';
import {
    createFD as createFDService,
    getFDByAccountNumber,
    deactivateFD as deactivateFDService
}
from "../services/FDService";
function FDComponent() {

const [searchAccountNumber, setSearchAccountNumber] = useState("");

const [accountNumber, setAccountNumber] = useState("");

const [fds, setFds] = useState([]);

const [fdAmount, setFdAmount] = useState("");

const [tenureMonths, setTenureMonths] = useState("");

const [interestRate, setInterestRate] = useState("");

const [status, setStatus] = useState("ACTIVE");

const [searchMessage,setSearchMessage] = useState("");
const [errorMessage, setErrorMessage] = useState("");
const [successMessage, setSuccessMessage] = useState("");

const createFDRecord = async () => {

    setErrorMessage("");
    setSuccessMessage("");

    try{

        if(
            !accountNumber ||
            !fdAmount ||
            !tenureMonths ||
            !interestRate
        ){
            setErrorMessage(
                "Please fill all fields."
            );

            return;
        }

        const fd = {

            accountNumber,
            fdAmount,
            tenureMonths,
            interestRate,
            status: "ACTIVE"
        };

        await createFDService(fd);

        setSuccessMessage(
            "FD Created Successfully"
        );

        if(searchAccountNumber){
    searchFDByAccountNumber(
        searchAccountNumber
    );
}


        setAccountNumber("");
        setFdAmount("");
        setTenureMonths("");
        setInterestRate("");

    }
    catch(error){

        setErrorMessage(
            error.message
        );
        setAccountNumber("");
        setFdAmount("");
        setTenureMonths("");
        setInterestRate("");
    }
};


const searchFDByAccountNumber = async(accountNumber) => {

    if(!accountNumber){

        setSearchMessage("");
        setFds([]);
        return;
    }

    try{

        const data =
            await getFDByAccountNumber(accountNumber);

        if(
            !data ||
            (Array.isArray(data) && data.length === 0)
        ){

            setFds([]);

            setSearchMessage(
                `No Fixed Deposits found for AccountNumber ${accountNumber}`
            );

            return;
        }

        setSearchMessage("");

        if(Array.isArray(data)){
            setFds(data);
        }
        else{
            setFds([data]);
        }

    }
    catch(error){

    setFds([]);

    setSearchMessage(
        error.message
    );
}
};

const handleDeactivateFD = async(fdNumber)=>{

    try{

        setErrorMessage("");
        setSuccessMessage("");

        await deactivateFDService(fdNumber);

        setSuccessMessage(
            "FD Deactivated Successfully"
        );

        searchFDByAccountNumber(
            searchAccountNumber
        );
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

    {/* LEFT COLUMN */}

    <div className="col-md-4">


    <div className="card shadow">

        <div className="card-header bg-primary text-white">

            Create Fixed Deposit

        </div>
        <div className="card-body">
        <input
        required
type="number"
className="form-control mb-3"
placeholder="Account Number"
value={accountNumber}
onChange={(e)=>setAccountNumber(e.target.value)}
/>

        <input
        required
type="number"
className="form-control mb-3"
placeholder="FD Amount"
value={fdAmount}
onChange={(e)=>setFdAmount(e.target.value)}
/>

        

        <input
        required
type="number"
className="form-control mb-3"
placeholder="Tenure (Months)"
value={tenureMonths}
onChange={(e)=>setTenureMonths(e.target.value)}
/>



        <input
        required
type="number"
step="0.01"
className="form-control mb-3"
placeholder="Interest Rate"
value={interestRate}
onChange={(e)=>setInterestRate(e.target.value)}
/>

        <button className="btn btn-success w-100" onClick={createFDRecord}>
            Create FD
        </button>

            </div> {/* card-body */}

    </div> {/* card */}

</div> {/* col-md-4 */}

    {/* RIGHT COLUMN */}

    <div className="col-md-8">

    <div className="card shadow">

        <div className="card-header bg-dark text-white">

            FD Records

        </div>

        <div className="card-body">

        <input
        className="form-control mb-3"
        placeholder="Search Account Number"
        value={searchAccountNumber}
        onChange={(e)=>{

            setSearchAccountNumber(e.target.value);

            searchFDByAccountNumber(
                e.target.value
            );

        }}
        />

        <br/><br/>

        {
searchMessage ? (

<div className="alert alert-danger text-center fw-bold">
    {searchMessage}
</div>

) : (

<div className="table-responsive">

            <table className="table table-bordered table-hover table-striped">              

            <thead className="table-dark">

                <tr>

                    <th>FD Id</th>
                    <th>FD Number</th>
                    <th>Account Number</th>
                    <th>Amount</th>
                    <th>Tenure</th>
                    <th>Interest Rate</th>
                    <th>Status</th>
                    <th>Deactivate</th>

                </tr>

            </thead>

            <tbody>

            {

                fds.map((fd)=>(

                    <tr key={fd.fdId}>

                        <td>{fd.fdId}</td>

                        <td>{fd.fdNumber}</td>

                        <td>{fd.accountNumber}</td>

                        <td>{fd.fdAmount}</td>

                        <td>{fd.tenureMonths}</td>

                        <td>{fd.interestRate}</td>

                        <td>{fd.status}</td>

                        <td>

                        {

                            fd.status === "ACTIVE"

                            &&

                            <button className="btn btn-warning btn-sm"
                            onClick={() =>
                            handleDeactivateFD(fd.fdNumber)
                            }
                            >
                            Deactivate
                            </button>

                        }

                        </td>

                    </tr>

                ))

            }

            </tbody>

        </table>

</div>
)
}
</div> 

</div> 

</div> 

</div> 

</div>


);
}

export default FDComponent;