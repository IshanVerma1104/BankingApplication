import { useState } from "react";

function LoginComponent(props) {

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

    

  const login = () => {

    if (
      username === "admin" &&
      password === "admin123"
    ) {
      props.onLoginSuccess();
    }
    else {
      alert("Invalid Credentials");
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

        <div className="card-header bg-primary text-white text-center">

            <h3>Admin Login</h3>

        </div>

        <div className="card-body">

            <input
                className="form-control mb-3"
                placeholder="Username"
                value={username}
                onChange={(e) =>
                    setUsername(e.target.value)
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
                className="btn btn-primary w-100"
                onClick={login}
            >
                Login
            </button>

        </div>

    </div>

</div>

);
}

export default LoginComponent;