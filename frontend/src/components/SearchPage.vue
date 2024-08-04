

<template>
  <v-sheet class="d-flex flex-column"> 
    <AppHeader />
    <v-sheet></v-sheet>
  </v-sheet>
</template>

<script setup lang="ts">
import { computed, onMounted, ref } from 'vue';
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


const moveToProductPage = (id) => {
  router.push({path: '/product', query: { id }})
}

onMounted(async () => {
  productStore.setSkip(0);
  productStore.setLimit(30);
  await productStore.getProducts()

}) 

</script>