<template>
  <v-sheet class="d-flex flex-row flex-wrap justify-center">
    <ProductCard v-for="product in products" :key="product.id" :product="product"/>
  </v-sheet>
</template>

<script setup>
import { onMounted, computed } from 'vue';
import { useProductStore } from '@/stores/productStore';
import ProductCard from "@/components/product/ProductCard.vue";
const productStore = useProductStore();

const products = computed(() => {
  return productStore.products["products"];
})

onMounted(async() => {
  productStore.getProducts();
})
</script>
