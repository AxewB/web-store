<template>
  <v-sheet class="pa-2 d-flex align-center" rounded>
    <v-sheet v-for="(productItem, index) in props.products">
    <v-sheet rounded class="bg-grey"
    :class="index > 0 ? 'ml-2' : ''">
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