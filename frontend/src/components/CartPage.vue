<template>
  <v-sheet class="bg-transparent pa-2">
    <PageHeader/>
    <PageHeading title="Корзина"/>
    <v-sheet 
     class="d-flex flex-row pt-2 bg-transparent" rounded>

      <v-sheet class="flex-grow-1 d-flex flex-column mr-2 pa-2" rounded>
        <CartItemList :products="cart.products"/>
      </v-sheet>
      <v-sheet width="400px" class="bg-transparent d-flex flex-column" rounded>
        <v-sheet class="pa-2" rounded>
          <v-sheet class="d-flex flex-column pa-2" rounded border>

            <v-btn  size="large" color="primary" @click="checkout()">Оформить заказ</v-btn>
            <VDivider class="my-2"/>
            <v-sheet class="d-flex flex-row justify-space-between my-1">
              <span class="font-weight-medium ">Общая стоимость</span>
              <span class="font-weight-bold ">{{ totalCost.toFixed(2) }}$</span>
            </v-sheet>
            <v-sheet class="d-flex flex-row justify-space-between my-1">
              <span class="font-weight-medium ">Товаров</span>
              <span class="font-weight-bold ">{{ cart.products.length }}</span>
            </v-sheet>
            <span class="text-body-1">  </span>
          </v-sheet>
        </v-sheet>
        <v-btn 
          color="error" 
          variant="plain" 
          size="large" 
          class="mt-2"
          @click="isConfirmingClearingCart = true"
        >
          Очистить корзину
        </v-btn>
      </v-sheet>
    </v-sheet>
  </v-sheet>
  <ConfirmDialog
    v-model="isConfirmingClearingCart"
    @on-confirm="confirmClearCart"
    @on-cancel="isConfirmingClearingCart = null"
    title="Очистить корзину"
    description="Вы точно хотите очистить корзину?"/>

</template>

<script setup>
import { onMounted, computed, ref } from 'vue';
import PageHeader from '@/components/AppHeader.vue'
import { useCartStore } from '@/stores/cartStore';
import CartItemList from '@/components/cart/CartItemList.vue';
import PageHeading from './PageHeading.vue';
import EmptyWarning from '@/components/EmptyWarning.vue';
import ConfirmDialog from './ConfirmDialog.vue';
const cartStore = useCartStore();

const isConfirmingClearingCart = ref(false)

function confirmClearCart() {
  isConfirmingClearingCart.value = false
  cartStore.clearCart()
}

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
