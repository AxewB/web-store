<template>
  <v-sheet class="d-flex flex-column  bg-transparent">
    <v-sheet class="d-flex justify-center mb-2 pa-3 bg-transparent" width="100%" border rounded>
      В наличии {{ product.quantity }}
    </v-sheet>
    <v-sheet rounded border class="pa-4">
      <v-sheet class="text-h4 mb-2 font-weight-medium">
        {{ product.cost }}$
      </v-sheet>
      <v-sheet v-if="!isProductInCart">
        <v-btn
        @click="emit('on-add-to-cart', product)"
        size="large"
        width="100%"
        color="primary"
      >
        В корзину
      </v-btn>
      </v-sheet>
      <v-sheet v-else class="d-flex align-center">
        <v-btn
          @click="emit('on-remove-from-cart', product)"
          color="error"
          rounded
          class="mr-2"
          icon="mdi-minus"/>
        <v-btn
        @click="emit('on-go-to-cart')"
        class="flex-grow-1"
        size="large"
        color="success"
      >
        В корзине
        <VIcon icon="mdi-arrow-right"></VIcon> 
      </v-btn>
    </v-sheet>
    </v-sheet>
  </v-sheet>
</template>

<script setup>

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

function buttonClick() {
  if (!props.isProductInCart) {
    alert(1)
    // emit('on-add-to-cart', props.product)
  }
  else {
    alert(2)
    // emit('on-remove-from-cart', props.product)
  }
}
</script>
