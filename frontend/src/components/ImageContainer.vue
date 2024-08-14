<template>
  <VSheet
    :width="size"
    :max-height="size"
  >
    <VHover v-slot="{ isHovering, props }">
      <VCard
        class="mx-auto"
        :height="size"
        v-bind="props"
        elevation="0"
        border
        @click="onClick()"
      >
        <VImg
          :src="src"
          :height="size"
          :width="size"
          cover
        />
        <VOverlay
          :model-value="isHovering"
          class="align-center justify-center cursor-pointer"
          scrim="rgba(0,0,0,0.7)"
          contained
        >
          <VSheet class="text-button bg-transparent" >
            {{ actionTitle }}
          </VSheet>
        </VOverlay>
      </VCard>
    </VHover>
  </VSheet>
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