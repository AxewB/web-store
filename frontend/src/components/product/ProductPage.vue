<template>
  <v-sheet>
asd
{{ product }}
<!-- {{ productStore.product }} -->
  </v-sheet>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import {useProductStore} from '@/stores/productStore'
import { useRoute } from 'vue-router';

const route = useRoute()
const product = ref({})
const productStore = useProductStore();
// const product = computed(() => {
//   return productStore.product;
// })
onMounted(async () => {
  const id = route.params.id
  product.value = await productStore.getProduct(id)
})
</script>
