<template>
  <VSheet v-if="loggedIn" class="bg-transparent pa-2">
    <PageHeader/>

    <PageHeading title="Корзина"/>

    <VSheet
     class="d-flex flex-row pt-2 bg-transparent"
     rounded
    >
      <VSheet
        class="flex-grow-1 d-flex flex-column mr-2 pa-2"
        rounded
      >
        <CartItemList :products="cart.products"/>
      </VSheet>
      <VSheet width="400px" class="bg-transparent d-flex flex-column" rounded>
        <VSheet
          class="pa-2"
          rounded>
          <VSheet
            class="d-flex flex-column pa-2"
            rounded
            border
          >
            <VBtn
              size="large"
              color="primary"
              @click="checkout()"
            >
              Оформить заказ
            </VBtn>
            <VDivider class="my-2"/>
            <VSheet class="d-flex flex-row justify-space-between my-1">
              <span class="font-weight-medium ">
                Общая стоимость
              </span>
              <span class="font-weight-bold">
                {{ totalCost.toFixed(2) }}$
              </span>
            </VSheet>
            <VSheet class="d-flex flex-row justify-space-between my-1">
              <span class="font-weight-medium ">
                Товаров
              </span>
              <span class="font-weight-bold ">
                {{ cart.products.length }}
              </span>
            </VSheet>
          </VSheet>
        </VSheet>
        <VBtn
          color="error"
          variant="plain"
          size="large"
          class="mt-2"
          @click="isConfirmingClearingCart = true"
        >
          Очистить корзину
        </VBtn>
      </VSheet>
    </VSheet>
  </VSheet>
  <ConfirmDialog
    v-model="isConfirmingClearingCart"
    @on-confirm="confirmClearCart"
    @on-cancel="isConfirmingClearingCart = null"
    title="Очистить корзину"
    description="Вы точно хотите очистить корзину?"
  />
</template>

<script setup>
import { onMounted, computed, ref } from 'vue';
import PageHeader from '@/components/AppHeader.vue'
import { useCartStore } from '@/stores/cartStore';
import { useUserStore } from '@/stores/userStore';
import CartItemList from '@/components/cart/CartItemList.vue';
import PageHeading from './PageHeading.vue';
import EmptyWarning from '@/components/EmptyWarning.vue';
import ConfirmDialog from './ConfirmDialog.vue';
import { useRouter } from 'vue-router';
const cartStore = useCartStore();
const userStore = useUserStore();
const router = useRouter();
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

const loggedIn = computed(() => {
    return userStore.status.loggedIn;
  },
)

onMounted(async () => {
  if (!loggedIn.value) {
    router.push({name: "userProfile"});
  }
  else {
    await cartStore.getCart()
  }
})
</script>
