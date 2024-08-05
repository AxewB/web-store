<template>
  <v-app >
    <v-main class="d-flex flex-row justify-center"
    >
    <v-sheet width="1500px" class="bg-transparent">
      <router-view class="pa-2"/>
    </v-sheet>
    </v-main>
  </v-app>
</template>

<script setup>
import { onMounted } from 'vue';
import { useProductStore } from '@/stores/productStore';
import { useCartStore } from './stores/cartStore';
const productStore = useProductStore();
const cartStore = useCartStore();

onMounted(async () => {
  productStore.limit = 30;
  const res = await productStore.fetchProducts();
  // cartStore.getCart();
})
</script>
