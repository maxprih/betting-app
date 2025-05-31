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
        <div v-for="transaction in transactions" :key="transaction.id" class="card mb-3">
            <div class="card text-center text-white" style="background-color: #262630;">
                <div class="card-header text-muted d-flex flex-row align-items-center justify-content-between">
                    <div class="h3">№{{ transaction.id }}</div>
                    <div class="h3 me-auto values">{{ transaction.date }}</div>
                </div>
                <div class="card-footer d-grid">
                    <div class="d-flex flex-row" data-bs-theme="dark">
                        <div class="h3 me-auto">Cумма: {{ transaction.amount }}</div>
                        <div class="h3">Тип: <span :class="statusColorClass(transaction.type)">{{ transaction.type }}</span>
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
    name: "Transactions",

    data() {
        return {
          transactions: [],
          page: 0,
          pageSize: 5,
          totalPages: 1,
          sort: "date,asc"
        }
    },
    methods: {
        getAllTransactions() {
            apiService.getAllTransactions(this.page, this.pageSize, this.sort)
                .then(response => {
                  const data = response.data;
                  this.totalPages = data.totalPages;
                  this.transactions = data.content;
                })
        },
        statusColorClass(status) {
            switch (status) {
                case 'Пополнение':
                    return 'text-green';
                case 'Убыток':
                    return 'text-red';
                default:
                    return '';
            }
        },
      async changePage(newPage) {
        this.page = newPage;
        this.getAllTransactions();
      },
    },
    mounted() {
        this.$store.dispatch('auth/update');
        this.getAllTransactions();
    },
    computed: {

    }
}
</script>

<style>
.values {
    margin-left: 20px;
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