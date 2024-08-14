<template>
  <VSheet
    class="bg-transparent pa-4 ma-2"
    width="100%" 
    rounded
    border
  >
    <VSheet class="mb-2 text-h6">
      {{ title }}
    </VSheet>
    <slot></slot>
  </VSheet>
</template>

<script setup>
const props = defineProps(['title'])
</script>
