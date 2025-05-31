export default function authHeader() {
  let user = JSON.parse(localStorage.getItem("userToken"));

  if (user && user.accessToken) {
    return { Authorization: "Bearer " + user.accessToken };
  } else {
    return {};
  }
}
