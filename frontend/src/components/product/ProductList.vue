<template>
  <v-sheet>
    
  </v-sheet>
</template>

<script setup>
import { computed, onMounted } from 'vue';
import { useProductStore } from '@/stores/productStore';

const productStore = useProductStore();

onMounted(() => {
  productStore.getProducts();
})
</script>
