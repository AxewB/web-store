<template>
  <v-sheet class="d-flex flex-column  bg-transparent">
    <v-sheet class="d-flex justify-center mb-2 pa-3 bg-transparent" width="100%" border rounded>
      В наличии {{ product.stock }}
    </v-sheet>
    <v-sheet rounded elevation="5" class="pa-4">
      <v-sheet class="text-h4 mb-2 font-weight-medium">
        {{ product.price }}$
      </v-sheet>
      <v-sheet class="">
        <v-btn
        @click="emit('on-add-to-cart')"
        size="large"
        width="100%"
        color="primary"
      >
        Заказать
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
  }
})

const emit = defineEmits(['on-add-to-cart'])
</script>
