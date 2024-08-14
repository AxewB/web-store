<template>
  <VSheet
    class="pa-2 d-flex align-center  flex-wrap"
    rounded
  >
    <VSheet v-for="(productItem, index) in props.products">
      <VSheet
        class="bg-grey mb-2"
        :class="index < props.products.length - 1 ? 'mr-2' : ''"
        rounded
      >
        <v-avatar
          size="80"
          :image="productItem.product.thumbnail"
          class="cursor-pointer"
          @click="emit('on-product-click', productItem.product.id)"
        />
      </VSheet>
    </VSheet>
  </VSheet>
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