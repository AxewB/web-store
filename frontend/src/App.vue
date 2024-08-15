<template>
  <v-app>
    <v-main class="d-flex flex-row justify-center">
      <VSheet width="1500px" class="bg-transparent">
        <router-view class="pa-2" />
      </VSheet>
    </v-main>

    <!-- TODO: move to external component -->
    <VOverlay v-model="requestStore.loading.value" scrim="#00000000" class="d-flex justify-center align-center bg-transparent">
      <VSheet v-if="requestStore.loading.value" width="100px" height="100px" rounded class="bg-transparent d-flex justify-center align-center">
        <VOverlay contained v-model="requestStore.loading.value" rounded class="d-flex justify-center align-center">
          <VProgressCircular indeterminate width="8" size="60" />
        </VOverlay>
      </VSheet>
    </VOverlay>
    <!-- TODO: move to external component -->
    <v-snackbar
      v-model="requestStore.snackbar.show"
      :color="requestStore.snackbar.color"
      variant="flat"
      :timeout="3000"
    >
      {{ requestStore.snackbar.text }}

      <template v-slot:actions>
        <v-btn
          variant="text"
          @click="requestStore.snackbar = false"
        >
          Close
        </v-btn>
      </template>
    </v-snackbar>
  </v-app>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import { useProductStore } from '@/stores/productStore';
import { useCartStore } from './stores/cartStore';
import { useRequestStore } from './stores/requestStore';
const productStore = useProductStore();
const cartStore = useCartStore();
const requestStore = useRequestStore();

const isLoading = ref(false)

onMounted(async () => {
  productStore.limit = 30;
  productStore.fetchProducts();
  cartStore.getCart();
})
</script>
