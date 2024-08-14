<template>
  <VSheet class="d-flex flex-column bg-transparent">
    <AppHeader class="mb-1"/>
    <VSheet class="my-1 pa-2 bg-transparent text-overline d-flex align-center">
      <div
        v-for="category in productCategoryPath"
        :key="category.id + 'category'"
      >
        <span>/</span>
        <VBtn size="small" variant="plain" class="mx-2">
          {{category.name}}
        </VBtn>
      </div>
    </VSheet>
    <VSheet
      class="d-flex justify-center pa-4 my-1"
      rounded
      width="100%"
    >
      <VSheet
        class="pa-2 bg-transparent"
        elevation="2"
        rounded 
        border
        min-width="350px"
      >
        <ProductImageCarousel :images="product.images"/>
      </VSheet>

      <VSheet class="flex-grow-1 pa-2 mx-4 bg-transparent">
        <ProductShortInfo
          :product="product"
        />
      </VSheet>

      <VSheet min-width="300px" class="pa-2  bg-transparent" >
        <ProductActions
          :is-product-in-cart="isProductInCart"
          :product="product"
          @on-add-to-cart="addProductToCart"
          @on-remove-from-cart="removeProductFromCart"
          @on-go-to-cart="pushProductToCart"
        />
      </VSheet>
    </VSheet>
    <VSheet
      class="pa-4 d-flex flex-column"
      rounded
    >
      <span class="text-h5 font-weight-medium ">
        Описание
      </span>
      <span class="text-body-1">
        {{ product.description }}
      </span>
    </VSheet>
  </VSheet>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import { useProductStore } from '@/stores/productStore'
import { useCategoryStore } from '@/stores/categoryStore'
import {useCartStore } from '@/stores/cartStore'

import { useRoute, useRouter } from 'vue-router';
import AppHeader from "@/components/AppHeader.vue";
import ProductImageCarousel from './page/ProductImageCarousel.vue';
import ProductShortInfo from './page/ProductShortInfo.vue';
import ProductActions from './page/ProductActions.vue';

const route = useRoute()
const router = useRouter()
const product = ref({})
const productStore = useProductStore();
const categoryStore = useCategoryStore();
const cartStore = useCartStore();
const productCategoryPath = computed(() => categoryStore.categoryPath)

const isProductInCart = computed(() => {
  return cartStore.isProductInCart(product.value)
})

function addProductToCart() {
  cartStore.addToCart(product.value)
}

function removeProductFromCart() {
  cartStore.removeFromCart(product.value)
}

function pushProductToCart() {
  router.push({name: 'cart'})
}


onMounted(async () => {
  const id = route.params.id
  const { res } = await productStore.fetchProduct(id)
  product.value = res.data
  categoryStore.getCategoryPath(product.value.categories[0].id)
})
</script>
