<template>
    <div style="margin-top: 30px;" data-bs-theme="dark">
      <ul class="pagination justify-content-end mt-3">
        <li class="page-item">
          <a href="#" class="page-link" style="background-color: #262630" :class="{'disabled' : page === 0 }" @click="changePage(page - 1)">Previous</a>
        </li>
        <li class="page-item">
          <a class="page-link disabled" style="background-color: #262630">Page {{ page + 1 }} of {{ totalPages }}</a>
        </li>
        <li class="page-item">
          <a href="#" class="page-link" style="background-color: #262630" :class="{'disabled' : page === totalPages - 1 }" @click="changePage(page + 1)">Next</a>
        </li>
      </ul>
        <div v-for="bet in bets" :key="bet.id" class="card mb-3">
            <div class="card text-center text-white" style="background-color: #262630;">
                <div class="card-header text-muted d-flex flex-row align-items-center justify-content-between">
                    <div class="h3">№{{ bet.id }}</div>
                    <div class="h3 me-auto values">{{ bet.date }}</div>
                    <div class="h3">{{ bet.coefficient.toFixed(2) }}</div>
                </div>
                <div class="card-body">
                    <div class="d-flex flex-row" v-for="matchEvent in bet.matchEvents" :key="matchEvent.name">
                        <div class="h3">{{ matchEvent.matchFirstPlayerName }} – {{ matchEvent.matchSecondPlayerName }}</div>
                        <div class="h3 me-auto values">{{ matchEvent.name }}</div>
                        <div class="h3 values" :class="statusColorClass(matchEvent.status)">{{ matchEvent.status }}</div>
                        <div class="h3 values">{{ matchEvent.coefficient }}</div>
                    </div>
                </div>
                <div class="card-footer d-grid">
                    <div class="d-flex flex-row" data-bs-theme="dark">
                        <div class="h3 me-auto">Cумма: {{ bet.amount }}</div>
                        <div class="h3">Статус: <span :class="statusColorClass(bet.status)">{{ bet.status }}</span></div>
                        <div class="h3 values">Итог: <span :class="statusColorClass(bet.status)">{{ bet.winnings }}</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import apiService from '../services/api.service';

export default {
    name: "BetHistory",

    data() {
        return {
          bets: [],
          page: 0,
          pageSize: 5,
          totalPages: 1,
          sort: "date,asc"
        }
    },
    methods: {
        getAllBets() {
            apiService.getAllBets(this.page, this.pageSize, this.sort)
                .then(response => {
                    const data = response.data;
                    this.totalPages = data.totalPages;
                    this.bets = data.content;
                })
        },
        statusColorClass(status) {
            switch (status) {
                case 'TBD':
                    return 'text-yellow';
                case 'WIN':
                    return 'text-green';
                case 'LOSE':
                    return 'text-red';
                default:
                    return '';
            }
        },
      async changePage(newPage) {
        this.page = newPage;
        await this.getAllBets();
      },
    },
    mounted() {
        this.$store.dispatch('auth/update');
        this.getAllBets();
    }
}
</script>

<style>
.values {
    margin-left: 20px;
}

.text-yellow {
    color: yellow !important;
}

.text-green {
    color: green !important;
    ;
}

.text-red {
    color: red !important;
    ;
}
</style>