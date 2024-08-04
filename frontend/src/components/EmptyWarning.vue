<template>
  <v-sheet class="d-flex flex-column justify-center align-center" width="100%">
    <v-sheet class="d-flex flex-column justify-center align-center pa-6" rounded>
      <span class="text-h2 mb-4">🤕</span>
      <span class="text-h5 font-weight-bold mb-2">{{ props.title }}</span>
      <span class="text-body-1">{{ props.description}}</span>
      <v-btn 
        v-if="props.action" 
        :text="props.action"
        color="primary"
        @click="emit('click')"
        variant="tonal"
        class="mt-2"
      />
    </v-sheet>
  </v-sheet>
</template>

<script setup>  
const props = defineProps({
  title: {
    type: String,
    default: 'Здесь пусто'
  },
  description: {
    type: String,
    default: 'Похоже, тут чего-то не хватает...'
  },
  action: {
    type: String,
    default: ''
  }
})
const emit = defineEmits(['click'])
</script>