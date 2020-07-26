export default {
  login: ({ usernameOrEmail, password }) => {
    console.log(usernameOrEmail)
    const request = new Request("http://localhost:8080/api/auth/signin", {
      method: "POST",
      body: JSON.stringify({ usernameOrEmail, password }),
      headers: new Headers({ "Content-Type": "application/json" }),
    });
    return fetch(request)
      .then((response) => {
        if (response.status < 200 || response.status >= 300) {
          throw new Error(response.statusText);
        }
        return response.json();
      })
      .then(({ accessToken, permissions }) => {
        localStorage.setItem("accessToken", accessToken);
        localStorage.setItem("permissions", permissions);
      });
  },
  logout: () => {
    localStorage.removeItem("accessToken");
    localStorage.removeItem("permissions");
    return Promise.resolve();
  },
  checkError: (error) => {
    // ...
  },
  checkAuth: () => {
    return localStorage.getItem("accessToken")
      ? Promise.resolve()
      : Promise.reject();
  },
  getPermissions: () => {
    const role = localStorage.getItem("permissions");
    return role ? Promise.resolve(role) : Promise.reject();
  },
};