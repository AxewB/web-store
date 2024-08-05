<template>
  <v-sheet class="bg-transparent">
    <AppHeader/>
    <PageHeading title="Каталог"/>
    <v-sheet class="bg-transparent d-flex flex-row">
      <v-sheet class="pa-2" min-width="250px" rounded>
        <CatalogueCategoryList @on-category-click="onCategoryClick"/>
      </v-sheet>
      <v-sheet class="flex-grow-1 ml-2 pa-2" rounded>
        <ProductList :products="products"/>
      </v-sheet>
    </v-sheet>
  </v-sheet>
</template>

<script setup>
import AppHeader from '@/components/AppHeader.vue'
import PageHeading from '@/components/PageHeading.vue'
import ProductList from '@/components/product/ProductList.vue'
import { onMounted, computed,ref, watch } from "vue"
import { useProductStore } from '@/stores/productStore';

const productStore = useProductStore();

const chosenCategory = ref({})
const products = computed(() => productStore.products)


function onCategoryClick(category) {
  chosenCategory.value = category
}

watch(chosenCategory, () => {
  productStore.fetchProductByCategory(chosenCategory.value.id)
})
onMounted(() => {
  productStore.limit = 30
  productStore.skip = 0
  productStore.fetchProductByCategory();
})
</script>
