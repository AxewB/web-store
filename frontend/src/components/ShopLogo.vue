<template>
  <v-sheet 
    class="d-flex justify-center bg-transparent align-center rounded text-h4 pa-2 cursor-pointer"
    width="175px"
    @click="router.push({name: 'home'})"
  >
    <span class="logo-font text-light-blue-accent-4">
      S H O P
    </span>
  </v-sheet>
</template>

<script setup>
import { useRouter } from 'vue-router';

const router = useRouter();
</script>

<style lang="scss" scoped>
@import url('https://fonts.googleapis.com/css2?family=Archivo+Black&display=swap');

.logo-font {
  font-family: "Archivo Black", sans-serif;
  font-weight: 400;
  font-style: normal;
}
</style>