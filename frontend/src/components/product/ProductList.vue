<template>
  <v-sheet class="d-flex flex-row flex-wrap justify-center">
    <ProductCard v-for="product in products" :key="product.id" :product="product"/>
  </v-sheet>
</template>

<script setup>
import { onMounted, computed } from 'vue';
import { useProductStore } from '@/stores/productStore';
import ProductCard from "@/components/product/ProductCard.vue";
const productStore = useProductStore();

const products = computed(() => {
  return productStore.products["products"];
})

onMounted(async() => {
  // let products = '';
  // for (let i = 0; i < 40; i++) {
  //   const {id, price, description, title, stock, images, thumbnail} = dummyProducts['products'][i]
  //   let images_str = ""
  //   for (let i = 0; i < images.length; i++) {
  //     images_str += `\"${images[i]}\"${ i < images.length - 1 ? ',' : ''}`
  //   }
  //   images_str += ""
  //   products += `(${id}, ${price}, "${description}", "${title}", ${stock}, '{${images_str}}', "${thumbnail}"),\n`
  // }
  // console.log(products)





  // console.log(JSON.stringify(dummyProducts['products']))
  // let images = `'`
  // let requests = ''
  // // images += `'`;
  // // console.log(`(${product.id}, ${product.price}, ${product.description}, ${product.title}, ${product.stock}, ${images}, ${product.thumbnail})`)
  // for (const product in dummyProducts) {
    
  //   for (let i = 0; i < product.images.length; i++) {
  //     console.log(product.images[i])
  //     images += `{${product.images[i]}}${i < product.images.length - 1 ? ',' : ''}`
  //   }
  //   requests += `(${product.id}, ${product.price}, ${product.description}, ${product.title}, ${product.stock}, ${product.images}, ${product.thumbnail})\n`

  // }
  // console.log(requests)
  
  productStore.getProducts();
})
</script>
