import { createWebHistory, createRouter } from "vue-router";

import Home from "./components/Home.vue";
import Main from "./components/MainPage.vue"
import Match from "./components/MatchPage.vue"
import Login from "./components/Login.vue";
import Register from "./components/Register.vue";
import Unauth from "./components/Unauth.vue";
import BetHistory from "./components/BetHistory.vue";
import Transactions from "./components/Transactions.vue";
import AdminPanel from "./components/AdminPanel.vue"


const Profile = () => import("./components/Profile.vue")

const routes = [
  {
    path: "/",
    name: "home",
    component: Home,
  },
  {
    path: "/home",
    component: Home,
  },
  {
    path: "/main",
    component: Main,
    name: "main"
  },
  {
    path: "/history",
    component: BetHistory,
    name: "history"
  },
  {
    path: "/transactions",
    component: Transactions,
    name: "transactions"
  },
  {
    path: "/unauth",
    component: Unauth,
    name: "unauth"
  },
  {
    path: "/login",
    component: Login,

  },
  {
    path: "/register",
    component: Register,
  },
  { path: '/match/:id',
    component: Match,
    name: 'match' 
  },
  {
    path: "/profile",
    name: "profile",
    component: Profile,
  },
  {
    path: "/admin",
    name: "admin",
    component: AdminPanel,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;