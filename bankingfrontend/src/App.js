import logo from './logo.svg';
import './App.css';
import { useState } from "react";
import UserLoginComponent from "./components/UserLoginComponent";
import RegisterComponent from "./components/RegisterComponent";
import LoginComponent from "./components/LoginComponent";
import CustomerComponent from "./components/CustomerComponent";
import AccountComponent from "./components/AccountComponent";
import FDComponent from "./components/FDComponent";
import UserComponent from "./components/UserComponent";

function App() {
  const [screen, setScreen] = useState("home");
  const [module, setModule] = useState("customer");
    return (

        <div>

        {

            screen === "home" &&

<div
    className="d-flex justify-content-center align-items-center"
    style={{ height: "100vh" }}
>

    <div
        className="card shadow"
        style={{ width: "450px" }}
    >

        <div className="card-header bg-primary text-white text-center">

            <h3>Banking Application</h3>

        </div>

        <div className="card-body text-center">

            <p>
                Welcome to Banking Management System
            </p>

            <button
                className="btn btn-primary me-3"
                onClick={() =>
                    setScreen("adminLogin")
                }
            >
                Admin Portal
            </button>

            <button
    className="btn btn-success"
    onClick={() =>
        setScreen("userLogin")
    }
>
    User Portal
</button>

        </div>

    </div>

</div>
        }

        {

            screen === "adminLogin" &&

            <LoginComponent
                onLoginSuccess={() =>
                    setScreen("admin")
                }
            />

        }
{
    screen === "userLogin" &&

    <UserLoginComponent
        onLoginSuccess={() =>
            setScreen("user")
        }
        onRegister={() =>
            setScreen("register")
        }
    />
}
        {
    screen === "admin" &&

    <div>

        <h2 className="text-center">
    Banking Admin Management System
</h2>

<div className="text-center mb-3">

<div className="text-end mb-3">

    <button
        className="btn btn-danger"
        onClick={() => {

            localStorage.removeItem("token");

            setScreen("home");

        }}
    >
        Logout
    </button>

</div>

    <button
        className="btn btn-primary me-2"
        onClick={() => setModule("customer")}
    >
        Customer Module
    </button>

    <button
        className="btn btn-success me-2"
        onClick={() => setModule("account")}
    >
        Account Module
    </button>

    <button
        className="btn btn-warning"
        onClick={() => setModule("fd")}
    >
        FD Module
    </button>

</div>

        <br/><br/>

        {
            module === "customer" &&

            <CustomerComponent
                title="Customer Module"
            />
        }

        {
            module === "account" &&

            <AccountComponent
                title="Account Module"
            />
        }

        {
            module === "fd" &&

            <FDComponent
                title="FD Module"
            />
        }

    </div>
}
{
    screen === "register" &&

    <RegisterComponent
        onBackToLogin={() =>
            setScreen("userLogin")
        }
    />
}
        {

            screen === "user" &&

            <UserComponent />

        }

        </div>

    );
}
export default App;