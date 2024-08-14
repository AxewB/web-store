<template>
  <VSheet class="text-h3 my-4 bg-transparent pl-5 text-disabled">
    {{ title}}
  </VSheet>
</template>

<script setup>
const props = defineProps(['title'])
</script>