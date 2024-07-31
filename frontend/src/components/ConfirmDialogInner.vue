<template>
  <v-card
    prepend-icon="mdi-alert-box"
    :text="props.description"
    :title="props.title"
  >
    <template v-slot:actions>
      <v-spacer></v-spacer>

      <v-btn @click="onCancel()">
        Отмена
      </v-btn>

      <v-btn @click="onConfirm()">
        Принять
      </v-btn>
    </template>
  </v-card>
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