<template>
  <div data-bs-theme="dark" class="modal fade" id="modal1" tabindex="-1" aria-labelledby="modal1label" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header text-center">
          <h1 class="modal-title w-100" id="modal2label">
            <img :src="pokemon1Image" alt="" style="height: 4em;"> {{ pokemon1.name }}
          </h1>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <ul class="list-group h3 text-center mx-auto" style="max-width: 400px;">
            <li class="list-group-item d-flex justify-content-between align-items-center">
              <strong>Здоровье:</strong>
              <span>{{ pokemon1.health }}<font-awesome-icon class="ms-2" icon="heartbeat" /></span>
            </li>
            <li class="list-group-item d-flex justify-content-between align-items-center">
              <strong>Атака:</strong>
              <span>{{ pokemon1.damage }}<font-awesome-icon class="ms-2" icon="fist-raised" /></span>

            </li>
            <li class="list-group-item d-flex justify-content-between align-items-center">
              <strong>Защита:</strong>
              <span>{{ pokemon1.defense }}<font-awesome-icon class="ms-2" icon="shield-alt" /></span>
            </li>
            <li class="list-group-item d-flex justify-content-between align-items-center">
              <strong>Страна:</strong>
              <span>{{ pokemon1.countryName }}<font-awesome-icon class="ms-2" icon="globe" /></span>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
  <div data-bs-theme="dark" class="modal fade" id="modal2" tabindex="-1" aria-labelledby="modal2label" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header text-center">
          <h1 class="modal-title w-100" id="modal2label">
            <img :src="pokemon2Image" alt="" style="height: 4em;"> {{ pokemon2.name }}
          </h1>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <ul class="list-group h3 text-center mx-auto" style="max-width: 400px;">
            <li class="list-group-item d-flex justify-content-between align-items-center">
              <strong>Здоровье:</strong>
              <span>{{ pokemon2.health }}<font-awesome-icon class="ms-2" icon="heartbeat" /></span>
            </li>
            <li class="list-group-item d-flex justify-content-between align-items-center">
              <strong>Атака:</strong>
              <span>{{ pokemon2.damage }}<font-awesome-icon class="ms-2" icon="fist-raised" /></span>

            </li>
            <li class="list-group-item d-flex justify-content-between align-items-center">
              <strong>Защита:</strong>
              <span>{{ pokemon2.defense }}<font-awesome-icon class="ms-2" icon="shield-alt" /></span>
            </li>
            <li class="list-group-item d-flex justify-content-between align-items-center">
              <strong>Страна:</strong>
              <span>{{ pokemon2.countryName }}<font-awesome-icon class="ms-2" icon="globe" /></span>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>

  <div class="position-absolute start-50 w-50 translate-middle-x">
        <div class="d-flex flex-row justify-content-between align-items-center rounded-rectangle text-center">
            <img :src="pokemon1Image" alt="" style="height: 4em;">
            <div class="h2" style="width: 25%;">
                <span class="hovered-text" data-bs-toggle="modal" data-bs-target="#modal1">{{ match.firstPlayerName
                }}</span>
            </div>
            <div class="h3" style="width: 25%;">{{ match.date }}</div>
            <div class="h2" style="width: 25%;">
                <span class="hovered-text" data-bs-toggle="modal" data-bs-target="#modal2">{{ match.secondPlayerName
                }}</span>
            </div>
            <img :src="pokemon2Image" alt="" style="height: 4em;">
        </div>

        <div>
            <template v-for="(row, index) in groupedMatchEvents" :key="index">
                <div class="d-flex flex-column justify-content-around align-items-center rounded-rectangle">
                    <template v-for="matchEvent in row" :key="matchEvent.id">
                        <input type="checkbox" :id="'btncheck' + matchEvent.id" class="btn-check" autocomplete="off"
                            @click="toggleCheckbox(matchEvent)" :checked="isSelected(matchEvent)" />
                        <label style="margin-top: 30px;" :for="'btncheck' + matchEvent.id"
                            class="btn btn-coeffs text-white hovered">
                            {{ matchEvent.name }} <br /> {{ matchEvent.coefficient }}
                            <div v-if="isAdmin">
                                <p>Текущий статус: {{ matchEvent.status }}</p>
                                <div class="d-flex flex-row align-items-center justify-content-around">
                                    <label>
                                        <input checked type="radio" :id="'tbdRadio' + matchEvent.id"
                                            v-model="matchEvent.status" value="TBD"
                                            @change="handleStatusChange(matchEvent)" />
                                        TBD
                                    </label>
                                    <label>
                                        <input type="radio" :id="'winRadio' + matchEvent.id" v-model="matchEvent.status"
                                            value="WIN" @change="handleStatusChange(matchEvent)" />
                                        Win
                                    </label>
                                    <label>
                                        <input type="radio" :id="'loseRadio' + matchEvent.id" v-model="matchEvent.status"
                                            value="LOSE" @change="handleStatusChange(matchEvent)" />
                                        Lose
                                    </label>
                                </div>

                                <button style="margin-top:10px;" @click="changeEventStatus(matchEvent)"
                                    class="btn btn-outline-success" type="button" id="button-addon2">Изменить
                                    статус</button>
                            </div>
                        </label>
                    </template>
                </div>
            </template>
        </div>
    </div>
</template>
<script>
import ApiSerivce from "../services/api.service";
import apiService from "../services/api.service";

export default {
    name: "Match",
    data() {
        return {
            match_id: this.$route.params.id,
            match: "",
            pokemon1: "",
            pokemon2: "",
            match_events: [],
            chunkedMatchEvents: [],
            elementsPerRow: 3,
            pokemon1Image: "",
            pokemon2Image: ""
        };
    },
    methods: {
        getMatch() {
            ApiSerivce.getMatchById(this.match_id)
                .then(response => {
                    this.match = response.data;
                    this.match_events = this.match.matchEvents;
                })
        },
        toggleCheckbox(matchEvent) {
            if (this.isSelected(matchEvent)) {
                this.$store.dispatch('events/removeEvent', matchEvent);
            } else {
                this.$store.dispatch('events/addEvent', matchEvent);
            }
        },
        isSelected(matchEvent) {
            return this.selectedEvents.some((event) => event.id === matchEvent.id);
        },
        getPokemons() {
            ApiSerivce.getPokemon(this.match.firstPlayerName)
                .then(response => {
                    this.pokemon1 = response.data
                })
            ApiSerivce.getPokemon(this.match.secondPlayerName)
                .then(response => {
                    this.pokemon2 = response.data
                })
        },
        changeEventStatus(matchEvent) {
            ApiSerivce.changeEventStatus(matchEvent.id, matchEvent.status)
        },
        async getRandomPokemonsImage() {
          try {
            this.pokemon1Image = await apiService.getRandomPokemonImage();
            this.pokemon2Image = await apiService.getRandomPokemonImage();
          } catch (error) {
            console.error(error);
          }
        }
    },
    computed: {
        selectedEvents() {
            return this.$store.getters['events/getSelectedEvents'];
        },
        groupedMatchEvents() {
            const rows = [];
            for (let i = 0; i < this.match_events.length; i += this.elementsPerRow) {
                rows.push(this.match_events.slice(i, i + this.elementsPerRow));
            }
            return rows;
        },
        isAdmin() {
            return (this.$store.state.auth.user.roles).some(role => role.roleName === "ROLE_ADMIN");
        }
    },
    mounted() {
        this.getMatch();
        setTimeout(() => {
            this.getPokemons();
        }, 500);

        this.getRandomPokemonsImage();
    },
};
</script>
<style scoped>
.rounded-rectangle {
    margin-top: 30px;
    background-color: #262630;
    border-radius: 10px;
    padding: 10px;

}

li>button {
    margin-left: 100px;
}

.btn-coeffs {
    background-color: #3f3f4c !important;
    width: 50%;
}

.hovered {
    transition: 0.5s box-shadow;
}

.hovered:hover {
    box-shadow: 0 5px 35px 0px white;
}

.hovered-text {
    transition: 0.5s box-shadow;
}

.hovered-text:hover {
    box-shadow: 0 5px 15px 0px red;
}
</style>