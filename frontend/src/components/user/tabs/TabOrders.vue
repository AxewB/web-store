<template>
  <v-sheet class="pa-2" rounded>
    Order history
    
  </v-sheet >
</template>

<script setup>
import { onUpdated } from 'vue';

onUpdated(() => {
  console.log('mounting')
})
</script>
