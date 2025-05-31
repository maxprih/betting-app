import { createStore } from "vuex";
import { auth } from "./auth.module";
import { events } from "./events.module";
import createPersistedState from "vuex-persistedstate";

const store = createStore({
  plugins: [
    createPersistedState({
      storage: window.localStorage,
    }),
  ],
  modules: {
    auth: auth,
    events: events,
  },
});

export default store;
