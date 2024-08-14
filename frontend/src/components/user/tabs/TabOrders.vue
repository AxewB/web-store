<template>
  <VSheet
    class="pa-2"
    rounded
  >
    <OrderList
      :orders="orders"
      @on-item-click="onItemClick"/>
  </VSheet >
</template>

<script setup>
import { computed, onMounted, ref } from 'vue';
import { useOrderStore } from '@/stores/orderStore';
import OrderList from '@/components/order/OrderList.vue';
const orderStore = useOrderStore();

const orders = computed(() => {
  return orderStore.orders
})

onMounted(async () => {
  await orderStore.fetchOrders()
})
</script>
