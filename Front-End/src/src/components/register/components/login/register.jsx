import React, { useState, useRef } from "react";
import loginImg from "../../login.svg";
import { useForm } from "react-hook-form";
import axios from "axios";

export const Register = ({ history, parentCallback, containerRef }) => {
  const [userAlreadyExists, setUserAlreadyExists] = useState(false);
  const { handleSubmit, register, errors, watch } = useForm();
  const password = useRef({});
  password.current = watch("password", "");
  const onSubmit = (values) => {
    axios
      .post("http://localhost:8080/api/auth/signup", values)
      .then((response) => {
        parentCallback();
        if (response.status === 201) {
          history.push("/login");
        }
      })
      .catch((error) => {
        setUserAlreadyExists(true);
      });
  };

  return (
    <form
      onSubmit={handleSubmit(onSubmit)}
      className="base-container"
      ref={containerRef}
    >
      <div className="header">Register</div>
      <div className="content">
        <div className="image">
          <img src={loginImg} />
        </div>
        <div className="form">
          <div className="form-group">
            <input
              name="username"
              placeholder="Username"
              ref={register({
                required: "You must specify a username",
                minLength: {
                  value: 8,
                  message: "Username must have at least 8 characters",
                },
              })}
            />
            {errors.username && (
              <p className="registerError">{errors.username.message}</p>
            )}
          </div>
          <div className="form-group">
            <input
              type="text"
              name="email"
              placeholder="E-mail"
              ref={register({
                required: "Required",
                pattern: {
                  value: /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i,
                  message: "invalid email address",
                },
              })}
            />
            {errors.email && (
              <p className="registerError">{errors.email.message}</p>
            )}
          </div>
          <div className="form-group">
            <input
              name="password"
              placeholder="Password"
              type="password"
              ref={register({
                required: "You must specify a password",
                minLength: {
                  value: 8,
                  message: "Password must have at least 8 characters",
                },
              })}
            />
            {errors.password && (
              <p className="registerError">{errors.password.message}</p>
            )}
          </div>
          <div className="form-group">
            <input
              name="passwordConfirm"
              placeholder="Confirm Password"
              type="password"
              ref={register({
                validate: (value) =>
                  value === password.current || "The passwords do not match",
              })}
            />
            {errors.passwordConfirm && (
              <p className="registerError">{errors.passwordConfirm.message}</p>
            )}
          </div>
        </div>
      </div>
      {userAlreadyExists ? (
        <p className="registerError">Username or Email already exists</p>
      ) : null}
      <div className="footer">
        <button type="submit" className="btn">
          Register
        </button>
      </div>
    </form>
  );
};
