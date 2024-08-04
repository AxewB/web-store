
<template>
  <v-sheet v-if="props.products.length" class="d-flex flex-column bg-transparent">
    <v-sheet 
      v-for="(product, index) in products"
      :key="product.id">
      <CartItem
        :product="product"
      ></CartItem>
      <VDivider v-if="index < products.length - 1" class="my-1"/>
    </v-sheet>
  </v-sheet>
  <EmptyWarning 
    v-else
    title="Ваша корзина пуста"
    description="Добавьте что-нибудь в корзину"/>
</template>

<script setup>
import CartItem from './CartItem.vue';
import { computed } from 'vue'
import EmptyWarning from '@/components/EmptyWarning.vue';
const props = defineProps({
  products: {
    type: Array,
    required: true
  }
})

const products = computed(() => props.products)
</script>
