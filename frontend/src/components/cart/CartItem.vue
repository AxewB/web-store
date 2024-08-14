<template>
  <VSheet class="d-flex flex-row pa-2" rounded>
    <VAvatar
    class="cursor-pointer"
      :image="thumbnail"
      size="100"
      rounded
      color="grey"
      @click="moveToProductPage()"
    />
    <VSheet class="flex-grow-1 d-flex flex-column justify-space-between ml-2">
      <VSheet
        class="d-flex flex-row align-center text-h6 font-weight-medium cursor-pointer "
        @click="moveToProductPage()"
      >
        {{ name }}
        <VSheet
          class="text-body-1 font-weight-black bg-succes px-2 py-1 ml-2"
          rounded
        >
          {{ cost }}$
        </VSheet>
      </VSheet>
      <VBtn
      icon="mdi-delete"
      variant="tonal"
      color="error"
      rounded
      size="small"
      @click="removeFromCart()"
    />
    </VSheet>
  </VSheet>
</template>

<script setup>
import { computed } from 'vue';
import { useRouter } from 'vue-router';
import { useCartStore } from '@/stores/cartStore';

// variables

const router = useRouter();
const cartStore = useCartStore();
const props = defineProps({
  product: {
    type: Object,
    required: true
  }
})

// computed

const thumbnail = computed(() => props.product.thumbnail)
const name = computed(() => props.product.name)
const cost = computed(() => props.product.cost)
const quantity = computed(() => props.product.quantity)
const productId = computed(() => props.product.id)

// methods

function removeFromCart() {
  cartStore.removeFromCartById(productId.value)
}

function moveToProductPage() {
  router.push({name: 'productPage', params: {id: productId.value}})
}
</script>
