<template>
  <div v-if="currentUser" class="container">
    <div class="rounded-rectangle h2" role="alert" style="text-align:center">
      Профиль <strong>{{ currentUser.name }}</strong>
    </div>
    <hr>
    <div style="display:flex;justify-content: space-between;">
      <div>
        <ul class="list-group h2" style="width:fit-content" data-bs-theme="dark">
          <li class="list-group-item d-flex align-items-center">
            <strong>ID:</strong>
            <span class="values">{{ currentUser.id }}</span>
          </li>
          <li class="list-group-item d-flex align-items-center">
            <strong>Логин:</strong>
            <span class="values">{{ currentUser.login }}</span>
          </li>
          <li class="list-group-item d-flex align-items-center">
            <strong>Баланс:</strong>
            <span class="values me-auto">{{ currentUser.balanceAmount }}</span>
            <button type="button" class="btn btn-outline-success values" @click="getMoney()"> Взять
              <strong>бесплатные</strong>
              50 фантиков</button>
          </li>
          <li class="list-group-item d-flex align-items-center">
            <strong>Роли:</strong>
            <span>
              <span class="values" v-for="role in currentUser.roles" :key="role.id">{{ role.roleName }}</span>
            </span>
          </li>

        </ul>
      </div>
      <div class="card" style="width:40%;" data-bs-theme="dark">
        <img src="../assets/osel.jpg" class="card-img-top" alt="Card image cap" style="width:100%;">
        <div class="card-footer d-grid">
          <div class="input-group" data-bs-theme="dark">
            <input v-model="donateAmount" type="number" class="form-control" placeholder="Сумма пожертвования"
              aria-label="Сумма пожертвования" aria-describedby="button-addon2" style="background-color: #262630;">
            <button @click="withdraw" class="btn btn-outline-success" type="button"
              id="button-addon2">Пожертвовать</button>
          </div>
        </div>
      </div>
    </div>


    <div v-if="showAlert" data-bs-theme="dark" class="alert alert-danger position-fixed m-3 fade show" role="alert"
      style="bottom: 0; right: 0; max-width: 300px;">
      <strong>Error!</strong> {{ error_message }}
    </div>
    <div v-if="showWithdrawAlertBoolean" data-bs-theme="dark" class="alert alert-danger position-fixed m-3 fade show"
      role="alert" style="bottom: 0; right: 0; max-width: 300px;">
      <strong>Error!</strong> На балансе недостаточно средств!
    </div>
    <div v-if="showWithdrawAlertSuccessBoolean" data-bs-theme="dark"
      class="alert alert-success position-fixed m-3 fade show" role="alert"
      style="bottom: 0; right: 0; max-width: 300px;">
      <strong>Success!</strong> Осел рад!
    </div>
  </div>
</template>

<script>
import apiService from '../services/api.service';
export default {
  name: 'Profile',
  data() {
    return {
      showAlert: false,
      showWithdrawAlertBoolean: false,
      showWithdrawAlertSuccessBoolean: false,
      error_message: "",
      bets: [],
      donateAmount: 0
    }
  },
  methods: {
    showErrorAlert() {
      this.showAlert = true;
      setTimeout(() => {
        this.showAlert = false;
      }, 3000);
    },
    showWithdrawAlert() {
      this.showWithdrawAlertBoolean = true;
      setTimeout(() => {
        this.showWithdrawAlertBoolean = false;
      }, 3000);
    },
    showWithdrawSuccessAlert() {
      this.showWithdrawAlertSuccessBoolean = true;
      setTimeout(() => {
        this.showWithdrawAlertSuccessBoolean = false;
      }, 3000);
    },
    getMoney() {
      apiService.getMoney()
        .then(
          setTimeout(() => {
            this.$store.dispatch('auth/update');
          }, 250))
        .catch(error => {
          if (error.response && error.response.status === 400) {
            this.error_message = error.response.data.message
            this.showErrorAlert();
          }
        })
    },
    getAllBets() {
      apiService.getAllBets()
        .then(response => {
          this.bets = response.data;
        })
    },
    withdraw() {
      if (this.donateAmount > this.currentUser.balanceAmount || this.donateAmount <= 0) {
        this.showWithdrawAlert();
        return;
      }

      this.showWithdrawSuccessAlert();
      apiService.donateToAnt(this.donateAmount)
      setTimeout(() => {
        this.$store.dispatch('auth/update');
      }, 450)
      this.donateAmount = 0;
    }
  },
  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    }
  },
  mounted() {
    this.$store.dispatch('auth/update');
    if (!this.currentUser) {
      this.$router.push('/login');
    }
    this.getAllBets();
  }
};
</script>

<style>
.rounded-rectangle {
  margin-top: 30px;
  background-color: #262630;
  border-radius: 10px;
  padding: 10px;
}

.values {
  margin-left: 20px;
}
</style>