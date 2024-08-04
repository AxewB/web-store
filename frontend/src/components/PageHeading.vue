<template>
  <v-sheet class="text-h3 my-4 bg-transparent pl-5 text-disabled">
    {{ title}}
  </v-sheet>
</template>

<script setup>
const props = defineProps(['title'])
</script>