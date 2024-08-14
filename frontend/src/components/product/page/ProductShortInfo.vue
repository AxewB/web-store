<template>
  <VSheet class="bg-transparent">
    <VSheet class="text-h4 bg-transparent">
      {{ product.name }}
    </VSheet>
    <VDivider class="my-2"/>
    <VSheet class="d-flex flex-column bg-transparent">
      <VSheet class="text-h5 font-weight-bold align-center d-flex mb-2 bg-transparent">
        О товаре
      </VSheet>
      <VSheet
        v-for="key in Object.keys(displayData)"
        :key="key"
        width="100%"
        class="d-flex overflow-auto bg-transparent"
      >
        <VSheet
          class="text-disabled mb-1 bg-transparent"
          width="200px"
        >
          {{ key }}
        </VSheet>
        <VSheet class="bg-transparent">
          {{ displayData[key] }}
        </VSheet>
      </VSheet>
    </VSheet>
  </VSheet>
</template>

<script setup>
import { computed } from 'vue';
const props = defineProps({
  product: {
    type: Object,
    required: true
  }
})
const emit = defineEmits(['on-open-description'])

// Заглушка
const displayData = computed(() => {
  return {
    "Ширина": "Ширина",
    "Высота": "Высота",
    "Глубина": "Глубина",
    "Вес": "Вес"
  }
})
</script>