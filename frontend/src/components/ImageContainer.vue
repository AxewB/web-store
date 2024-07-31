<template>
  <v-sheet
    :width="size"
    :max-height="size"
  >
    <v-hover v-slot="{ isHovering, props }">
      <v-card
      class="mx-auto"
      :height="size"
      v-bind="props"
      elevation="0"
      border
      @click="onClick()"
    >
      <v-img
        :src="src"
        :height="size"
        :width="size"
        cover
      />
      <v-overlay
        :model-value="isHovering"
        class="align-center justify-center cursor-pointer"
        scrim="rgba(0,0,0,0.7)"
        contained
      >
        <v-sheet class="text-button bg-transparent" >
          {{ actionTitle }}
        </v-sheet>
      </v-overlay>
    </v-card>
    </v-hover>
  </v-sheet>
</template>

<script setup>
import { computed } from 'vue';
const props = defineProps({
  src: {
    type: String,
    required: true
  },
  size: {
    type: Number,
    default: 200
  },
  actionTitle: {
    type: String,
    default: 'REMOVE'
  }
})
const emit = defineEmits(['on-click']);

const size = computed(() => props.size + 'px')
const actionTitle = computed(() => props.actionTitle)
function onClick() {
  emit('on-click')
}
</script>