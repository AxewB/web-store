<template>
  <VSheet>
    <PageHeader />
    <ProductList @on-card-click="moveToProductPage"/>
  </VSheet>
</template>

<script setup>
import {onMounted } from 'vue';
import { useProductStore } from '@/stores/productStore';
import PageHeader from '@/components/AppHeader.vue'
import ProductList from '@/components/product/ProductList.vue';
import { useRouter } from 'vue-router';
const router = useRouter();
const productStore = useProductStore();

function moveToProductPage(id) {
  router.push({name: 'productPage', params: {id: id}})
}

onMounted(() => {
  productStore.limit = 30;
  productStore.fetchProducts();
})
</script>
