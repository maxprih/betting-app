<template>
  <div class="jumbotron jumbotron-fluid">
    <div class="container text-center">
      <h1 class="display-4 animated-text">VT BETS</h1>
    </div>
  </div>
</template>

<script>
export default {
  name: "Home"
};
</script>

<style scoped>
.jumbotron {
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  color: white;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.3);
}

.container {
  max-width: 600px;
  text-align: center;
}

.display-4 {
  font-size: 4rem;
  font-weight: bold;
  margin-bottom: 20px;
}

.lead {
  font-size: 1.25rem;
  font-weight: 300;
}

.animated-text {
  animation: fadeInUp 2s ease-in-out;
}

@keyframes fadeInUp {
  0% {
    opacity: 0;
    transform: translateY(30px);
  }
  100% {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>
