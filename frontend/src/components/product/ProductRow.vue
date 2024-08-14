<template>
  <VCard
    width="100%"
    rounded
    class="my-2 pa-2 d-flex"
    border
    @click="onClick"
  >
    <VSheet width="100px">
      <VImg :src="thumbnail"/>
    </VSheet>
    <VDivider
      class="mx-2"
      vertical
    />

    <VSheet class="d-flex flex-column flex-grow-1">
      <span class="text-h6 my-1">
        {{ props.product.cost }}$
      </span>
      <span class="my-1">
        {{ props.product.name }}
      </span>
    </VSheet>
  </VCard>
</template>

<script setup>
import { computed } from 'vue';
const props = defineProps({
  product: {
    type: Object,
    required: true
  }
});
const emit = defineEmits(['on-click']);
const thumbnail = computed(() => props.product.thumbnail)
function onClick() {
  emit('on-click', props.product.id)
}
</script>