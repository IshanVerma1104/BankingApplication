import { useState } from "react";
import { registerUser } from "../services/UserAuthService";

function RegisterComponent(props){

    const [customerName, setCustomerName] = useState("");
    const [email, setEmail] = useState("");
    const [phone, setPhone] = useState("");
    const [address, setAddress] = useState("");
    const [city, setCity] = useState("");
    const [password, setPassword] = useState("");

    const [errorMessage, setErrorMessage] = useState("");
    const [successMessage, setSuccessMessage] = useState("");

    const register = async() => {

        try{

            setErrorMessage("");

            await registerUser({

                customerName,
                email,
                phone,
                address,
                city,
                password
            });

            setSuccessMessage(
                "Registration successful. Waiting for admin approval."
            );
        }
        catch(error){

            setErrorMessage(
                error.message
            );
        }
    };

    return (

<div
    className="d-flex justify-content-center align-items-center"
    style={{ minHeight: "100vh" }}
>

    <div
        className="card shadow"
        style={{ width: "500px" }}
    >

        <div className="card-header bg-primary text-white text-center">

            <h3>User Registration</h3>

        </div>

        <div className="card-body">

            {
                errorMessage &&

                <div className="alert alert-danger">
                    {errorMessage}
                </div>
            }

            {
                successMessage &&

                <div className="alert alert-success">
                    {successMessage}
                </div>
            }

            <input
                className="form-control mb-3"
                placeholder="Customer Name"
                value={customerName}
                onChange={(e)=>
                    setCustomerName(e.target.value)
                }
            />

            <input
                className="form-control mb-3"
                placeholder="Email"
                value={email}
                onChange={(e)=>
                    setEmail(e.target.value)
                }
            />

            <input
                className="form-control mb-3"
                placeholder="Phone"
                value={phone}
                onChange={(e)=>
                    setPhone(e.target.value)
                }
            />

            <input
                className="form-control mb-3"
                placeholder="Address"
                value={address}
                onChange={(e)=>
                    setAddress(e.target.value)
                }
            />

            <input
                className="form-control mb-3"
                placeholder="City"
                value={city}
                onChange={(e)=>
                    setCity(e.target.value)
                }
            />

            <input
                type="password"
                className="form-control mb-3"
                placeholder="Password"
                value={password}
                onChange={(e)=>
                    setPassword(e.target.value)
                }
            />

            <button
                className="btn btn-success w-100"
                onClick={register}
            >
                Register
            </button>

            <button
                className="btn btn-link w-100 mt-2"
                onClick={() =>
                    props.onBackToLogin()
                }
            >
                Back To Login
            </button>

        </div>

    </div>

</div>

);
}

export default RegisterComponent;