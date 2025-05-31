<template>
  <div id="app">
    <nav class="navbar navbar-expand navbar-dark" style="background-color: #262631;">
      <a href="/" class="navbar-brand">
        <img src="./assets/osel.jpg" alt="" width="30" height="30" class="d-inline-block rounded"
             style="margin-left: 20px;">
        VT BETS</a>
      <div class="navbar-nav me-auto">
        <li class="nav-item">
          <router-link to="/main" class="nav-link">
            <font-awesome-icon icon="home" /> Matches
          </router-link>
        </li>
        <li class="nav-item" v-if="isAdmin">
          <router-link to="/admin" class="nav-link"> Admin panel
          </router-link>
        </li>
      </div>

      <div v-if="!currentUser" class="navbar-nav ml-auto">
        <li class="nav-item">
          <router-link to="/register" class="nav-link">
            <font-awesome-icon icon="user-plus" /> Register
          </router-link>
        </li>
        <li class="nav-item">
          <router-link to="/login" class="nav-link">
            <font-awesome-icon icon="sign-in-alt" /> Login
          </router-link>
        </li>
      </div>

      <div v-if="currentUser" class="navbar-nav ml-auto">
        <li class="nav-item">
          <router-link to="/transactions" class="nav-link">
            Transactions
          </router-link>
        </li>
        <li class="nav-item">
          <router-link to="/history" class="nav-link">
            <font-awesome-icon icon="history" /> History
          </router-link>
        </li>

        <li class="nav-item nav-link">
          Balance:
          {{ currentUser.balanceAmount }}
        </li>
        <li class="nav-item">
          <router-link to="/profile" class="nav-link">
            <font-awesome-icon icon="user" />
            {{ currentUser.name }}
          </router-link>
        </li>
        <li class="nav-item">
          <a class="nav-link" @click.prevent="logOut">
            <font-awesome-icon icon="sign-out-alt" /> Logout
          </a>
        </li>
      </div>
    </nav>

    <div class="container">
      <div v-if="pagesWithCoupon.includes($route.name)">
        <coupon />
      </div>
      <router-view />
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      pagesWithCoupon: ['match', 'main'],
    }
  },
  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    },
    isAdmin() {
      if (!this.currentUser) {
        return false;
      }
      return (this.$store.state.auth.user.roles).some(role => role.roleName === "ROLE_ADMIN");
    }

  },
  methods: {
    logOut() {
      this.$store.dispatch('auth/logout');
      this.$router.push('/login');
    }
  }
};
</script>
<style>
#app,
html {
  background-color: #121219;
}

p,
div {
  color: white;
}
</style>
