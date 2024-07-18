<template>
  <v-card width="200px" rounded class="ma-4 pa-2 d-flex flex-column" elevation="10" border @click="goToProductPage()">
    <v-img :src="props.product.thumbnail"></v-img>

    <VDivider />
    <span class="text-h6 my-1">
      {{ props.product.price }}$
    </span>
    <span class="my-1">
      {{ props.product.title }}
    </span>
    <v-rating class="my-1" readonly density="compact" size="small" color="orange" v-model="props.product.rating"></v-rating>
  </v-card>
  
</template>

<script setup>
import { defineProps } from 'vue'
import {useRouter} from 'vue-router'

const props = defineProps(['product'])
const router = useRouter()

const goToProductPage = () => {
  router.push(`/product/${props.product.id}`)
}
</script>