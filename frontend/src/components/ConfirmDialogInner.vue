<template>
  <VCard
    prepend-icon="mdi-alert-box"
    :text="props.description"
    :title="props.title"
  >
    <template v-slot:actions>
      <VSpacer/>

      <VBtn @click="onCancel()">
        Отмена
      </VBtn>

      <VBtn @click="onConfirm()">
        Принять
      </VBtn>
    </template>
  </VCard>
</template>

<script setup>
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
}
const onCancel = () => {
  emit('on-cancel');
}
</script>