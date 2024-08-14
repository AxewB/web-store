<template>
  <VSheet v-if="orders.length" class="pa-2" rounded>
    <VSheet
      v-for="(order, index) in orders"
      :key="order.id"
    >
    <OrderItem
      :order="order"
      @on-item-click="onItemClick"
    />
    <VDivider v-if="index < orders.length - 1" />
    </VSheet>
  </VSheet>

  <EmptyWarning
    v-else
    title="Заказы отсутствуют"
    description="Создайте заказ"
    action="Перейти в корзину"
    @click="goToCart()"
  />
</template>

<script setup>
import OrderItem from './OrderItem.vue';
import EmptyWarning from '../EmptyWarning.vue';
import { useRouter } from 'vue-router';

// variables

const router = useRouter();

const props = defineProps({
  orders: {
    type: Array,
    required: true
  }
})
const emit = defineEmits(['on-item-click'])

// methods

function onItemClick() {
  emit('on-item-click')
}

function goToCart() {
  router.push({name: 'cart'})
}
</script>