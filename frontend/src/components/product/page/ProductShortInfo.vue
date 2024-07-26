<template>
  <v-sheet class="bg-transparent">
    <v-sheet class="text-h4 bg-transparent"> 
      {{ product.name }} 
    </v-sheet>
    <VDivider class="my-2"/>
    <v-sheet class="d-flex flex-column bg-transparent">
      <v-sheet class="text-h5 font-weight-bold align-center d-flex mb-2 bg-transparent">
        О товаре
      </v-sheet>
      <v-sheet 
        v-for="key in Object.keys(displayData)" 
        :key="key"
        width="100%"
        class="d-flex overflow-auto bg-transparent"
      >
        <v-sheet class="text-disabled mb-1 bg-transparent" width="200px">
          {{ key }}
        </v-sheet>
        <v-sheet class="bg-transparent">
          {{ displayData[key] }}
        </v-sheet>
      </v-sheet>
    </v-sheet>
  </v-sheet>
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

const displayData = computed(() => {
  // const dims = props.product.dimensions
  // return {
  //   "Ширина": dims.width,
  //   "Высота": dims.height,
  //   "Глубина": dims.depth,
  //   "Вес": props.product.weight
  // }

  return {
    "Ширина": "Ширина",
    "Высота": "Высота",
    "Глубина": "Глубина",
    "Вес": "Вес"
  }
})
</script>