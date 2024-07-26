<template>
  <v-sheet class="d-flex flex-column bg-transparent">
    <AppHeader class="mb-1"/>
    <v-sheet class="my-1 pa-2 bg-transparent text-overline d-flex align-center">
      <v-div  v-for="category in productCategoryPath" :key="category.id + 'category'">
        <span>/</span>
        <v-btn size="small" variant="plain" class="mx-2">
          {{category.name}}
        </v-btn>
      </v-div>
      
    </v-sheet>
    <v-sheet class="d-flex justify-center pa-4 my-1" rounded width="100%" >
      <v-sheet elevation="2" rounded class="pa-2 bg-transparent" border min-width="350px">
        <ProductImageCarousel :images="product.images"/>
      </v-sheet>

      <v-sheet class="flex-grow-1 pa-2 mx-4 bg-transparent">
        <ProductShortInfo :product="product" @on-open-description=""/>
      </v-sheet>

      <v-sheet min-width="300px" class="pa-2  bg-transparent" >
        <ProductActions :product="product" @on-add-to-cart="addProductToCart"/>
      </v-sheet>
    </v-sheet>
    <v-sheet class="pa-4 d-flex flex-column" rounded >
      <span class="text-h5 font-weight-medium ">Описание</span>
      <span class="text-body-1"> {{ product.description }} </span>
    </v-sheet>
   
  </v-sheet>
</template>

<script setup>
import { computed, onMounted, ref, watch } from 'vue'
import { useProductStore } from '@/stores/productStore'
import { useCategoryStore } from '@/stores/categoryStore'

import { useRoute } from 'vue-router';
import AppHeader from "@/components/AppHeader.vue";
import ProductImageCarousel from './page/ProductImageCarousel.vue';
import ProductShortInfo from './page/ProductShortInfo.vue';
import ProductActions from './page/ProductActions.vue';

const route = useRoute()
const product = ref({})
const productStore = useProductStore();
const categoryStore = useCategoryStore();

const productCategoryPath = computed(() => categoryStore.categoryPath)

function addProductToCart() {
  alert("add product to cart") // TODO: add product to cart
}

// watch(product, () => {
  
// })

onMounted(async () => {
  const id = route.params.id
  product.value = await productStore.getProduct(id)
  categoryStore.getCategoryPath(product.value.categories[0].id)
  
})

</script>
