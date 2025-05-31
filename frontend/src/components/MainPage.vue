<template>
  <div data-bs-theme="dark" class="position-absolute start-50 w-50 translate-middle-x">
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
    <div v-for="match in matches" :key="match.id">
      <router-link style="text-decoration: none; color: inherit;" :to="'/match/' + match.id">
        <div class="hovered d-flex flex-row justify-content-between align-items-center rounded-rectangle">
          <img v-if="match.randomPokemonImage1" :src="match.randomPokemonImage1" alt="Pokémon 1" style="height: 3em;" />
          <img v-else src="../assets/pikachu.png" alt="Loading Pokémon 1" style="height: 3em;" />
          <div style="width: 25%;" class="h4 text-center">{{ match.firstPlayerName }}</div>
          <div style="width: 25%;" class="h5 text-center">{{ match.date }}</div>
          <div style="width: 25%;" class="h4 text-center">{{ match.secondPlayerName }}</div>
          <img v-if="match.randomPokemonImage2" :src="match.randomPokemonImage2" alt="Pokémon 2" style="height: 3em;" />
          <img v-else src="../assets/pikachu.png" alt="Loading Pokémon 2" style="height: 3em;" />
        </div>
      </router-link>
    </div>
  </div>
</template>

<script>
import ApiService from "../services/api.service";
import apiService from "@/services/api.service";

export default {
  name: "Main",
  data() {
    return {
      matches: [],
      page: 0,
      pageSize: 5,
      totalPages: 1,
      sort: "date,asc",
    };
  },
  methods: {
    async getMatches() {
      try {
        const response = await ApiService.getMatches(this.page, this.pageSize, this.sort);
        const data = response.data;
        this.totalPages = data.totalPages;

        this.matches = data.content.map((match) => ({
          ...match,
          randomPokemonImage1: null,
          randomPokemonImage2: null,
        }));

        this.loadPokemonImages();
      } catch (error) {
        console.error("Error fetching matches:", error);
      }
    },
    async loadPokemonImages() {
      const imagePromises = this.matches.map(async (match) => {
        try {
          match.randomPokemonImage1 = await this.getRandomPokemonImage();
          match.randomPokemonImage2 = await this.getRandomPokemonImage();
        } catch (error) {
          console.error(`Error loading images for match ${match.id}:`, error);
        }
      });

      await Promise.all(imagePromises);
    },
    async getRandomPokemonImage() {
      try {
        return await apiService.getRandomPokemonImage();
      } catch (error) {
        console.error("Error fetching Pokémon image:", error);
        return "";
      }
    },
    async changePage(newPage) {
      this.page = newPage;
      await this.getMatches();
    },
  },
  async mounted() {
    await this.getMatches();
  }
};
</script>

<style scoped>
.rounded-rectangle {
  margin-top: 30px;
  background-color: #262630;
  border-radius: 10px;
  padding: 10px;
}

.hovered {
  transition: 0.5s box-shadow;
}

.hovered:hover {
  box-shadow: 0 5px 35px 0px white;
}

@keyframes clockwise {
  0% {
    top: -5px;
    left: 0;
  }
  12% {
    top: -2px;
    left: 2px;
  }
  25% {
    top: 0;
    left: 5px;
  }
  37% {
    top: 2px;
    left: 2px;
  }
  50% {
    top: 5px;
    left: 0;
  }
  62% {
    top: 2px;
    left: -2px;
  }
  75% {
    top: 0;
    left: -5px;
  }
  87% {
    top: -2px;
    left: -2px;
  }
  100% {
    top: -5px;
    left: 0;
  }
}

@keyframes counterclockwise {
  0% {
    top: -5px;
    right: 0;
  }
  12% {
    top: -2px;
    right: 2px;
  }
  25% {
    top: 0;
    right: 5px;
  }
  37% {
    top: 2px;
    right: 2px;
  }
  50% {
    top: 5px;
    right: 0;
  }
  62% {
    top: 2px;
    right: -2px;
  }
  75% {
    top: 0;
    right: -5px;
  }
  87% {
    top: -2px;
    right: -2px;
  }
  100% {
    top: -5px;
    right: 0;
  }
}

button {
  margin-top: 8px;
}
</style>
