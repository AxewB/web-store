
<template>
  <VSheet v-if="products.length" class="d-flex flex-column bg-transparent">
    <VSheet
      v-for="(product, index) in products"
      :key="product.id">
      <CartItem :product="product"/>
      <VDivider v-if="index < products.length - 1" class="my-1"/>
    </VSheet>
  </VSheet>
  <EmptyWarning
    v-else
    title="Ваша корзина пуста"
    description="Добавьте что-нибудь в корзину"
  />
</template>

<script setup>
import CartItem from './CartItem.vue';
import { computed } from 'vue'
import EmptyWarning from '@/components/EmptyWarning.vue';

// variables

const props = defineProps({
  products: {
    type: Array,
    required: true
  }
})

// computed

const products = computed(() => props.products)
</script>
