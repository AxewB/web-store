<template>
  <v-card
    width="200px"
    rounded class="ma-4 pa-2 d-flex flex-column"
    elevation="10"
    border
    @click="onClick()"
  >
    <v-img 
      width="200px"
      height="200px"
      :src="thumbnail">
    </v-img>

    <VDivider />
    <span class="text-h6 my-1">
      {{ cost }}$
    </span>
    <span class="my-1">
      {{ name }}
    </span>
    <v-rating
      class="my-1"
      readonly
      density="compact"
      size="small"
      color="orange"
      :value="rating"
    />
  </v-card>
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

const thumbnail = computed(() => props.product.thumbnail)
const name = computed(() => props.product.name)
const rating = computed(() => props.product.rating)
const cost = computed(() => props.product.cost)

function onClick() {
  emit('on-click', props.product.id)
}
</script>