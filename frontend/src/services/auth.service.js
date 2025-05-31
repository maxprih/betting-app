import axios from "axios";
import authHeader from "./auth-header";

const API_URL = "http://localhost:8080/api/v1/auth/";

class AuthService {
  login(user) {
    return axios
      .post(API_URL + "signin", {
        login: user.login,
        password: user.password,
      })
      .then((response) => {
        if (response.data) {
          localStorage.setItem("userToken", JSON.stringify(response.data));
        }

        return response.data;
      });
  }

  update() {
    return axios
      .get(API_URL + "me", { headers: authHeader() })
      .then((response) => {
        localStorage.setItem("user", JSON.stringify(response.data));
        return response.data;
      });
  }

  logout() {
    localStorage.removeItem("userToken");
    localStorage.removeItem("user");
  }

  register(user) {
    return axios.post(API_URL + "signup", {
      login: user.login,
      name: user.name,
      password: user.password,
      confirmPassword: user.confirm_password,
    });
  }
}

export default new AuthService();
