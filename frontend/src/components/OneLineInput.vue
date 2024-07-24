<template>
  <v-sheet class="d-flex flex-column pa-2">
    <v-text-field
      v-model="value"
      :label="label"/>
    <v-sheet class="d-flex">
      <v-btn @click="onCancel()">Cancel</v-btn>
      <v-btn @click="onConfirm()">Confirm</v-btn>
    </v-sheet>
  </v-sheet>
</template>

<script setup>
import { computed, ref } from 'vue';
const props = defineProps({
  label: {
    type: String,
    required: true
  },
})
const emit = defineEmits(['on-confirm', 'on-cancel']);
const value = ref('');

const onCancel = () => emit('on-cancel');
const onConfirm = () => emit('on-confirm', value.value);
</script>