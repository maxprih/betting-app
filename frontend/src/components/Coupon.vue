<template>
    <div class="position-fixed bottom-0 end-0 bets-container" style="z-index: 5; width: 23%;">
        <div v-if="showAlert" data-bs-theme="dark" class="alert alert-danger m-3 fade show" role="alert">
            <strong>Error!</strong> На балансе недостаточно средств!
        </div>
        <div class="card text-center text-white" style="background-color: #262630;">
            <div class="card-header">
                <button class="btn btn-outline-secondary w-100" type="button" data-bs-toggle="collapse"
                    data-bs-target="#coupon-body" aria-expanded="false" aria-controls="coupon-body">
                    <h4 class="text-center">Купон</h4>
                </button>
            </div>
            <div id="coupon-body">
                <div class="card-body">
                    <div>
                        <div class="d-flex flex-column align-items-start" v-for="match_event in selectedEvents"
                            :key="match_event.id">
                            <div data-bs-theme="dark" class="d-flex w-100">
                                <div class="me-auto">{{ match_event.name }} - {{ match_event.coefficient }}</div>
                                <button @click="removeFromCoupon(match_event)" type="button" class="btn-close"
                                    aria-label="Close"></button>
                            </div>
                            <div class="p2">{{ match_event.matchDate }}</div>
                            <div class="p1">{{ match_event.matchFirstPlayerName }} – {{ match_event.matchSecondPlayerName }}
                            </div>
                            <hr class="w-100" />
                        </div>
                    </div>
                </div>
                <div class="card-footer d-grid">
                    <div class="input-group" data-bs-theme="dark">
                        <input v-model="betAmount" type="number" class="form-control" placeholder="Сумма ставки"
                            aria-label="Сумма ставки" aria-describedby="button-addon2" style="background-color: #262630;">
                        <button @click="makeBet" class="btn btn-outline-success" type="button" id="button-addon2">Заключить
                            пари</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import apiService from "../services/api.service"

export default {
    name: "Coupon",
    data() {
        return {
            betAmount: 0,
            showAlert: false
        }
    },

    methods: {
        showErrorAlert() {
            this.showAlert = true;
            setTimeout(() => {
                this.showAlert = false;
            }, 3000);
        },
        removeFromCoupon(matchEvent) {
            this.$store.dispatch('events/removeEvent', matchEvent);
        },
        makeBet() {
            if (this.betAmount > this.currentBalance | this.betAmount <= 0) {
                this.showErrorAlert();
                return;
            }
            const idsArray = this.selectedEvents.map(event => event.id);

            if (idsArray.length <= 0) {
                return;
            }

            apiService.makeBet(this.betAmount, idsArray)
            this.$store.dispatch('events/clearAll');
            setTimeout(() => {
                this.$store.dispatch('auth/update');
            }, 450)
            this.betAmount = 0;
        }
    },
    computed: {
        selectedEvents() {
            return this.$store.getters['events/getSelectedEvents'];
        },
        currentBalance() {
            return this.$store.state.auth.user.balanceAmount;
        }
    },
};
</script>
<style>
.bets-container {
    margin-right: 20px;
    margin-bottom: 20px;
}
</style>