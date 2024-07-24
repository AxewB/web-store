<template>
  <v-sheet width="100%" class="bg-transparent pa-4 ma-2" rounded border>
    <v-sheet class="mb-2 text-h6">
      {{ title }}
    </v-sheet>
    <slot></slot>
  </v-sheet>
</template>

<script setup>
const props = defineProps(['title'])
</script>
