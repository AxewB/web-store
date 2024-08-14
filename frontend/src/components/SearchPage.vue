

<template>
  <VSheet class="d-flex flex-column">
    <AppHeader/>
    <VSheet>
      <ProductList @on-card-click="moveToProductPage" />
    </VSheet>
  </VSheet>
</template>

<script setup lang="ts">
import { computed, onMounted, ref, watch } from 'vue';
import AppHeader from './AppHeader.vue';
import {useRoute, useRouter} from 'vue-router';
import { useProductStore} from '@/stores/productStore';

const productStore = useProductStore();
const router = useRouter();
const route = useRoute();

const products = computed(() => productStore.products)

const search = computed(() => {
  return route.query.search
})

watch(search, (value) => {
  productStore.fetchProductsByName(value)
})


const moveToProductPage = (id) => {
  router.push({path: '/product', query: { id }})
}



onMounted(async () => {
  productStore.skip = 0;
  productStore.limit = 20;

  await productStore.fetchProductsByName(search.value)
})

</script>