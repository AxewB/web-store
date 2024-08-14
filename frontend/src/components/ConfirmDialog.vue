<template>
  <v-dialog persistent max-width="500" v-model="model">
    <ConfirmDialogInner
      :title="props.title"
      :description="props.description"
      @on-cancel="onCancel"
      @on-confirm="onConfirm"
    />
  </v-dialog>
</template>

<script setup>
import ConfirmDialogInner from './ConfirmDialogInner.vue';
const model = defineModel()
const props = defineProps({
  title: {
    type: String,
    default: 'Подтвердите действие'
  },
  description: {
    type: String,
    default: 'Вы точно хотите сделать это?'
  },
})

const emit = defineEmits(['on-cancel', 'on-confirm'])

const onConfirm = () => {
  emit('on-confirm');
  model.value = false
}
const onCancel = () => {
  emit('on-cancel');
  model.value = false
}

</script>