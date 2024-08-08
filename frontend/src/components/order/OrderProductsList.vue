<template>
  <v-sheet class="pa-2 d-flex align-center  flex-wrap" rounded >
    <v-sheet v-for="(productItem, index) in props.products">
      <v-sheet rounded class="bg-grey mb-2"
      :class="index < props.products.length - 1 ? 'mr-2' : ''">
        <v-avatar 
          size="80" 
          :image="productItem.product.thumbnail"
          class="cursor-pointer"
          @click="emit('on-product-click', productItem.product.id)"/>
      </v-sheet>
    </v-sheet>
  </v-sheet>
</template>

<script setup>
const props = defineProps({
  products: {
    type: Object,
    required: true
  }
})
const emit = defineEmits(['on-product-click']);

</script>