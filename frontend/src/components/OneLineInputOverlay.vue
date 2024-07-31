<template>
  <v-overlay v-model="props.isShown" width="100vw" height="100vh" class="d-flex flex-column justify-center align-center">
    <v-sheet class="d-flex flex-column pa-2 align-center justify-center bg-transparent flex-grow-1" height="100%">
      <v-sheet class="pa-2" rounded width="300px">
        <span class="text-h5 font-weight-medium"> {{ title }} </span>
        <v-text-field
          v-model="value"/>
        <v-sheet class="d-flex justify-end">
          <v-btn @click="onCancel()" variant="plain" class="mr-2">
            Cancel
          </v-btn>
          <v-btn @click="onConfirm()" color="primary">
            Confirm
          </v-btn>
        </v-sheet>
      </v-sheet>
    </v-sheet>
  </v-overlay>
</template>

<script setup>
import { ref } from 'vue';
const props = defineProps({
  isShown: {
    type: Boolean,
    required: true
  },
  title: {
    type: String,
    default: "Enter value",
  },
  func: {
    type: Function,
    default: () => {},
  }
})
const emit = defineEmits(['on-confirm', 'on-cancel']);
const value = ref('');

const onCancel = () => emit('on-cancel');
const onConfirm = () => {
  if (props.func) {
    props.func(value.value);
  } else {
    emit('on-confirm', value.value);
  }
  onCancel()
}

</script>