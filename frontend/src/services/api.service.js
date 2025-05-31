import axios from "axios";
import authHeader from "./auth-header";
import router from "../router";
import store from "../store";
import * as Yup from "yup";
import {Match} from "@/models/MatchModel";
import {Bet} from "@/models/BetModel";
import {Pokemon} from "@/models/PokemonModel";
import {Transaction} from "@/models/TransactionModel";
import {Country} from "@/models/CountryModel";
import {League} from "@/models/LeagueModel";

const API_URL = "http://localhost:8080/api/v1/";

axios.interceptors.response.use(
  function (response) {
    return response;
  },
  function (error) {
    if (error.response && error.response.status === 401) {
      router.push("/unauth");
      store.dispatch("auth/logout");
    }
    return Promise.reject(error);
  }
);

class ApiSerivce {
  async getMatchById(id) {
    try {
      const response = await axios.get(`${API_URL}matches/${id}`, {
        headers: authHeader(),
      });

      const validatedData = this.validateResponse(response.data, Match);
      if (!validatedData) {
        console.error(`Invalid data received for match ID ${id}`);
      }
      return response;
    } catch (error) {
      console.error("Error fetching match by ID:", error);
      throw error;
    }
  }
  async getMatches(page, pageSize, sort) {
    try {
      const response = await axios.get(`${API_URL}matches`, {
        headers: authHeader(),
        params: { page, size: pageSize, sort },
      });

      const validatedData = this.validateResponse(response.data.content, Yup.array().of(Match));
      if (!validatedData) {
        console.error("Invalid matches data received.");
      }
      return response;
    } catch (error) {
      console.error("Error fetching matches:", error);
      throw error;
    }
  }
  getMoney() {
    return axios.post(API_URL + "balance/free", {}, { headers: authHeader() });
  }
  makeBet(amount, ids) {
    const makeBetRequest = {
      amount: amount,
      match_events: ids,
    };
    return axios.post(API_URL + "bets", makeBetRequest, {
      headers: authHeader(),
    });
  }
  async getAllBets(page, pageSize, sort) {
    try {
      const response = await axios.get(`${API_URL}bets`, {
        headers: authHeader(),
        params: { page, size: pageSize, sort },
      });

      const validatedData = this.validateResponse(response.data.content, Yup.array().of(Bet));
      if (!validatedData) {
        console.error("Invalid data received.");
      }
      return response;
    } catch (error) {
      console.error("Error fetching bets:", error);
      throw error;
    }
  }
  async getPokemon(name) {
    try {
      const response = await axios.get(`${API_URL}pokemon/${name}`, {
        headers: authHeader(),
      });

      const validatedData = this.validateResponse(response.data, Pokemon);
      if (!validatedData) {
        console.error(`Invalid data received for pokemon name ${name}`);
      }
      return response;
    } catch (error) {
      console.error("Error fetching pokemon by name:", error);
      throw error;
    }
  }

  async getAllTransactions(page, pageSize, sort) {
    try {
      const response = await axios.get(`${API_URL}transaction`, {
        headers: authHeader(),
        params: { page, size: pageSize, sort },
      });

      const validatedData = this.validateResponse(response.data.content, Yup.array().of(Transaction));
      if (!validatedData) {
        console.error("Invalid data received.");
      }
      return response;
    } catch (error) {
      console.error("Error fetching transactions:", error);
      throw error;
    }
  }
  donateToAnt(amount) {
    const withdrawRequest = {
      amount: amount,
    };
    return axios.post(API_URL + "balance/withdraw", withdrawRequest, {
      headers: authHeader(),
    });
  }
  changeEventStatus(id, status) {
    const changeEventRequest = {
      id: id,
      status: status,
    };
    return axios.put(API_URL + "events/change", changeEventRequest, {
      headers: authHeader(),
    });
  }
  createCountry(name) {
    const createCountryRequest = {
      name: name,
    };
    return axios.post(API_URL + "country", createCountryRequest, {
      headers: authHeader(),
    });
  }
  createLeague(name, start_date, end_date) {
    const createLeagueRequest = {
      name: name,
      startDate: start_date,
      endDate: end_date
    };
    return axios.post(API_URL + "league", createLeagueRequest, {
      headers: authHeader(),
    });
  }
  addPlayer(name, health, damage, defense, countryId) {
    const createPokemonRequest = {
      name: name,
      health: health,
      damage: damage,
      defense: defense,
      countryId: countryId
    };
    return axios.post(API_URL + "pokemon", createPokemonRequest, {
      headers: authHeader(),
    });
  }
  addMatch(player1Id, player2Id, date, leagueId, countryId) {
    const createMatchRequest = {
      firstPlayerId: player1Id,
      secondPlayerId: player2Id,
      date: date,
      leagueId: leagueId,
      countryId: countryId
    };
    return axios.post(API_URL + "matches", createMatchRequest, {
      headers: authHeader(),
    });
  }
  addMatchEvent(matchId, name, coefficient) {
    const createMatchEventRequest = {
      matchId: matchId,
      name: name,
      coefficient: coefficient,
      status: "TBD"
    };
    return axios.post(API_URL + "events", createMatchEventRequest, {
      headers: authHeader(),
    });
  }
  async getAllPlayers() {
    try {
      const response = await axios.get(`${API_URL}pokemon`, {
        headers: authHeader(),
      });

      const validatedData = this.validateResponse(response.data, Yup.array().of(Pokemon));
      if (!validatedData) {
        console.error(`Invalid data received for pokemons`);
      }
      return response;
    } catch (error) {
      console.error("Error fetching pokemons:", error);
      throw error;
    }
  }
  async getAllCountries() {
    try {
      const response = await axios.get(`${API_URL}country`, {
        headers: authHeader(),
      });

      const validatedData = this.validateResponse(response.data, Yup.array().of(Country));
      if (!validatedData) {
        console.error(`Invalid data received for countries`);
      }
      return response;
    } catch (error) {
      console.error("Error fetching countries:", error);
      throw error;
    }
  }
  async getAllLeagues() {
    try {
      const response = await axios.get(`${API_URL}league`, {
        headers: authHeader(),
      });

      const validatedData = this.validateResponse(response.data, Yup.array().of(League));
      if (!validatedData) {
        console.error(`Invalid data received for leagues`);
      }
      return response;
    } catch (error) {
      console.error("Error fetching leagues:", error);
      throw error;
    }
  }
  async getRandomPokemonImage() {
    const randomId = Math.floor(Math.random() * 1010) + 1;
    const response =  await axios.get(`https://pokeapi.co/api/v2/pokemon/${randomId}`);
    return response.data.sprites.other["official-artwork"].front_default;
  }
  validateResponse(data, schema) {
    try {
      return schema.validateSync(data, { abortEarly: false, strict: true });
    } catch (err) {
      console.error("Validation Error:", err.errors);
      return null;
    }
  }
}

export default new ApiSerivce();
