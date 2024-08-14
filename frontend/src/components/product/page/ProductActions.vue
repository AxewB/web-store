<template>
  <VSheet class="d-flex flex-column  bg-transparent">
    <VSheet
      class="d-flex justify-center mb-2 pa-3 bg-transparent" 
      elevation="3"
      width="100%"
      border
      rounded
    >
      В наличии {{ product.quantity }}
    </VSheet>
    <VSheet
      rounded
      border
      class="pa-4"
    >
      <VSheet class="text-h4 mb-2 font-weight-medium">
        {{ product.cost }}$
      </VSheet>
      <VSheet v-if="!isProductInCart">
        <VBtn
        @click="emit('on-add-to-cart', product)"
        size="large"
        width="100%"
        color="primary"
      >
        В корзину
      </VBtn>
      </VSheet>
      <VSheet
        class="d-flex align-center"
        v-else
      >
        <VBtn
          class="mr-2"
          color="error"
          rounded
          icon="mdi-minus"
          @click="emit('on-remove-from-cart', product)"
        />
        <VBtn
          class="flex-grow-1"
          size="large"
          color="success"
          @click="emit('on-go-to-cart')"
        >
          В корзине
          <VIcon icon="mdi-arrow-right"/>
        </VBtn>
      </VSheet>
    </VSheet>
  </VSheet>
</template>

<script setup>
// variables

const props = defineProps({
  product: {
    type: Object,
    required: true
  },
  isProductInCart: {
    type: Boolean,
    required: true
  }
})

const emit = defineEmits(['on-add-to-cart', 'on-remove-from-cart', 'on-go-to-cart'])
</script>
