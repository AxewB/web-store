<template>
  <v-sheet class="d-flex flex-column">
    <v-sheet>
      <CartItem
        v-for="(product, key, index) in products"
        :key="product.id"
        :product="product"
        :class="index < products.length - 1 ? 'mb-2' : ''"
      ></CartItem>
    </v-sheet>
  </v-sheet>
</template>

<script setup>
import CartItem from './CartItem.vue';
import { computed } from 'vue'
const props = defineProps({
  products: {
    type: Array,
    required: true
  }
})

const products = computed(() => props.products)
</script>
