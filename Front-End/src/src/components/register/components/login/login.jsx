import loginImg from "../../login.svg";
import React, { useState } from "react";
import { useLogin, useNotify, Notification } from "react-admin";
import { ThemeProvider } from "@material-ui/styles";

export const Login = ({ theme, containerRef }) => {
  const [usernameOrEmail, setUsernameOrEmail] = useState("");
  const [password, setPassword] = useState("");
  const login = useLogin();
  const notify = useNotify();
  const submit = (e) => {
    e.preventDefault();
    login({ usernameOrEmail, password }).catch(() =>
      notify("Invalid usernameOrEmail or password")
    );
  };

  return (
    <ThemeProvider theme={theme} ref={containerRef}>
      <form onSubmit={submit}>
        <div className="base-container">
          <div className="header">Login</div>
          <div className="content">
            <div className="image">
              <img src={loginImg} />
            </div>
            <div className="form">
              <div className="form-group">
                <input
                  name="usernameOrEmail"
                  type="usernameOrEmail"
                  placeholder="usernameOrEmail"
                  value={usernameOrEmail}
                  onChange={(e) => setUsernameOrEmail(e.target.value)}
                />
              </div>
              <div className="form-group">
                <input
                  name="password"
                  type="password"
                  placeholder="Password"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                />
              </div>
            </div>
          </div>
          <div className="footer">
            <button type="submit" className="btn">
              Login
            </button>
          </div>
        </div>
      </form>
      <Notification />
    </ThemeProvider>
  );
};

// export default Login;
