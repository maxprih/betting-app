export const events = {
  namespaced: true,
  state: {
    selectedEvents: [],
  },
  actions: {
    addEvent({ commit }, matchEvent) {
      commit("addEvent", matchEvent);
    },

    removeEvent({ commit }, matchEvent) {
      commit("removeEvent", matchEvent);
    },
    clearAll({ commit }) {
      commit("clearAll");
    },
  },
  mutations: {
    addEvent(state, matchEvent) {
      state.selectedEvents.push(matchEvent);
    },
    removeEvent(state, matchEvent) {
      state.selectedEvents = state.selectedEvents.filter(
        (event) => event.id !== matchEvent.id
      );
    },
    clearAll(state) {
      state.selectedEvents = [];
    },
  },
  getters: {
    getSelectedEvents: (state) => state.selectedEvents,
  },
};
