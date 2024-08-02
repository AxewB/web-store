<template>
  <v-sheet class="d-flex flex-row pa-2" border rounded>
    <v-avatar :image="thumbnail" size="100" rounded color="grey"/>
    <v-sheet class="flex-grow-1 d-flex flex-column ml-2">
      <span class="text-h6 font-weight-medium">{{ name }}</span>
      <span class="text-body-1">{{ cost }}$</span>
    </v-sheet>
    <v-sheet class="d-flex align-center">
      <v-text-field
        width="150px"
        hide-details
        v-model="quantity"
        density="compact"
        variant="outlined"
        class="mx-2"
        readonly>
        <template #prepend-inner>
          <v-btn
            color="primary"
            variant="plain"
            icon="mdi-minus"
            size="small"
            rounded
          >
            
          </v-btn>
        </template>
        <template #append-inner>
          <v-btn
            color="primary"
            variant="plain"
            icon="mdi-plus"
            size="small"
            rounded
          >
            
          </v-btn>
        </template>
      </v-text-field>
    </v-sheet>
    <v-sheet class="d-flex align-center">
      <v-btn icon="mdi-delete" variant="plain" color="error"/>
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

const thumbnail = computed(() => props.product.thumbnail)
const name = computed(() => props.product.name)
const cost = computed(() => props.product.cost)
const quantity = computed(() => props.product.quantity)

</script>
