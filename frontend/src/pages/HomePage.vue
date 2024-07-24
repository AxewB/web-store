<template>
  <v-sheet>
    <PageHeader />
    <ProductList @on-card-click="moveToProductPage"/>
  </v-sheet>
</template>

<script setup>
import PageHeader from '@/components/AppHeader.vue'
import ProductList from '@/components/product/ProductList.vue';
import { useProductStore } from '@/stores/productStore';
import { onMounted } from 'vue';
import { useRouter } from 'vue-router';
const productStore = useProductStore();
const router = useRouter();

function moveToProductPage(id) {
  router.push({name: 'productPage', params: {id: id}})
}

onMounted(() => {
  productStore.limit = 30;
  productStore.getProducts();
})

</script>
