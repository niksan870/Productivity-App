// in src/MyLoginPage.js
import React, { useState } from "react";
import { useLogin, useNotify, Notification } from "react-admin";
import { ThemeProvider } from "@material-ui/styles";

const MyLoginPage = ({ theme }) => {
  const [usernameOrEmail, setUsernameOrEmail] = useState("");
  const [password, setPassword] = useState("");
  const login = useLogin();
  const notify = useNotify();
  const submit = (e) => {
    e.preventDefault();
    login({ usernameOrEmail, password }).catch(() => notify("Invalid usernameOrEmail or password"));
  };

  console.log(theme);
  return (
    <ThemeProvider theme={theme}>
      <form onSubmit={submit}>
        <input
          name="usernameOrEmail"
          type="usernameOrEmail"
          value={usernameOrEmail}
          onChange={(e) => setUsernameOrEmail(e.target.value)}
        />
        <input
          name="password"
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        <button type="submit">Submit</button>
      </form>
      <Notification />
    </ThemeProvider>
  );
};

export default MyLoginPage;
