<template>
  <v-card width="100%" rounded class="my-2 pa-2 d-flex" border @click="onClick">
    <v-sheet width="100px">
      <v-img :src="thumbnail"/>
    </v-sheet>
    <VDivider vertical class="mx-2"/>

    <v-sheet class="d-flex flex-column flex-grow-1">
      <span class="text-h6 my-1">
        {{ props.product.cost }}$
      </span>
      <span class="my-1">
        {{ props.product.name }}
      </span>
    </v-sheet>
  </v-card>
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