<template>
  <VOverlay
    class="d-flex flex-column justify-center align-center" 
    v-model="props.isShown"
    width="100vw"
    height="100vh"
  >
    <VSheet
      class="d-flex flex-column pa-2 align-center justify-center bg-transparent flex-grow-1"
      height="100%"
    >
      <VSheet
        class="pa-2"
        rounded
        width="300px"
      >
        <span class="text-h5 font-weight-medium">
          {{ title }}
        </span>

        <VTextField v-model="value"/>

        <VSheet class="d-flex justify-end">
          <VBtn
            class="mr-2"
            @click="onCancel()"
            variant="plain"
          >
            Cancel
          </VBtn>
          <VBtn
            @click="onConfirm()"
            color="primary"
          >
            Confirm
          </VBtn>
        </VSheet>
      </VSheet>
    </VSheet>
  </VOverlay>
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