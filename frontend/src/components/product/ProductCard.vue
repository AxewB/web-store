<template>
  <VCard
    class="ma-4 pa-2 d-flex flex-column"
    width="200px"
    rounded
    elevation="10"
    border
    @click="onClick()"
  >
    <VImg
      width="200px"
      height="200px"
      :src="thumbnail"
    />

    <VDivider />
    <span class="text-h6 my-1">
      {{ cost }}$
    </span>
    <span class="my-1">
      {{ name }}
    </span>
  </VCard>
</template>

<script setup>
import { computed } from 'vue'
const props = defineProps({
  product: {
    type: Object,
    required: true
  }
})
const emit = defineEmits(['on-click'])

// == computed ==

const thumbnail = computed(() => props.product.thumbnail)
const name = computed(() => props.product.name)
const cost = computed(() => props.product.cost)

function onClick() {
  emit('on-click', props.product.id)
}
</script>