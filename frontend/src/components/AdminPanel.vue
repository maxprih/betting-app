<template>
  <div data-bs-theme="dark"  class="position-absolute start-50 w-75 translate-middle-x">
    <div class="d-flex flex-row justify-content-between align-items-center rounded-rectangle text-center">
      <input class="form-control w-25 ms-2" style="background-color: #262630;" v-model="countryName" type="text" placeholder="Enter country name">
      <button class="btn btn-outline-secondary w-auto ms-5" type="button" @click="createCountry">Add country</button>
    </div>

    <div class="d-flex flex-row justify-content-between align-items-center rounded-rectangle text-center">
      <input class="form-control w-25 ms-2" style="background-color: #262630;" v-model="leagueName" type="text" placeholder="Enter league name">
      <input type="datetime-local" style="background-color: #262630;" class = "form-control w-auto ms-2" v-model="league_start_date">
      <input type="datetime-local" style="background-color: #262630;" class = "form-control w-auto ms-2" v-model="league_end_date">
      <button class="btn btn-outline-secondary w-auto ms-5" type="button" @click="createLeague">Add league</button>
    </div>

    <div class="d-flex flex-row justify-content-between align-items-center rounded-rectangle text-center">
      <input class="form-control ms-2" style="background-color: #262630; width: 12%" v-model="playerName" type="text" placeholder="Enter name">
      <input class="form-control ms-2" style="background-color: #262630; width: 15%" v-model="playerHealth" type="number" placeholder="Enter health">
      <input class="form-control ms-2" style="background-color: #262630; width: 15%" v-model="playerDamage" type="number" placeholder="Enter damage">
      <input class="form-control ms-2" style="background-color: #262630; width: 15%" v-model="playerDefense" type="number" placeholder="Enter defense">
      <select id="beb1" required class="form-select w-auto ms-2" style="background-color: #262630;" v-model="playerCountryId">
        <option value="" disabled hidden>Select country</option>
        <option v-for="country in countries" :value="country.id" :key="country.id">{{ country.name }}</option>
      </select>
      <button class="btn btn-outline-secondary w-auto" type="button" @click="createPlayer">Add player</button>
    </div>

    <div class="d-flex flex-row justify-content-between align-items-center rounded-rectangle text-center">
      <select id="beb2" required class="form-select ms-2" style="background-color: #262630; width: 15%" v-model="player1id">
        <option value="" disabled hidden>Select Player 1</option>
        <option v-for="player1 in players" :value="player1.id" :key="player1.id">{{ player1.name }}</option>
      </select>
      <select required class="form-select ms-2" style="background-color: #262630; width: 15%" v-model="player2id">
        <option value="" disabled hidden>Select Player 2</option>
        <option v-for="player2 in players" :value="player2.id" :key="player2.id">{{ player2.name }}</option>
      </select>
      <input type="datetime-local" style="background-color: #262630;" class = "form-control w-auto ms-2" v-model="match_date">
      <select required class="form-select ms-2" style="background-color: #262630; width: 15%" v-model="matchCountryId">
        <option value="" disabled selected hidden>Select country</option>
        <option v-for="country in countries" :value="country.id" :key="country.id">{{ country.name }}</option>
      </select>
      <select required class="form-select ms-2" style="background-color: #262630; width: 15%" v-model="matchLeagueId">
        <option value="" disabled selected hidden>Select league</option>
        <option v-for="league in leagues" :value="league.id" :key="league.id">{{ league.name }}</option>
      </select>
      <button class="btn btn-outline-secondary w-auto ms-5" type="button" @click="createMatch">Add match</button>
    </div>

    <div class="d-flex flex-row justify-content-between align-items-center rounded-rectangle text-center">
      <select class="form-select ms-2" style="background-color: #262630; width: 15%" v-model="matchid">
        <option value="" disabled selected hidden>Select match</option>
        <option v-for="match in matches" :key="match.id" :value="match.id">{{match.firstPlayerName}} VS {{match.secondPlayerName }}</option>
      </select>
      <input class="form-control w-auto ms-2" style="background-color: #262630;" v-model="event_name" type="text" placeholder="Event name">
      <input class="form-control w-auto ms-2" style="background-color: #262630;" v-model.number="coefficient" type="number" placeholder="Enter coefficient">
      <button class="btn btn-outline-secondary w-auto ms-5" type="button"  @click="createEvent">Add event</button>
    </div>
  </div>
</template>

<script>
import apiService from "../services/api.service"

export default {
  name: "Admin",
  data() {
    return {
      playerName: '',
      players: [],
      countries: [],
      leagues: [],
      player1id: '',
      player2id: '',
      match_date: null,
      matchid: '',
      matches: [],
      coefficient: null,
      event_name: null,
      countryName: '',
      leagueName: '',
      league_start_date: null,
      league_end_date: null,
      playerHealth: '',
      playerDefense: '',
      playerDamage: '',
      playerCountryId: '',
      matchLeagueId: '',
      matchCountryId: ''
    }
  },

  mounted() {
    this.getPlayers()
    this.getMatches()
    this.getLeagues()
    this.getCountries()
  },
  methods: {
    createCountry() {
      apiService.createCountry(this.countryName)
      this.countryName = ''
      setTimeout(() => {
        this.getCountries();
      }, 500);
    },
    createLeague() {
      apiService.createLeague(this.leagueName, this.league_start_date, this.league_end_date)
      this.leagueName = ''
      this.league_start_date = null;
      this.league_end_date = null;
      setTimeout(() => {
        this.getLeagues();
      }, 500);
    },
    createPlayer() {
      apiService.addPlayer(this.playerName, this.playerHealth, this.playerDamage, this.playerDefense, this.playerCountryId)
      this.playerName = ''
      this.playerHealth = ''
      this.playerDamage = ''
      this.playerDefense = ''
      this.playerCountryId = null;
      setTimeout(() => {
        this.getPlayers();
      }, 500);
    },
    createMatch() {
      apiService.addMatch(this.player1id, this.player2id, this.match_date, this.matchLeagueId, this.matchCountryId)
      this.player1id = null;
      this.player2id = null;
      this.match_date = null;
      this.matchLeagueId = null;
      this.matchCountryId = null;
      setTimeout(() => {
        this.getMatches();
      }, 500);
    },
    getCountries() {
      apiService.getAllCountries()
          .then(response => {
            this.countries = response.data;
          })
    },
    getLeagues() {
      apiService.getAllLeagues()
          .then(response => {
            this.leagues = response.data;
          })
    },
    getPlayers() {
      apiService.getAllPlayers()
          .then(response => {
            this.players = response.data;
          })
    },
    getMatches() {
      apiService.getMatches()
          .then(response => {
            this.matches = response.data.content;
          })
    },
    createEvent() {
      apiService.addMatchEvent(this.matchid, this.event_name, this.coefficient);
      this.matchid = '';
      this.event_name = '';
      this.coefficient = '';
    }
  },
}
</script>

<style>
.rounded-rectangle {
  margin-top: 30px;
  background-color: #262630;
  border-radius: 10px;
  padding: 10px;

}
</style>