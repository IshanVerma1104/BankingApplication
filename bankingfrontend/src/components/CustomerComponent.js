import { useState } from "react";
import { useEffect } from "react";
import authHeader from "../services/AuthHeader";
import 'bootstrap/dist/css/bootstrap.min.css';
import {
    addCustomer,
    getAllCustomers,
    getCustomerById,
    deleteCustomer,
    updateCustomer,
    approveCustomer
} from "../services/CustomerService";

function CustomerComponent(props) {
  const [editingId, setEditingId] = useState(null);
  const [searchCustomerId, setSearchCustomerId] = useState("");
  const [customers, setCustomers] = useState([]);
  const [customerName, setCustomerName] = useState("");
  const [email, setEmail] = useState("");
  const [phone, setPhone] = useState("");
  const [address, setAddress] = useState("");
  const [city, setCity] = useState("");
  const [status,setStatus] = useState("ACTIVE");
  const [searchMessage,setSearchMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const [successMessage, setSuccessMessage] = useState("");
  const pendingCustomers =
    customers.filter(
        customer =>
        customer.status === "PENDING"
    );

useEffect(() => {

    getCustomers();

}, []);

  const saveCustomer = async () => {

    setErrorMessage("");
    setSuccessMessage("");
    if(
    !customerName.trim() ||
    !email.trim() ||
    !phone.trim() ||
    !address.trim() ||
    !city.trim()
){
    setErrorMessage(
      "Please fill all details."
    );
}

    const customer = {
        customerName,
        email,
        phone,
        address,
        city,
        status:"ACTIVE"
    };

    try{

    setErrorMessage("");

    await addCustomer(customer);

    setSuccessMessage("Customer Added Successfully");

    getCustomers();

    setCustomerName("");
    setEmail("");
    setPhone("");
    setAddress("");
    setCity("");

}
catch(error){
    setCustomers([]);
    setErrorMessage(error.message);
}
};

  const getCustomers = async() => {

    const data = await getAllCustomers();
    setCustomers(data);

};

const approveCustomerHandler =
async (customerId) => {

    try{

        await approveCustomer(
            customerId
        );

        setSuccessMessage(
            "Customer Approved Successfully"
        );

        getCustomers();

    }
    catch(error){

        setErrorMessage(
            error.message
        );
    }
};

const searchCustomerById = async (id) => {

    setErrorMessage("");
    setSuccessMessage("");
    if(!id){

        setSearchMessage("");
        getCustomers();
        return;
    }

    try{

        const customer =
            await getCustomerById(id);

        if(
            !customer ||
            !customer.customerId
        ){

            setCustomers([]);

            setSearchMessage(
                `Customer Id ${id} does not exist`
            );

            return;
        }

        setCustomers([customer]);

        setSearchMessage("");

    }
   catch(error){

    setCustomers([]);

    setSearchMessage(error.message);

}
};



const deleteCustomerHandler = async (id) => {

    setErrorMessage("");
    setSuccessMessage("");
    const isConfirmed = window.confirm(
        "Are you sure you want to delete this customer?"
    );

    if (!isConfirmed) {
        return;
    }

    try {

        await deleteCustomer(id);

        setSuccessMessage("Customer Deleted Successfully");

        getCustomers();

    }
    catch(error) {

        setErrorMessage(error.message);
    }
};
    
const saveInline = async(customer)=>{
    setErrorMessage("");
    setSuccessMessage(""); 
    try{

        setErrorMessage("");

        await updateCustomer(
            customer.customerId,
            customer
        );

        setSuccessMessage("Customer Updated Successfully");

        setEditingId(null);

        getCustomers();
    }
    catch(error){

        setErrorMessage(error.message);
    }
}

  return (

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

        <div className="card-header bg-warning text-dark">

            Pending Approvals

        </div>

        <div className="card-body">

            {
                pendingCustomers.length === 0 ?

                <div className="text-center">
                    No Pending Requests
                </div>

                :

                pendingCustomers.map(customer => (

                    <div
                        key={customer.customerId}
                        className="card mb-2"
                    >

                        <div className="card-body">

                            <h6>
                                {customer.customerName}
                            </h6>

                            <p>
                                {customer.email}
                            </p>

                            <p>
                                {customer.phone}
                            </p>

                            <button
                                className="btn btn-success btn-sm"
                                onClick={() =>
                                    approveCustomerHandler(
                                        customer.customerId
                                    )
                                }
                            >
                                Approve
                            </button>

                        </div>

                    </div>

                ))
            }

        </div>

    </div>

</div>
    {/* RIGHT COLUMN */}

    <div className="col-md-8">

        <div className="card shadow">

<div className="card-header bg-dark text-white">

    Customer Records

</div>

<div className="card-body">
    
        <input
className="form-control mb-3"
placeholder="Search Customer Id"
value={searchCustomerId}
onChange={(e)=>{

    setSearchCustomerId(e.target.value);

    searchCustomerById(e.target.value);

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

                    <th>Id</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Address</th>
                    <th>City</th>
                    <th>Status</th>
                    <th>Edit</th>
                    <th>Delete</th>

                </tr>

            </thead>

            <tbody>

            {

                customers.map(customer => (

                <tr key={customer.customerId}>

                    <td>{customer.customerId}</td>

                    <td>

                    {
                        editingId === customer.customerId ?

                        <input
                            value={customer.customerName}
                            onChange={(e)=>{

                                const updatedCustomers =
                                customers.map(c =>

                                    c.customerId === customer.customerId

                                    ?

                                    {
                                        ...c,
                                        customerName:e.target.value
                                    }

                                    : c

                                );

                                setCustomers(updatedCustomers);

                            }}
                        />

                        :

                        customer.customerName
                    }

                    </td>

                    <td>

                        {
editingId === customer.customerId ?

<input
    value={customer.email}
    onChange={(e)=>{

        const updatedCustomers =
            customers.map(c =>

                c.customerId === customer.customerId

                ?

                {
                    ...c,
                    email:e.target.value
                }

                : c

            );

        setCustomers(updatedCustomers);

    }}
/>

:

customer.email

                    }

                    </td>

                    <td>

{
editingId === customer.customerId ?

<input
    value={customer.phone}
    onChange={(e)=>{

        const updatedCustomers =
            customers.map(c =>

                c.customerId === customer.customerId

                ?

                {
                    ...c,
                    phone:e.target.value
                }

                : c

            );

        setCustomers(updatedCustomers);

    }}
/>

:

customer.phone

}

</td>

                    <td>

{
editingId === customer.customerId ?

<input
    value={customer.address}
    onChange={(e)=>{

        const updatedCustomers =
            customers.map(c =>

                c.customerId === customer.customerId

                ?

                {
                    ...c,
                    address:e.target.value
                }

                : c

            );

        setCustomers(updatedCustomers);

    }}
/>

:

customer.address

}

</td>

                    <td>

{
editingId === customer.customerId ?

<input
    value={customer.city}
    onChange={(e)=>{

        const updatedCustomers =
            customers.map(c =>

                c.customerId === customer.customerId

                ?

                {
                    ...c,
                    city:e.target.value
                }

                : c

            );

        setCustomers(updatedCustomers);

    }}
/>

:

customer.city

}

</td>

                    <td>{customer.status}</td>

                    <td>

                    {

                        editingId === customer.customerId

                        ?

                        <button
                            className="btn btn-warning btn-sm" onClick={()=>saveInline(customer)}
                        >
                            Save
                        </button>

                        :

                        <button
                            className="btn btn-warning btn-sm" onClick={()=>
                                    setEditingId(customer.customerId)
            
                            }
                        >
                            Edit
                        </button>

                    }

                    </td>

                    <td>

                        <button
                            className="btn btn-danger btn-sm" onClick={()=>
                            deleteCustomerHandler(customer.customerId)
                            }
                        >
                            Delete
                        </button>

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

export default CustomerComponent;