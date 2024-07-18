<template>
  <v-sheet class="d-flex flex-column">
    <AppHeader/>
    <v-container width="100%" class="bg-red">
    <v-row no-gutters>
      <v-col sm="4">
        <v-sheet class="ma-2 pa-2">
          One of three columns
        </v-sheet>
      </v-col>
      <v-spacer></v-spacer>
      <v-col  sm="4">
        <v-sheet class="ma-2 pa-2">
          One of three columns
        </v-sheet>
      </v-col>
      <v-spacer></v-spacer>
      <v-col width="100%" class="bg-red" sm="4">
        <v-sheet class="ma-2 pa-2">
          One of three columns
        </v-sheet>
      </v-col>
    </v-row>
  </v-container>
    {{ product }}
  </v-sheet>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import {useProductStore} from '@/stores/productStore'
import { useRoute } from 'vue-router';
import AppHeader from "@/components/AppHeader.vue";

const route = useRoute()
const product = ref({})
const productStore = useProductStore();
onMounted(async () => {
  const id = route.params.id
  product.value = await productStore.getProduct(id)
})
</script>
