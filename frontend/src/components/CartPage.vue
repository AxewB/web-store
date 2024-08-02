<template>
  <v-sheet>
    <PageHeader/>
    <span class="text-h5 font-weight-medium">Cart</span>

    <v-sheet class="d-flex flex-row pt-2" rounded>

      <v-sheet class="flex-grow-1 d-flex flex-column mr-2 pa-2" rounded>
        <CartItemList :products="cart.products"/>
      </v-sheet>
      <v-sheet width="300px">
        <v-sheet class="d-flex flex-column pa-2" border  rounded>
          <span class="text-h5 font-weight-medium">Checkout</span>
          <span class="text-body-1"> {{ totalCost }}$ </span>
          <v-btn class="mt-2" color="primary" @click="checkout()">Checkout</v-btn>
        </v-sheet>
      </v-sheet>
      
    </v-sheet>
  </v-sheet>
</template>

<script setup>
import { onMounted, computed } from 'vue';
import PageHeader from '@/components/AppHeader.vue'
import { useCartStore } from '@/stores/cartStore';
import CartItemList from '@/components/cart/CartItemList.vue';
const cartStore = useCartStore();

const cart = computed(() => {
  return cartStore.cart
})

const totalCost = computed(() => {
  return cartStore.totalCost
})

function checkout() {
  cartStore.checkout()
}

onMounted(async () => {
  await cartStore.getCart()
})
</script>
