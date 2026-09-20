import { useState } from "react";
import { loginUser } from "../services/UserAuthService";

function UserLoginComponent(props){
const [emailOrPhone, setEmailOrPhone] = useState("");
const [password, setPassword] = useState("");

const [errorMessage, setErrorMessage] = useState("");
const login = async () => {

    try{

        const user =
        await loginUser({

            emailOrPhone,
            password
        });

        localStorage.setItem(
            "loggedInUser",
            JSON.stringify(user)
        );

        props.onLoginSuccess();

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
    style={{ height: "100vh" }}
>

    <div
        className="card shadow"
        style={{ width: "400px" }}
    >

        <div className="card-header bg-success text-white text-center">

            <h3>User Login</h3>

        </div>

        <div className="card-body">

            {
                errorMessage &&

                <div className="alert alert-danger">
                    {errorMessage}
                </div>
            }

            <input
                className="form-control mb-3"
                placeholder="Email or Phone"
                value={emailOrPhone}
                onChange={(e) =>
                    setEmailOrPhone(e.target.value)
                }
            />

            <input
                type="password"
                className="form-control mb-3"
                placeholder="Password"
                value={password}
                onChange={(e) =>
                    setPassword(e.target.value)
                }
            />

            <button
    className="btn btn-success w-100"
    onClick={login}
>
    Login
</button>

<button
    className="btn btn-link w-100 mt-2"
    onClick={() =>
        props.onRegister()
    }
>
    New User? Register Here
</button>

        </div>

    </div>

</div>

);
}
export default UserLoginComponent;