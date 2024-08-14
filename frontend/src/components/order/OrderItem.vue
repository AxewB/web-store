<template>
  <VSheet
    class="pa-2 d-flex flex-column"
    rounded
  >
    <VSheet class="d-flex flex-row cursor-default">
      <VSheet class="flex-grow-1">
        <span class="text-h6 font-weight-bold mr-2">
          Заказ
        </span>
        <span class="text-body-1">
          №{{ props.order.id }}
        </span>
      </VSheet>
      <VSheet class="flex-grow-1 d-flex flex-row justify-end align-center">
        <span class="text-h6 font-weight-bold mr-2">
          Дата
        </span>
        <span class="text-body-1">
          {{ date }}
        </span>
      </VSheet>
    </VSheet>
    <VSheet>
      <OrderProductsList
        :products="props.order.orderItems"
        @on-product-click="goToProductPage"
      />
    </VSheet>
  </VSheet>
</template>

<script setup>
import { useOrderStore } from '@/stores/orderStore';
import { onMounted, computed } from 'vue';
import { useRouter } from 'vue-router';
import OrderProductsList from './OrderProductsList.vue';

// variables

const orderStore = useOrderStore();
const router = useRouter();

const props = defineProps({
  order: {
    type: Object,
    required: true
  }
})

// computed

const date = computed(() => {
  const d = new Date(props.order.orderDate)
  return `${d.getDate()} / ${d.getMonth() + 1} / ${d.getFullYear()}`
})

// methods

function goToProductPage(id) {
  router.push({name: 'productPage', params: {id: id}})
}

// lifecycle hooks

onMounted(() => {
  orderStore.fetchOrder(props.order.id)
})
</script>
